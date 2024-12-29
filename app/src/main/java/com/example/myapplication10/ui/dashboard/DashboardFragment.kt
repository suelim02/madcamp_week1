package com.example.myapplication10.ui.dashboard

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication10.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private val imageList = ArrayList<ImageDiary>() // RecyclerView 데이터 리스트
    private lateinit var adapter: ImageListAdapter // RecyclerView 어댑터


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        // RecyclerView 초기화
        adapter = ImageListAdapter(imageList) { item, position ->
            val inputTextDialog = EditTextDialogFragment(
                initialTitle = item.description, // 기존 제목 전달
                initialDate = item.date // 기존 날짜 전달
            ) { updatedTitle, updatedDate ->
                if (updatedTitle != null && updatedDate != null) {
                    imageList[position].description = updatedTitle
                    imageList[position].date = updatedDate
                    adapter.notifyItemChanged(position) // RecyclerView 갱신
                }
            }
            inputTextDialog.show(parentFragmentManager, "EditTextDialog")
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // ActivityResultLauncher 초기화
        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri: Uri? = result.data?.data
                imageUri?.let {
                    // 다이얼로그를 띄워 텍스트와 날짜 입력받기
                    val inputTextDialog = EditTextDialogFragment(initialTitle = null, // 새 데이터이므로 기존 제목은 null
                        initialDate = null ) {inputText, inputTextDate ->
                        val newItem = ImageDiary(
                            imageUri = it,
                            description = inputText ?: "Default Text",
                            date = inputTextDate ?: "Default Date"// 기본 날짜
                        )

                        // 데이터를 리스트 맨 위에 추가
                        imageList.add(0, newItem)

                        // 어댑터에 삽입된 데이터 알리기
                        adapter.notifyItemInserted(0)

                        // RecyclerView를 맨 위로 스크롤
                        binding.recyclerView.scrollToPosition(0)
                    }
                    inputTextDialog.show(parentFragmentManager, "EditTextDialog")
                }
            }
        }


        // 버튼 클릭 리스너 설정
        binding.buttonGallery.setOnClickListener {
            loadImage()
        }

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Triple<Int, String, String>>("updatedData")
            ?.observe(viewLifecycleOwner) { updatedData ->
                val (position, updatedText, updatedDate) = updatedData
                imageList[position].description = updatedText
                imageList[position].date = updatedDate // 날짜 업데이트
                adapter.notifyItemChanged(position) // RecyclerView 갱신
            }
    }


    private fun loadImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        galleryLauncher.launch(intent)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

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
        adapter = ImageListAdapter(imageList)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // ActivityResultLauncher 초기화
        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri: Uri? = result.data?.data
                imageUri?.let {
                    // 다이얼로그를 띄워 텍스트 입력 받기
                    val inputTextDialog = EditTextDialogFragment { inputText ->
                        // RecyclerView 데이터 리스트에 추가
                        imageList.add(ImageDiary(it, inputText ?: "Default Text"))
                        adapter.notifyDataSetChanged() // RecyclerView 갱신
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

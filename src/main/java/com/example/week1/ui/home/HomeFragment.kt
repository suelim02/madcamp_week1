package com.example.week1.ui.home

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week1.Myadapter
import com.example.week1.Team
import com.example.week1.allListDB
import com.example.week1.databinding.FragmentHomeBinding
import com.example.week1.favorListDB
import com.example.week1.pushListActivity
import java.util.ArrayList

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var favordbHelper: favorListDB
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favordbHelper = favorListDB(requireContext())
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val selectedData = result.data?.getSerializableExtra("selected_data") as? ArrayList<Team>
                if (selectedData != null) {
                    favordbHelper.insertTeam(selectedData[0].name, selectedData[0].stadium)
                    val favorrecyclerView: RecyclerView = binding.recyclerviewFavorlist
                    val favorList = favordbHelper.getFavorTeams()

                    favorrecyclerView.layoutManager = LinearLayoutManager(requireContext())
                    favorrecyclerView.adapter = Myadapter(favorList)

                    val divider = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
                    favorrecyclerView.addItemDecoration(divider)
                    Toast.makeText(requireContext(), "Selected: ${selectedData[0].name}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val favorrecyclerView: RecyclerView = binding.recyclerviewFavorlist
        val favorList = favordbHelper.getFavorTeams()

        favorrecyclerView.layoutManager = LinearLayoutManager(requireContext())
        favorrecyclerView.adapter = Myadapter(favorList)

        val divider = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        favorrecyclerView.addItemDecoration(divider)

        val btn_push_list: Button = binding.btnPlusList
        btn_push_list.setOnClickListener    {
            val intent = Intent(requireContext(), pushListActivity::class.java)
            resultLauncher.launch(intent)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
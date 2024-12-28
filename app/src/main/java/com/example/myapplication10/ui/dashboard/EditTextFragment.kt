package com.example.myapplication10.ui.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication10.databinding.FragmentEditTextBinding

class EditTextFragment : Fragment() {

    private var _binding: FragmentEditTextBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditTextBinding.inflate(inflater, container, false)
        val root = binding.root

        // Submit 버튼 클릭 이벤트
        binding.buttonSubmit.setOnClickListener {
            val updatedText = binding.editText.text.toString()
            val action = EditTextFragmentDirections.actionEditTextFragmentToNavigationDashboard(
                updatedText,  // currentText 인자
                42           // position 인자
            )
            findNavController().navigate(action)
        }
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


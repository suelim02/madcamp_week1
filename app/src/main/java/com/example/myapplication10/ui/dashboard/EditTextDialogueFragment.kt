package com.example.myapplication10.ui.dashboard

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.myapplication10.R

//class EditTextDialogFragment(private val onTextEntered: (String?, String?) -> Unit) : DialogFragment() {
//
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val builder = AlertDialog.Builder(requireContext())
//
//        // XML 레이아웃 사용
//        val view = LayoutInflater.from(context).inflate(R.layout.fragment_edit_text, null)
//        val titleInput = view.findViewById<EditText>(R.id.editText) // 제목 입력
//        val dateInput = view.findViewById<EditText>(R.id.editTextDate)  // 날짜 입력
//
//        builder.setView(view)
//            .setTitle("일기 기록")
//            .setPositiveButton("OK") { _, _ ->
//                val title = titleInput.text.toString()
//                val date = dateInput.text.toString()
//                onTextEntered(title, date) // 두 값을 반환
//            }
//            .setNegativeButton("Cancel") { dialog, _ ->
//                dialog.dismiss()
//                onTextEntered(null, null) // 취소 시 null 반환
//            }
//
//        return builder.create()
//    }
//}

class EditTextDialogFragment(
    private val initialTitle: String?, // 기존 제목
    private val initialDate: String?, // 기존 날짜
    private val onTextEntered: (String?, String?) -> Unit // 콜백 함수
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_edit_text, null)

        val titleInput = view.findViewById<EditText>(R.id.editText)
        val dateInput = view.findViewById<EditText>(R.id.editTextDate)

        // 기존 데이터 초기화
        titleInput.setText(initialTitle) // 제목 초기화
        dateInput.setText(initialDate)   // 날짜 초기화

        builder.setView(view)
            .setTitle("Edit Entry")
            .setPositiveButton("OK") { _, _ ->
                val title = titleInput.text.toString()
                val date = dateInput.text.toString()
                onTextEntered(title, date)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
                onTextEntered(null, null)
            }

        return builder.create()
    }
}


package com.example.myapplication10.ui.dashboard

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class EditTextDialogFragment(private val onTextEntered: (String?) -> Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val input = EditText(requireContext())
        input.hint = "Enter description"

        builder.setTitle("Image Description")
            .setView(input)
            .setPositiveButton("OK") { _, _ ->
                onTextEntered(input.text.toString())
            }
            .setNegativeButton("Cancel") { _, _ ->
                onTextEntered(null)
            }
        return builder.create()
    }
}

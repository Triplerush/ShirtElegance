package com.android.inventarioapp.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.android.inventarioapp.SQLManager
import com.android.inventarioapp.databinding.DialogInputAceptBinding

class AceptDialog(private val context: Context,
                  private val onSubmitClickListener: (Boolean) -> Unit
): DialogFragment() {
    private lateinit var binding : DialogInputAceptBinding
    private lateinit var base : SQLManager
    var flag = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogInputAceptBinding.inflate(LayoutInflater.from(context))
        base = SQLManager(context)
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        binding.btnAceptar.setOnClickListener {
            flag = true
            onSubmitClickListener.invoke(flag)
            dismiss()
        }

        binding.btnCancelar.setOnClickListener {
            dismiss()
        }

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }
}
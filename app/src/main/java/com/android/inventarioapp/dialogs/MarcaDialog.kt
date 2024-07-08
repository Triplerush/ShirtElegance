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
import com.android.inventarioapp.class_tables.Marca
import com.android.inventarioapp.databinding.DialogInputMarcaBinding

class MarcaDialog ( private val context: Context,
                    private val onSubmitClickListener: (Boolean) -> Unit
): DialogFragment() {
    private lateinit var binding : DialogInputMarcaBinding
    private lateinit var base : SQLManager
    var flag = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogInputMarcaBinding.inflate(LayoutInflater.from(context))
        base = SQLManager(context)
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        binding.bAddQuantity.setOnClickListener {
            val code = binding.codeValue.text.toString()
            val name = binding.nameValue.text.toString()
            if(code.isNotBlank() && name.isNotBlank()){
                flag = base.addMarca(context, Marca(code.toInt(),name))
                dismiss()
            }

            onSubmitClickListener.invoke(flag)
        }

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }
}
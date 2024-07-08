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
import com.android.inventarioapp.class_tables.Cliente
import com.android.inventarioapp.databinding.DialogInputClientBinding

class ClientDialog(private val context: Context,
                   private val onSubmitClickListener: (Boolean) -> Unit
): DialogFragment() {
    private lateinit var binding : DialogInputClientBinding
    private lateinit var base : SQLManager
    var flag = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogInputClientBinding.inflate(LayoutInflater.from(context))
        base = SQLManager(context)
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        binding.bAddQuantity.setOnClickListener {
            val code = binding.codeValue.text.toString()
            val name = binding.nameValue.text.toString()
            val phone = binding.phoneValue.text.toString()
            if(code.isNotBlank() && name.isNotBlank()){
                flag = base.addCliente(context, Cliente(code,name,phone))
                dismiss()
            }

            onSubmitClickListener.invoke(flag)
        }

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }
}
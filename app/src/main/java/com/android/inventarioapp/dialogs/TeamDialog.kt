package com.android.inventarioapp.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.android.inventarioapp.R
import com.android.inventarioapp.SQLManager
import com.android.inventarioapp.class_tables.Equipo
import com.android.inventarioapp.databinding.DialogInputTeamBinding

class TeamDialog (private val context: Context,
                  private val onSubmitClickListener: (Boolean) -> Unit
): DialogFragment() {
    private lateinit var binding : DialogInputTeamBinding
    private lateinit var base : SQLManager
    var flag = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogInputTeamBinding.inflate(LayoutInflater.from(context))
        base = SQLManager(context)
        val builder = AlertDialog.Builder(requireActivity())
        var ListaPaises = arrayOf("País")
        val sprPais = binding.sprPais
        for (pais in base.getPaises(context)) {
            ListaPaises = ListaPaises.plus("${pais.PaiCod} - ${pais.PaiNom}")
        }
        val adaptadorPais = ArrayAdapter<String>(context, R.layout.spinner_items,ListaPaises)
        sprPais.adapter = adaptadorPais

        builder.setView(binding.root)

        binding.bAddQuantity.setOnClickListener {
            val code = binding.codeValue.text.toString()
            val name = binding.nameValue.text.toString()
            val pais = sprPais.selectedItem.toString()
            if(code.isNotBlank() && name.isNotBlank() && pais != "País"){
                flag = base.addEquipo(context, Equipo(code,name,pais.split(" - ")[0].toInt()))
                dismiss()
            }

            onSubmitClickListener.invoke(flag)
        }

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }
}
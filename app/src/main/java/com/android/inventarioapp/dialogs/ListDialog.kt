package com.android.inventarioapp.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.inventarioapp.SQLManager
import com.android.inventarioapp.databinding.DialogInputListBinding
import com.android.inventarioapp.view_delete_shirt.rvDeleteAdapter

class ListDialog(
    private val context: Context,
    private val table: String
) : DialogFragment() {
    private lateinit var binding: DialogInputListBinding
    private lateinit var base: SQLManager
    private lateinit var adapterList: rvListAdapter
    private lateinit var lista: ArrayList<String>

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogInputListBinding.inflate(LayoutInflater.from(context))
        base = SQLManager(context)
        lista = listData(table)

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)
        adapterList = rvListAdapter(lista)

        binding.tvHeader.text = table
        binding.bAddQuantity.setOnClickListener {
            dismiss()
        }

        binding.rvList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterList
        }

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    private fun listData(table : String): ArrayList<String>{
        var list = ArrayList<String>()

        when (table) {
            "Clientes" -> {
                val data = base.getClientes(context).toList()
                list = ArrayList(data.map { "${it.CliCod} - ${it.CliNom.take(15)} - ${it.CliNumTel}" })
            }
            "Equipos" -> {
                val data = base.getEquipos(context).toList()
                list = ArrayList(data.map { "${it.EquCod} - ${it.EquNom.take(15)} - ${it.PaiCod}" })
            }
            "Tallas" -> {
                val data = base.getTallas(context).toList()
                list = ArrayList(data.map { "${it.TalCod} - ${it.TalDes}"})
            }
            "Países" -> {
                val data = base.getPaises(context).toList()
                list = ArrayList(data.map { "${it.PaiCod} - ${it.PaiNom.take(15)}"})
            }
            "Marcas" -> {
                val data = base.getMarcas(context).toList()
                list = ArrayList(data.map { "${it.MarCod} - ${it.MarNom.take(15)}"})
            }
        }

        return list
    }
}
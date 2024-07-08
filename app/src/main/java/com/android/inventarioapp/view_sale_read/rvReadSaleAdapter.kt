package com.android.inventarioapp.view_sale_read

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.inventarioapp.R
import com.android.inventarioapp.SQLManager
import com.android.inventarioapp.class_tables.SalidaDetalle

class rvReadSaleAdapter(private var list: ArrayList<SalidaDetalle>) : RecyclerView.Adapter<ShirtViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShirtViewHolder {
        val context = parent.context
        return ShirtViewHolder(context, LayoutInflater.from(context).inflate(R.layout.item_recicler_read_sale, parent, false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ShirtViewHolder, position: Int) {
        holder.render(list[position])
    }
}

class ShirtViewHolder(private val context: Context, view: View) : RecyclerView.ViewHolder(view) {
    val txtShirt = view.findViewById<TextView>(R.id.txtShirt)
    val inputAmount = view.findViewById<TextView>(R.id.inputAmount)
    val inputPrice = view.findViewById<TextView>(R.id.inputPrice)
    val base = SQLManager(context)

    fun render(sDet: SalidaDetalle) {
        val shirt = base.getOneCamiseta(context,sDet.CamCod)?.CamNom
        txtShirt.text = shirt
        inputPrice.text = sDet.DetPre.toString()
        inputAmount.text = sDet.CanCam.toString()
    }
}

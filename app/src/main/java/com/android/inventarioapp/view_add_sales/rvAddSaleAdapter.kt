package com.android.inventarioapp.view_add_sales

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.inventarioapp.R
import com.android.inventarioapp.class_tables.Shirt
import com.google.android.material.textfield.TextInputEditText

class rvAddSaleAdapter(
    private var list: ArrayList<String>,
    private val onDelete: (String) -> Unit
) : RecyclerView.Adapter<ShirtViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShirtViewHolder {
        val context = parent.context
        return ShirtViewHolder(context, LayoutInflater.from(context).inflate(R.layout.item_recicler_view_sales, parent, false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ShirtViewHolder, position: Int) {
        holder.render(list[position], onDelete)
    }

    fun changeResult(listFilter: ArrayList<String>){
        this.list = listFilter
        notifyDataSetChanged()
    }

}

class ShirtViewHolder(private val context: Context, view: View) : RecyclerView.ViewHolder(view) {
    val txtShirt = view.findViewById<TextView>(R.id.txtShirt)
    val inputAmount = view.findViewById<TextInputEditText>(R.id.inputAmount)
    val inputPrice = view.findViewById<TextInputEditText>(R.id.inputPrice)
    val btnDelete = view.findViewById<Button>(R.id.btnDelete)


    fun render(shirt: String, onDelete: (String) -> Unit) {
        txtShirt.text = shirt
        inputPrice.setText("")
        inputAmount.setText("")
        btnDelete.setOnClickListener { onDelete(shirt) }
    }

    fun getNameShirt(): String {
        return txtShirt.text.toString()
    }

    fun getInputAmountValue(): String {
        return inputAmount.text.toString()
    }

    fun getInputPriceValue(): String {
        return inputPrice.text.toString()
    }
}

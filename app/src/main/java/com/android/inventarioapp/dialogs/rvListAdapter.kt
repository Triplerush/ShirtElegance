package com.android.inventarioapp.dialogs

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.inventarioapp.R

class rvListAdapter(private var list: ArrayList<String>, ) : RecyclerView.Adapter<ShirtViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShirtViewHolder {
        val context = parent.context
        return ShirtViewHolder(context, LayoutInflater.from(context).inflate(R.layout.item_recicler_list, parent, false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ShirtViewHolder, position: Int) {
        holder.render(list[position])
    }

}

class ShirtViewHolder(private val context: Context, view: View) : RecyclerView.ViewHolder(view) {
    val txtData = view.findViewById<TextView>(R.id.txtData)

    fun render(data: String) {
        txtData.text = data
    }
}

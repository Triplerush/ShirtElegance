package com.android.inventarioapp.view_delete_shirt

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.inventarioapp.R
import com.android.inventarioapp.class_tables.Shirt

class rvDeleteAdapter(
    private var list: ArrayList<Shirt>,
    private val onDelete: (Boolean, String) -> Unit
) : RecyclerView.Adapter<ShirtViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShirtViewHolder {
        val context = parent.context
        return ShirtViewHolder(context, LayoutInflater.from(context).inflate(R.layout.item_recicler_view_delete, parent, false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ShirtViewHolder, position: Int) {
        holder.render(list[position], onDelete)
    }

    fun changeResult(listFilter: ArrayList<Shirt>){
        this.list = listFilter
        notifyDataSetChanged()
    }
}

class ShirtViewHolder(private val context: Context, view: View) : RecyclerView.ViewHolder(view) {
    val txtName = view.findViewById<TextView>(R.id.txtShirt)
    val txtAmount = view.findViewById<TextView>(R.id.txtAmount)
    val imageShirt = view.findViewById<ImageView>(R.id.imageShirt)
    val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        android.Manifest.permission.READ_MEDIA_IMAGES
    else
        android.Manifest.permission.READ_EXTERNAL_STORAGE

    fun render(shirt: Shirt, onDelete: (Boolean, String) -> Unit) {
        txtName.text = shirt.CamNom
        txtAmount.text = "Cantidad: ${shirt.CamCan}"
        if (ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            imageShirt.setImageURI(Uri.parse(shirt.CamIma))
        }
        checkBox.setOnClickListener {  onDelete(checkBox.isChecked, shirt.CamCod) }
    }

}

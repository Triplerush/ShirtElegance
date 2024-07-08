import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.inventarioapp.R
import com.android.inventarioapp.class_tables.Shirt
import com.android.inventarioapp.view_shirt_read.ReadShirtActivity

class rvSearchAdapter(private var list: ArrayList<Shirt>) : RecyclerView.Adapter<ShirtViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShirtViewHolder {
        val context = parent.context
        return ShirtViewHolder(context, LayoutInflater.from(context).inflate(R.layout.item_recicler_view_search, parent, false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ShirtViewHolder, position: Int) {
        holder.render(list[position])
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
    val container = view.findViewById<LinearLayout>(R.id.container)
    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        android.Manifest.permission.READ_MEDIA_IMAGES
    else
        android.Manifest.permission.READ_EXTERNAL_STORAGE

    fun render(shirt: Shirt) {
        txtName.text = shirt.CamNom
        txtAmount.text = "Cantidad: ${shirt.CamCan}"
        if (ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            imageShirt.setImageURI(Uri.parse(shirt.CamIma))
        }
        container.setOnClickListener { navigateToReadShirtActivity(shirt.CamCod) }
    }

    fun navigateToReadShirtActivity(code: String) {
        val intent = Intent(context, ReadShirtActivity::class.java)
        intent.putExtra("EXTRA_TEXT",code)
        context.startActivity(intent)
    }


}

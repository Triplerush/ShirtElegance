package com.android.inventarioapp.view_delete_shirt

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.inventarioapp.R
import com.android.inventarioapp.SQLManager
import com.android.inventarioapp.class_tables.Shirt
import com.android.inventarioapp.dialogs.AceptDialog
import com.android.inventarioapp.view_menu_app.MenuActivity
import com.google.android.material.textfield.TextInputEditText

class DeleteShirtActivity : AppCompatActivity() {
    private lateinit var rvSearch: RecyclerView
    private lateinit var btnVolver: Button
    private lateinit var btnDelete: Button
    private lateinit var lista: ArrayList<Shirt>
    private lateinit var txtInputSearch: TextInputEditText

    lateinit var adapterShirtAction: rvDeleteAdapter
    lateinit var base: SQLManager

    val listaDelete = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_shirt)
        initComponents()
        initListeners()
        reviewPermission()
    }

    private fun initComponents() {
        base = SQLManager(this)

        txtInputSearch = findViewById(R.id.txtInputSearch)
        txtInputSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                filtrar(s.toString())
            }
        })

        rvSearch = findViewById(R.id.rvShirtsActions)
        btnDelete = findViewById(R.id.btnDelete)
        lista = ArrayList(base.getCamisetas(this).asList().filter { camiseta -> camiseta.EstCam == 1 })
        adapterShirtAction = rvDeleteAdapter(
            lista,
            { flag, cod -> selectedDelete(flag, cod) }
        )
        btnVolver = findViewById(R.id.btnVolver)

        rvSearch.apply {
            layoutManager = LinearLayoutManager(this@DeleteShirtActivity)
            adapter = adapterShirtAction
        }
    }

    private fun initListeners() {
        btnVolver.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        btnDelete.setOnClickListener {  mostrarDialog(AceptDialog(this, { flag -> deleteShirts(flag)})) }
    }

    private fun mostrarDialog(dialog: DialogFragment) {
        dialog.show(supportFragmentManager, "dialog")
    }

    private fun selectedDelete(flag : Boolean, cod: String) {
        if(flag){
            listaDelete.add(cod)
            return
        }
        listaDelete.remove(cod)
    }

    private fun deleteShirts(flag: Boolean) {
        if(flag){
            listaDelete.forEach { elemento ->
                base.deleteShirt(this,elemento)
            }
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finishAffinity()
            Toast.makeText(this@DeleteShirtActivity,"CAMISETAS ELIMINADAS CORRECTAMENTE", Toast.LENGTH_LONG).show()
        }
    }

    private fun filtrar(texto: String) {
        val listaFiltrada = ArrayList<Shirt>()
        lista.forEach {
            if (it.CamNom.lowercase().contains(texto.lowercase()))
                listaFiltrada.add(it)
        }
        adapterShirtAction.changeResult(listaFiltrada)
    }

    private fun reviewPermission() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            android.Manifest.permission.READ_MEDIA_IMAGES
        else
            android.Manifest.permission.READ_EXTERNAL_STORAGE

        if (ContextCompat.checkSelfPermission(
                this@DeleteShirtActivity,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val message = "Debes conceder el permiso para ver las imagenes de las camisetas"
            Toast.makeText(this@DeleteShirtActivity,message, Toast.LENGTH_LONG).show()
        }
    }
}

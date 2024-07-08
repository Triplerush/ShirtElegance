package com.android.inventarioapp.view_add_data

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.android.inventarioapp.MainActivity2
import com.android.inventarioapp.R
import com.android.inventarioapp.dialogs.ClientDialog
import com.android.inventarioapp.dialogs.ListDialog
import com.android.inventarioapp.dialogs.TallaDialog
import com.android.inventarioapp.dialogs.MarcaDialog
import com.android.inventarioapp.dialogs.PaisDialog
import com.android.inventarioapp.dialogs.TeamDialog


class AddDataActivity : AppCompatActivity() {
    lateinit var btnVolver: Button
    lateinit var btnAddTalla: Button
    lateinit var btnAddTeam: Button
    lateinit var btnAddMarca: Button
    lateinit var btnAddPais: Button
    lateinit var btnAddClient: Button
    lateinit var btnListClient: Button
    lateinit var btnListTeam: Button
    lateinit var btnListTalla: Button
    lateinit var btnListPais: Button
    lateinit var btnListMarca: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_data)
        initComponents()
        initListeners()
    }

    private fun initComponents() {
        btnVolver = findViewById(R.id.btnVolver)
        btnAddTalla = findViewById(R.id.btnAddTalla)
        btnAddTeam = findViewById(R.id.btnAddTeam)
        btnAddMarca = findViewById(R.id.btnAddMarca)
        btnAddPais = findViewById(R.id.btnAddPais)
        btnAddClient = findViewById(R.id.btnAddClient)
        btnListClient = findViewById(R.id.btnListClient)
        btnListTeam = findViewById(R.id.btnListTeam)
        btnListTalla = findViewById(R.id.btnListTalla)
        btnListPais = findViewById(R.id.btnListPais)
        btnListMarca = findViewById(R.id.btnListMarca)
    }

    private fun initListeners() {
        btnVolver.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        //btnViewBrand.setOnClickListener {
           // val intent = Intent(this, MainActivity2::class.java)
          //  startActivity(intent)
       // }

        btnAddTalla.setOnClickListener { mostrarDialog(TallaDialog(this, { flag -> viewMessage(flag)})) }
        btnAddMarca.setOnClickListener { mostrarDialog(MarcaDialog(this, { flag -> viewMessage(flag)})) }
        btnAddPais.setOnClickListener { mostrarDialog(PaisDialog(this, { flag -> viewMessage(flag)})) }
        btnAddClient.setOnClickListener { mostrarDialog(ClientDialog(this, { flag -> viewMessage(flag)}) )}
        btnAddTeam.setOnClickListener { mostrarDialog(TeamDialog(this, { flag -> viewMessage(flag)})) }

        btnListClient.setOnClickListener { mostrarListDialog("Clientes") }
        btnListTalla.setOnClickListener { mostrarListDialog("Tallas") }
        btnListPais.setOnClickListener { mostrarListDialog("Países") }
        btnListMarca.setOnClickListener { mostrarListDialog("Marcas") }
        btnListTeam.setOnClickListener { mostrarListDialog("Equipos") }
    }

    private fun viewMessage(flag : Boolean){
        val mensaje = if (flag) "DATO AGREGADO CORRECTAMENTE" else "DATO NO AGREGADO"
        Toast.makeText(this@AddDataActivity, mensaje, Toast.LENGTH_LONG).show()
    }

    fun mostrarListDialog(table: String) {
        ListDialog(this, table).show(supportFragmentManager, "dialog")
    }

    fun mostrarDialog(dialog: DialogFragment) {
        dialog.show(supportFragmentManager, "dialog")
    }
}
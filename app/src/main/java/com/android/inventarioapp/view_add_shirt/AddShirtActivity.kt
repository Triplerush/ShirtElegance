package com.android.inventarioapp.view_add_shirt

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.*
import com.android.inventarioapp.R
import com.android.inventarioapp.SQLManager
import com.android.inventarioapp.class_tables.Shirt
import com.android.inventarioapp.view_menu_app.MenuActivity
import com.google.android.material.textfield.TextInputEditText

class AddShirtActivity : AppCompatActivity() {
    private lateinit var sprTeam : Spinner
    private lateinit var sprTalla : Spinner
    private lateinit var sprMarca : Spinner
    private lateinit var btnImage : Button
    private lateinit var btnAceptar: Button
    private lateinit var btnCancelar: Button
    private lateinit var txtInputCode: TextInputEditText
    private lateinit var txtInputName: TextInputEditText
    private lateinit var txtInputPlayer: TextInputEditText
    private lateinit var txtInputTemp: TextInputEditText
    private lateinit var txtInputDorsal: TextInputEditText
    private lateinit var txtInputAmount: TextInputEditText
    private lateinit var uriImage: String

    lateinit var base: SQLManager

    var ListaEquipos = arrayOf("---  Elegir Equipo  ---")
    var ListaMarcas = arrayOf("Marca")
    var ListaTallas = arrayOf("Talla")


    private val pickMedia = registerForActivityResult(PickVisualMedia()){ uri ->
        if(uri != null){
            uriImage = uri.toString()
            btnImage.text = "Imagen"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_shirt)
        initComponents()
        initListeners()
    }

    private fun initComponents(){
        base = SQLManager(this)
        btnAceptar = findViewById(R.id.btnAceptar)
        btnCancelar = findViewById(R.id.btnCancelar)
        txtInputCode = findViewById(R.id.txtInputCode)
        txtInputName = findViewById(R.id.txtInputName)
        txtInputPlayer = findViewById(R.id.txtInputPlayer)
        txtInputTemp = findViewById(R.id.txtInputTemp)
        txtInputDorsal = findViewById(R.id.txtInputDorsal)
        txtInputAmount = findViewById(R.id.txtInputAmount)
        uriImage = ""

        sprTeam = findViewById(R.id.sprTeam)
        for (equipo in base.getEquipos(this)) {
            ListaEquipos = ListaEquipos.plus("${equipo.EquCod} - ${equipo.EquNom} - ${equipo.PaiCod}")
        }
        val adaptadorEquipos = ArrayAdapter<String>(this,R.layout.spinner_items,ListaEquipos)
        sprTeam.adapter = adaptadorEquipos

        sprTalla = findViewById(R.id.sprTalla)
        for (talla in base.getTallas(this)) {
            ListaTallas = ListaTallas.plus("${talla.TalCod} - ${talla.TalDes}")
        }
        val adaptadorTallas = ArrayAdapter<String>(this,R.layout.spinner_items,ListaTallas)
        sprTalla.adapter = adaptadorTallas

        sprMarca = findViewById(R.id.sprMarca)
        for (marca in base.getMarcas(this)) {
            ListaMarcas = ListaMarcas.plus("${marca.MarCod} - ${marca.MarNom}")
        }
        val adaptadorMarcas = ArrayAdapter<String>(this,R.layout.spinner_items,ListaMarcas)
        sprMarca.adapter = adaptadorMarcas
        btnImage = findViewById(R.id.btnImagen)

    }

    private fun initListeners() {
        btnImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        }

        btnCancelar.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        btnAceptar.setOnClickListener {
            addShirt()
        }
    }

    fun indexUri(str: String): String {
        val regex = Regex("\\d+$")
        val matchResult = regex.find(str)
        val lastNumber = matchResult?.value
        return "content://media/external/images/media/${lastNumber.toString()}"
    }

    private fun addShirt(){
        val inputValueCode = txtInputCode.text.toString()
        val inputValueName = txtInputName.text.toString()
        val inputValuePlayer = txtInputPlayer.text.toString()
        val inputValueTemp = txtInputTemp.text.toString()
        val inputValueDorsal = txtInputDorsal.text.toString()
        val inputValueAmount = txtInputAmount.text.toString()
        val selectedTeam = sprTeam.selectedItem.toString().split(" - ")[0]
        val selectedTalla = sprTalla.selectedItem.toString().split(" - ")[0]
        val selectedMarca = sprMarca.selectedItem.toString().split(" - ")[0]

        if (inputValueCode.isNotBlank() &&
            inputValueName.isNotBlank() &&
            inputValuePlayer.isNotBlank() &&
            inputValueTemp.isNotBlank() &&
            inputValueDorsal.isNotBlank() &&
            inputValueAmount.isNotBlank() &&
            selectedTeam.isNotBlank() &&
            selectedTalla.isNotBlank() &&
            selectedMarca.isNotBlank() &&
            uriImage.isNotBlank())
        {
            Toast.makeText(this@AddShirtActivity,"CAMISETA AGREGADA CORRECTAMENTE", Toast.LENGTH_LONG).show()
            base.addCamiseta(this, Shirt(inputValueCode,inputValueName,selectedTalla,inputValueDorsal.toInt(),inputValuePlayer,inputValueTemp,selectedTeam,inputValueAmount.toInt(),selectedMarca.toInt(),indexUri(uriImage)))
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finishAffinity()
        } else {
            Toast.makeText(this@AddShirtActivity,"FALTA AGREGAR ALGUN DATO", Toast.LENGTH_LONG).show()
        }
    }
}
package com.android.inventarioapp.view_edit_shirt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.android.inventarioapp.R
import com.android.inventarioapp.SQLManager
import com.android.inventarioapp.class_tables.Shirt
import com.android.inventarioapp.view_menu_app.MenuActivity
import com.google.android.material.textfield.TextInputEditText

class EditShirtActivity : AppCompatActivity() {
    private lateinit var sprTeam : Spinner
    private lateinit var sprTalla : Spinner
    private lateinit var sprMarca : Spinner
    private lateinit var btnImage : Button
    private lateinit var btnAceptar: Button
    private lateinit var btnCancelar: Button
    private lateinit var txtInputCode: TextView
    private lateinit var txtInputName: TextInputEditText
    private lateinit var txtInputPlayer: TextInputEditText
    private lateinit var txtInputTemp: TextInputEditText
    private lateinit var txtInputDorsal: TextInputEditText
    private lateinit var txtInputAmount: TextInputEditText
    private lateinit var uriImage: String

    lateinit var base: SQLManager
    private lateinit var code: String

    var ListaEquipos = arrayOf("---  Elegir Equipo  ---")
    var ListaMarcas = arrayOf("Marca")
    var ListaTallas = arrayOf("Talla")

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){ uri ->
        if(uri != null){
            uriImage = indexUri(uri.toString())
            btnImage.text = "Imagen"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_shirt)
        initComponents()
        initListeners()
    }

    private fun initComponents(){
        base = SQLManager(this)
        code = intent.extras?.getString("EXTRA_TEXT").orEmpty()

        val shirt = base.getOneCamiseta(this,code)
        val team = base.getOneEquipo(this,shirt!!.EquCod)
        val tal = base.getOneTalla(this,shirt.TalCod)
        val mark = base.getOneMarca(this,shirt.MarCod)

        for (equipo in base.getEquipos(this))
            ListaEquipos = ListaEquipos.plus("${equipo.EquCod} - ${equipo.EquNom} - ${equipo.PaiCod}")
        for (talla in base.getTallas(this))
            ListaTallas = ListaTallas.plus("${talla.TalCod} - ${talla.TalDes}")
        for (marca in base.getMarcas(this))
            ListaMarcas = ListaMarcas.plus("${marca.MarCod} - ${marca.MarNom}")

        val indiceTeamSeleccionado = ListaEquipos.indexOf("${team?.EquCod} - ${team?.EquNom} - ${team?.PaiCod}")
        val indiceTallaSeleccionado = ListaTallas.indexOf("${tal?.TalCod} - ${tal?.TalDes}")
        val indiceMarcaSeleccionado = ListaMarcas.indexOf("${mark?.MarCod} - ${mark?.MarNom}")

        btnAceptar = findViewById(R.id.btnAceptar)
        btnCancelar = findViewById(R.id.btnCancelar)

        txtInputCode = findViewById(R.id.txtInputCode)
        txtInputCode.text = shirt?.CamCod
        txtInputName = findViewById(R.id.txtInputName)
        txtInputName.setText(shirt?.CamNom)
        txtInputPlayer = findViewById(R.id.txtInputPlayer)
        txtInputPlayer.setText(shirt?.CamNomJug)
        txtInputTemp = findViewById(R.id.txtInputTemp)
        txtInputTemp.setText(shirt?.CamTem)
        txtInputDorsal = findViewById(R.id.txtInputDorsal)
        txtInputDorsal.setText(shirt?.CamDor.toString())
        txtInputAmount = findViewById(R.id.txtInputAmount)
        txtInputAmount.setText(shirt?.CamCan.toString())

        uriImage = shirt.CamIma

        sprTeam = findViewById(R.id.sprTeam)
        val adaptadorEquipos = ArrayAdapter<String>(this,R.layout.spinner_items,ListaEquipos)
        sprTeam.adapter = adaptadorEquipos
        sprTeam.setSelection(indiceTeamSeleccionado)

        sprTalla = findViewById(R.id.sprTalla)
        val adaptadorTallas = ArrayAdapter<String>(this,R.layout.spinner_items,ListaTallas)
        sprTalla.adapter = adaptadorTallas
        sprTalla.setSelection(indiceTallaSeleccionado)

        sprMarca = findViewById(R.id.sprMarca)
        val adaptadorMarcas = ArrayAdapter<String>(this,R.layout.spinner_items,ListaMarcas)
        sprMarca.adapter = adaptadorMarcas
        sprMarca.setSelection(indiceMarcaSeleccionado)

        btnImage = findViewById(R.id.btnImagen)
    }

    private fun initListeners(){
        btnImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        btnCancelar.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        btnAceptar.setOnClickListener {
            addShirt()
        }
    }

    private fun addShirt() {
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
            selectedMarca.isNotBlank())
        {
            Toast.makeText(this@EditShirtActivity,"CAMISETA MODIFICADA CORRECTAMENTE", Toast.LENGTH_LONG).show()
            base.updateShirt(this, Shirt(inputValueCode,inputValueName,selectedTalla,inputValueDorsal.toInt(),inputValuePlayer,inputValueTemp,selectedTeam,inputValueAmount.toInt(),selectedMarca.toInt(),indexUri(uriImage)))
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finishAffinity()
        } else {
            Toast.makeText(this@EditShirtActivity,"FALTA AGREGAR ALGUN DATO", Toast.LENGTH_LONG).show()
        }
    }

    fun indexUri(str: String): String {
        val regex = Regex("\\d+$")
        val matchResult = regex.find(str)
        val lastNumber = matchResult?.value
        return "content://media/external/images/media/${lastNumber.toString()}"
    }
}
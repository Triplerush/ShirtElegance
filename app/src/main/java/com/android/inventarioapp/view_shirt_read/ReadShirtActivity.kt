package com.android.inventarioapp.view_shirt_read

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.android.inventarioapp.R
import com.android.inventarioapp.SQLManager
import com.android.inventarioapp.view_edit_shirt.EditShirtActivity
import com.android.inventarioapp.view_search_shirts.SearchShirtActivity

class ReadShirtActivity : AppCompatActivity() {
    private lateinit var img: ImageView
    private lateinit var txtNameShirt: TextView
    private lateinit var txtCodeShirt: TextView
    private lateinit var txtAmount: TextView
    private lateinit var txtInputPlayer: TextView
    private lateinit var txtInputTeam: TextView
    private lateinit var txtInputTemp: TextView
    private lateinit var txtInputDorsal: TextView
    private lateinit var txtInputTalla: TextView
    private lateinit var btnVolver: Button
    private lateinit var btnEdit: Button


    private lateinit var code: String
    lateinit var base: SQLManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_shirt)
        initComponents()
        initListeners()
    }

    private fun initComponents(){
        img = findViewById(R.id.imgShirt)

        base = SQLManager(this)

        code = intent.extras?.getString("EXTRA_TEXT").orEmpty()

        txtNameShirt = findViewById(R.id.txtNameShirt)
        txtCodeShirt = findViewById(R.id.txtCodeShirt)
        txtAmount = findViewById(R.id.txtAmount)
        txtInputPlayer = findViewById(R.id.txtInputPlayer)
        txtInputTeam = findViewById(R.id.txtInputTeam)
        txtInputTemp = findViewById(R.id.txtInputTemp)
        txtInputDorsal = findViewById(R.id.txtInputDorsal)
        txtInputTalla = findViewById(R.id.txtInputTalla)
        btnVolver = findViewById(R.id.btnVolver)
        btnEdit = findViewById(R.id.btnEdit)

        val shirt = base.getOneCamiseta(this,code)
        val equipo = base.getOneEquipo(this,shirt!!.EquCod)?.EquNom
        val talla = base.getOneTalla(this,shirt.TalCod)?.TalDes

        txtInputTeam
        img.setImageURI(Uri.parse(shirt?.CamIma))
        txtNameShirt.text = shirt?.CamNom
        txtCodeShirt.text = "Código: ${shirt?.CamCod}"
        txtAmount.text = "Stock: ${shirt?.CamCan}"
        txtInputPlayer.text = shirt.CamNomJug
        txtInputTeam.text = equipo
        txtInputTemp.text = shirt.CamTem
        txtInputDorsal.text = shirt.CamDor.toString()
        txtInputTalla.text = "${shirt.TalCod} - $talla"

    }

    private fun initListeners(){
        btnVolver.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        btnEdit.setOnClickListener { navigateToEditShirt() }
    }

    private fun navigateToEditShirt() {
        val intent = Intent(this, EditShirtActivity::class.java)
        intent.putExtra("EXTRA_TEXT",code)
        startActivity(intent)
    }
}
package com.android.inventarioapp.view_sales

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.inventarioapp.R
import com.android.inventarioapp.SQLManager
import com.android.inventarioapp.class_tables.SalidaCabecera
import com.android.inventarioapp.class_tables.Shirt
import com.android.inventarioapp.view_add_sales.AddSalesActivity
import com.android.inventarioapp.view_delete_shirt.rvDeleteAdapter
import com.android.inventarioapp.view_menu_app.MenuActivity
import com.google.android.material.textfield.TextInputEditText

class ViewSalesActivity : AppCompatActivity() {
    private lateinit var base: SQLManager
    private lateinit var btnVolver: Button
    private lateinit var btnAdd: Button
    private lateinit var rvSales: RecyclerView
    private lateinit var lista: ArrayList<SalidaCabecera>
    private lateinit var txtInputSearch: TextInputEditText
    lateinit var adapterSales: rvViewSales


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_sales)
        initComponents()
        initListeners()
    }

    private fun initComponents() {
        base = SQLManager(this)
        rvSales = findViewById(R.id.rvSales)
        btnVolver = findViewById(R.id.btnVolver)
        btnAdd = findViewById(R.id.btnAdd)
        lista = ArrayList(base.getSalidasCabeceras(this).asList())
        adapterSales = rvViewSales(lista)
        rvSales.apply {
            layoutManager = LinearLayoutManager(this@ViewSalesActivity)
            adapter = adapterSales
        }

        txtInputSearch = findViewById(R.id.txtInputSearch)
    }

    private fun initListeners() {
        txtInputSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                filtrar(s.toString())
            }
        })

        btnVolver.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        btnAdd.setOnClickListener {
            val intent = Intent(this, AddSalesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun filtrar(texto: String) {
        val listaFiltrada = ArrayList<SalidaCabecera>()
        lista.forEach {
            if (it.SalCabNum.toString().contains(texto))
                listaFiltrada.add(it)
        }
        adapterSales.changeResult(listaFiltrada)
    }
}
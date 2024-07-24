package com.android.inventarioapp.view_sales

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.inventarioapp.R
import com.android.inventarioapp.SQLManager
import com.android.inventarioapp.class_tables.SalidaCabecera
import com.android.inventarioapp.view_add_sales.AddSalesActivity
import com.google.android.material.textfield.TextInputEditText

class ViewSalesActivity : AppCompatActivity() {
    private lateinit var base: SQLManager
    private lateinit var btnVolver: Button
    private lateinit var btnAdd: Button
    private lateinit var rvSales: RecyclerView
    private lateinit var lista: ArrayList<SalidaCabecera>
    private lateinit var txtInputSearch: TextInputEditText
    lateinit var adapterSales: rvViewSales

    private val handler = Handler(Looper.getMainLooper())

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
        txtInputSearch = findViewById(R.id.txtInputSearch)

        // Inicializar el RecyclerView sin datos
        adapterSales = rvViewSales(ArrayList())
        rvSales.apply {
            layoutManager = LinearLayoutManager(this@ViewSalesActivity)
            adapter = adapterSales
        }

        // Cargar los datos en segundo plano
        loadDataInBackground()
    }

    private fun initListeners() {
        txtInputSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                s?.let { searchInBackground(it.toString()) }
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

    private fun loadDataInBackground() {
        Thread {
            // Cargar los datos en segundo plano
            lista = ArrayList(base.getSalidasCabeceras(this).asList())

            // Actualizar la UI en el hilo principal
            handler.post {
                adapterSales.changeResult(lista)
            }
        }.start()
    }

    private fun searchInBackground(query: String) {
        Thread {
            // Filtrar la lista en segundo plano
            val listaFiltrada = lista.filter {
                it.SalCabNum.toString().contains(query)
            }

            // Actualizar la UI en el hilo principal
            handler.post {
                adapterSales.changeResult(ArrayList(listaFiltrada))
            }
        }.start()
    }
}

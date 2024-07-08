package com.android.inventarioapp.view_add_sales

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.inventarioapp.R
import com.android.inventarioapp.SQLManager
import com.android.inventarioapp.class_tables.SalidaCabecera
import com.android.inventarioapp.class_tables.SalidaDetalle
import com.android.inventarioapp.class_tables.Shirt
import com.android.inventarioapp.view_menu_app.MenuActivity
import java.util.Calendar
import com.google.android.material.textfield.TextInputEditText

class AddSalesActivity : AppCompatActivity() {
    private lateinit var rvShirts: RecyclerView
    private lateinit var btnCancelar: Button
    private lateinit var btnAceptar: Button
    private lateinit var btnCalcular: Button
    private lateinit var txtInputCode: TextInputEditText
    private lateinit var inputPrice: TextView
    private lateinit var inputAmount: TextView
    private lateinit var sprCodeshirt: Spinner
    private lateinit var sprCliente: Spinner
    private lateinit var txtInputYear: TextView
    private lateinit var txtInputMonth: TextView
    private lateinit var txtInputDay: TextView
    private lateinit var txtTitleMenu: TextView

    private lateinit var lista: ArrayList<String>
    private lateinit var adapterShirtAction: rvAddSaleAdapter
    private lateinit var base: SQLManager

    var ListaShirts = arrayOf("Camisetas")
    var ListaClientes = arrayOf("Cliente")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_sales)

        initComponents()
        initListeners()

    }

    private fun initComponents() {
        base = SQLManager(this)
        rvShirts = findViewById(R.id.rvShirts)
        btnAceptar = findViewById(R.id.btnAceptar)
        txtInputCode = findViewById(R.id.txtInputCode)
        btnCalcular = findViewById(R.id.btnCalcular)
        sprCodeshirt = findViewById(R.id.sprCodeshirt)
        sprCliente = findViewById(R.id.sprCliente)
        txtInputYear = findViewById(R.id.txtInputYear)
        txtTitleMenu = findViewById(R.id.txtTitleMenu)
        txtInputMonth = findViewById(R.id.txtInputMonth)
        txtInputDay = findViewById(R.id.txtInputDay)
        inputPrice = findViewById(R.id.inputPrice)
        inputAmount = findViewById(R.id.inputAmount)
        obtenerFechaActual()

        for (shirt in base.getCamisetas(this).asList()
            .filter { camiseta -> camiseta.EstCam == 1 }) {
            ListaShirts = ListaShirts.plus("${shirt.CamCod} - ${shirt.CamNom}")
        }
        val adaptadorShirt = ArrayAdapter<String>(this, R.layout.spinner_items, ListaShirts)
        sprCodeshirt.adapter = adaptadorShirt

        for (cliente in base.getClientes(this)) {
            ListaClientes = ListaClientes.plus("${cliente.CliCod} - ${cliente.CliNom}")
        }
        val adaptadorClientes = ArrayAdapter<String>(this, R.layout.spinner_items, ListaClientes)
        sprCliente.adapter = adaptadorClientes
        lista = ArrayList()
        adapterShirtAction = rvAddSaleAdapter(
            lista,
            { cod -> filtrar(cod) }
        )
        btnCancelar = findViewById(R.id.btnCancelar)
        rvShirts.apply {
            layoutManager = LinearLayoutManager(this@AddSalesActivity)
            adapter = adapterShirtAction
        }
    }

    private fun initListeners() {
        btnCancelar.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        sprCodeshirt.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                if (selectedItem != "Camisetas" && !lista.contains(selectedItem)) {
                    lista.add(selectedItem)
                    adapterShirtAction.notifyItemInserted(lista.size - 1)
                    sprCodeshirt.setSelection(0)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        btnCalcular.setOnClickListener {
            calculatePrice()
            calculateAmount()
        }

        btnAceptar.setOnClickListener {
            addSaleCab()
        }
    }

    private fun addSaleCab() {
        calculatePrice()
        calculateAmount()

        val code = txtInputCode.text.toString()
        val amount = inputAmount.text.toString()
        val price = inputPrice.text.toString()
        val client = sprCliente.selectedItem.toString()

        if (code.isNotBlank() && amount.isNotBlank() && price.isNotBlank() && client != "Cliente") {
            val year = txtInputYear.text.toString()
            val month = txtInputMonth.text.toString()
            val day = txtInputDay.text.toString()
            val clientCode = client.split(" - ")[0]
            val camCod = verifySales(code.toInt())

            if (camCod == "") {
                createSales(code.toInt())
                base.addSalidaCab(
                    this, SalidaCabecera(
                        code.toInt(),
                        year.toInt(),
                        month.toInt(),
                        day.toInt(),
                        price.toFloat(),
                        clientCode
                    )
                )
                Toast.makeText(this@AddSalesActivity, "Venta Agregada", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
                finishAffinity()
            } else {
                val shirt = base.getOneCamiseta(this, camCod)
                val nombre = shirt?.CamNom
                val amount = shirt?.CamCan
                Toast.makeText(
                    this@AddSalesActivity,
                    "LA CAMISETA $nombre solo tiene $amount unidades",
                    Toast.LENGTH_LONG
                ).show()
            }
        } else {
            Toast.makeText(this@AddSalesActivity, "FALTA AGREGAR ALGUN DATO", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun createSales(cabCod: Int) {
        for (i in 0 until adapterShirtAction.itemCount) {
            val shirtViewHolder = rvShirts.findViewHolderForAdapterPosition(i) as ShirtViewHolder?
            val nameShirt = shirtViewHolder?.getNameShirt()?.split(" - ")
            val codShirt = nameShirt?.get(0)
            val inputAmountValue = shirtViewHolder?.getInputAmountValue()
            val inputPriceValue = shirtViewHolder?.getInputPriceValue()
            val shirt = base.getOneCamiseta(this, codShirt.toString())!!
            shirt.CamCan -= inputAmountValue!!.toInt()

            base.updateShirt(this, shirt)
            base.addSalidaDet(
                this, SalidaDetalle(
                    ("$cabCod$i").toInt(),
                    cabCod,
                    codShirt.toString(),
                    inputAmountValue!!.toInt(),
                    inputPriceValue!!.toFloat()
                )
            )
        }
    }

    private fun verifySales(cabCod: Int): String {
        for (i in 0 until adapterShirtAction.itemCount) {
            val shirtViewHolder = rvShirts.findViewHolderForAdapterPosition(i) as ShirtViewHolder?
            val nameShirt = shirtViewHolder?.getNameShirt()?.split(" - ")
            val codShirt = nameShirt?.get(0)
            val inputAmountValue = shirtViewHolder?.getInputAmountValue()

            if (!isAvailable(codShirt.toString(), inputAmountValue.toString())) {
                return codShirt.toString()
            }
        }
        return ""
    }

    private fun isAvailable(cod: String, amount: String): Boolean {
        val shirt = base.getOneCamiseta(this, cod)
        return shirt?.CamCan!! >= amount.toInt()
    }

    private fun filtrar(texto: String) {
        val index = lista.indexOf(texto)

        if (index != -1) {
            lista.removeAt(index)
            adapterShirtAction.notifyItemRemoved(index)

            // Notificar al adaptador que los elementos han cambiado en el rango afectado
            adapterShirtAction.notifyItemRangeChanged(index, lista.size - index)
        }
    }



    private fun calculatePrice() {
        if (adapterShirtAction.itemCount == 0) {
            inputPrice.text = ""
            return
        }

        var price: Double = 0.0;
        for (i in 0 until adapterShirtAction.itemCount) {
            val shirtViewHolder = rvShirts.findViewHolderForAdapterPosition(i) as ShirtViewHolder?
            val inputPriceValue = shirtViewHolder?.getInputPriceValue()
            val inputAmountValue = shirtViewHolder?.getInputAmountValue()
            if (inputPriceValue.isNullOrBlank()) {
                Toast.makeText(
                    this@AddSalesActivity,
                    "FALTA PONER PRECIO A ALGUNA CAMISETA",
                    Toast.LENGTH_LONG
                ).show()
                return
            }
            price += inputPriceValue.toDouble() * inputAmountValue!!.toDouble()
        }
        inputPrice.text = price.toString();
    }

    private fun calculateAmount() {
        if (adapterShirtAction.itemCount == 0) {
            inputAmount.text = ""
            return
        }

        var amount = 0;
        for (i in 0 until adapterShirtAction.itemCount) {
            val shirtViewHolder = rvShirts.findViewHolderForAdapterPosition(i) as ShirtViewHolder?
            val inputAmountValue = shirtViewHolder?.getInputAmountValue()
            if (inputAmountValue.isNullOrBlank()) {
                Toast.makeText(
                    this@AddSalesActivity,
                    "FALTA PONER LA CANTIDAD DE ALGUNA CAMISETA",
                    Toast.LENGTH_LONG
                ).show()
                return
            }
            amount += inputAmountValue?.toInt() ?: 0;
        }
        inputAmount.text = amount.toString();
    }

    fun obtenerFechaActual() {
        val cal = Calendar.getInstance()
        val anio = cal.get(Calendar.YEAR)
        val mes = cal.get(Calendar.MONTH) + 1 // Los meses comienzan desde 0
        val dia = cal.get(Calendar.DAY_OF_MONTH)

        txtInputYear.text = anio.toString()
        txtInputMonth.text = mes.toString()
        txtInputDay.text = dia.toString()
    }
}
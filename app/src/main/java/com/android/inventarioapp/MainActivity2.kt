package com.android.inventarioapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.android.inventarioapp.class_tables.Cliente
import com.android.inventarioapp.class_tables.Marca
import com.android.inventarioapp.class_tables.Equipo
import com.android.inventarioapp.class_tables.Pais
import com.android.inventarioapp.class_tables.SalidaCabecera
import com.android.inventarioapp.class_tables.SalidaDetalle
import com.android.inventarioapp.class_tables.Shirt
import com.android.inventarioapp.class_tables.Talla


class MainActivity2 : AppCompatActivity() {
    lateinit var btn: Button
    lateinit var base: SQLManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        btn = findViewById(R.id.button)
        base = SQLManager(this)
        btn.setOnClickListener { addInfo()}
    }

    private fun addInfo() {
        val dbManager = SQLManager(this)
        val marcasData = listOf(
            Marca(1, "Adidas"),
            Marca(2, "Nike"),
            Marca(3, "Puma"),
            Marca(4, "Reebok"),
            Marca(5, "Under Armour"),
            Marca(6, "New Balance"),
            Marca(7, "Converse"),
            Marca(8, "Vans"),
            Marca(9, "Fila"),
            Marca(10, "Sketchers")
        )

        for (marca in marcasData) {
            dbManager.addMarca(this, marca)
        }

        val tallasData = listOf(
            Talla("S", "Small"),
            Talla("M", "Medium"),
            Talla("L", "Large"),
            Talla("XL", "Extra Large"),
            Talla("XXL", "Double Extra Large"),
            Talla("3XL", "Triple Extra Large"),
            Talla("4XL", "Quadruple Extra Large"),
            Talla("5XL", "Quintuple Extra Large"),
            Talla("6XL", "Sextuple Extra Large"),
            Talla("7XL", "Septuple Extra Large")
        )

        for (talla in tallasData) {
            dbManager.addTalla(this, talla)
        }

        val equiposData = listOf(
            Equipo("001", "TeamA", 1),
            Equipo("002", "TeamB", 2),
            Equipo("003", "TeamC", 1),
            Equipo("004", "TeamD", 2),
            Equipo("005", "TeamE", 1),
            Equipo("006", "TeamF", 2),
            Equipo("007", "TeamG", 1),
            Equipo("008", "TeamH", 2),
            Equipo("009", "TeamI", 1),
            Equipo("010", "TeamJ", 2)
        )

        for (equipo in equiposData) {
            dbManager.addEquipo(this, equipo)
        }

        val paisesData = listOf(
            Pais(1, "CountryA"),
            Pais(2, "CountryB"),
            Pais(3, "CountryC"),
            Pais(4, "CountryD"),
            Pais(5, "CountryE"),
            Pais(6, "CountryF"),
            Pais(7, "CountryG"),
            Pais(8, "CountryH"),
            Pais(9, "CountryI"),
            Pais(10, "CountryJ")
        )

        for (pais in paisesData) {
            dbManager.addPais(this, pais)
        }

        val camisetasData = listOf(
            Shirt("001", "JerseyA", "M", 1, "PlayerA", "ThemeA", "001", 10, 1, "ImageURL1"),
            Shirt("002", "JerseyB", "L", 2, "PlayerB", "ThemeB", "002", 15, 2, "ImageURL2"),
            Shirt("003", "JerseyC", "XL", 1, "PlayerC", "ThemeC", "003", 12, 1, "ImageURL3"),
            Shirt("004", "JerseyD", "S", 2, "PlayerD", "ThemeD", "004", 8, 2, "ImageURL4"),
            Shirt("005", "JerseyE", "XXL", 1, "PlayerE", "ThemeE", "005", 20, 1, "ImageURL5"),
            Shirt("006", "JerseyF", "L", 2, "PlayerF", "ThemeF", "006", 18, 2, "ImageURL6"),
            Shirt("007", "JerseyG", "M", 1, "PlayerG", "ThemeG", "007", 14, 1, "ImageURL7"),
            Shirt("008", "JerseyH", "S", 2, "PlayerH", "ThemeH", "008", 9, 2, "ImageURL8"),
            Shirt("009", "JerseyI", "XL", 1, "PlayerI", "ThemeI", "009", 16, 1, "ImageURL9"),
            Shirt("010", "JerseyJ", "XXL", 2, "PlayerJ", "ThemeJ", "010", 22, 2, "ImageURL10")
        )

        for (camiseta in camisetasData) {
            dbManager.addCamiseta(this, camiseta)
        }

        val clientesData = listOf(
            Cliente("C001", "ClientA", "1234567890"),
            Cliente("C002", "ClientB", "9876543210"),
            Cliente("C003", "ClientC", "5555555555"),
            Cliente("C004", "ClientD", "1111111111"),
            Cliente("C005", "ClientE", "9999999999"),
            Cliente("C006", "ClientF", "4444444444"),
            Cliente("C007", "ClientG", "6666666666"),
            Cliente("C008", "ClientH", "3333333333"),
            Cliente("C009", "ClientI", "7777777777"),
            Cliente("C010", "ClientJ", "8888888888")
        )

        for (cliente in clientesData) {
            dbManager.addCliente(this, cliente)
        }
    }
}


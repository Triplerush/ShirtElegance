package com.android.inventarioapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.android.inventarioapp.class_tables.Marca
import com.android.inventarioapp.class_tables.Cliente
import com.android.inventarioapp.class_tables.Equipo
import com.android.inventarioapp.class_tables.Pais
import com.android.inventarioapp.class_tables.Shirt
import com.android.inventarioapp.class_tables.Talla
import com.android.inventarioapp.class_tables.SalidaCabecera
import com.android.inventarioapp.class_tables.SalidaDetalle

class SQLManager(context: Context): SQLiteOpenHelper(context, "bd_inventario.db", null,1){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(sqlQueryMarcas)
        db?.execSQL(sqlQueryTallas)
        db?.execSQL(sqlQueryEquipos)
        db?.execSQL(sqlQueryPaises)
        db?.execSQL(sqlQueryCamisetas)
        db?.execSQL(sqlQuerySalidasDet)
        db?.execSQL(sqlQueryCliente)
        db?.execSQL(sqlQuerySalidasCab)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun addMarca(context: Context, marca: Marca): Boolean {
        var response = false
        val contentValues = ContentValues()
        contentValues.put("MarCod", marca.MarCod)
        contentValues.put("MarNom", marca.MarNom)
        val db = SQLManager(context)
        val manager = db.writableDatabase
        try {
            manager.insert("Marcas", null, contentValues)
            response = true
        } catch (e: Exception) {
            print(e.message)
        } finally {
            db.close()
        }
        return response
    }

    fun getOneMarca(context: Context, cod: Int): Marca? {
        val db = SQLManager(context)
        val manager = db.readableDatabase
        val query = "SELECT * FROM MARCAS WHERE MarCod = $cod"
        val cursor = manager.rawQuery(query, null)

        var marca: Marca? = null

        if (cursor.moveToFirst()) {
            marca = Marca(cursor.getInt(0), cursor.getString(1))
        }

        cursor.close()

        return marca
    }

    fun updateMarca(context: Context, marca: Marca): Boolean {
        var response = true
        val args = arrayOf(marca.MarCod.toString())

        val contentValues = ContentValues()
        contentValues.put("MarNom", marca.MarNom)
        val db = SQLManager(context)
        val manager = db.writableDatabase
        try {
            manager.update("Marcas", contentValues,"MarCod = ?", args)
        } catch (e: Exception) {
            print(e.message)
            response = false
        } finally {
            db.close()
        }
        return response
    }

    fun deleteData(context: Context, code: String, attribute: String, table: String): Boolean {
        var response = true
        val args = arrayOf(code)
        val db = SQLManager(context)
        val manager = db.writableDatabase
        try {
            manager.delete(table,"$attribute = ?", args)
        } catch (e: Exception) {
            print(e.message)
            response = false
        } finally {
            db.close()
        }
        return response
    }

    fun getMarcas(context: Context): Array<Marca> {
        var listaMarcas: Array<Marca> = emptyArray()
        val db = SQLManager(context)
        val manager = db.readableDatabase
        val query = "SELECT * FROM MARCAS"
        val cursor = manager.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val marca = Marca(cursor.getInt(0), cursor.getString(1))
                listaMarcas = listaMarcas.plus(marca)
            } while (cursor.moveToNext())
        }

        if (cursor != null && !cursor.isClosed) {
            cursor.close()
        }

        return listaMarcas
    }

    fun addTalla(context: Context, talla: Talla): Boolean {
        var response = true
        val contentValues = ContentValues()
        contentValues.put("TalCod", talla.TalCod)
        contentValues.put("TalDes", talla.TalDes)
        val db = SQLManager(context)
        val manager = db.writableDatabase
        try {
            manager.insert("Tallas", null, contentValues)
        } catch (e: Exception) {
            print(e.message)
            response = false
        } finally {
            db.close()
        }
        return response
    }

    fun getTallas(context: Context): Array<Talla> {
        var listaTallas: Array<Talla> = emptyArray()
        val db = SQLManager(context)
        val manager = db.readableDatabase
        val query = "SELECT * FROM Tallas"
        val cursor = manager.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val talla = Talla(cursor.getString(0), cursor.getString(1))
                listaTallas = listaTallas.plus(talla)
            } while (cursor.moveToNext())
        }

        if (cursor != null && !cursor.isClosed) {
            cursor.close()
        }

        return listaTallas
    }

    fun getOneTalla(context: Context, cod: String): Talla? {
        val db = SQLManager(context)
        val manager = db.readableDatabase
        val query = "SELECT * FROM Tallas WHERE TalCod = '$cod'"
        val cursor = manager.rawQuery(query, null)

        var talla: Talla? = null

        if (cursor.moveToFirst()) {
            talla = Talla(cursor.getString(0), cursor.getString(1))
        }

        cursor.close()

        return talla
    }

    fun addEquipo(context: Context, equipo: Equipo): Boolean {
        var response = true
        val contentValues = ContentValues()
        contentValues.put("EquCod", equipo.EquCod)
        contentValues.put("EquNom", equipo.EquNom)
        contentValues.put("PaiCod", equipo.PaiCod)
        val db = SQLManager(context)
        val manager = db.writableDatabase
        try {
            manager.insert("Equipos", null, contentValues)
        } catch (e: Exception) {
            print(e.message)
            response = false
        } finally {
            db.close()
        }
        return response
    }

    fun getOneEquipo(context: Context, cod: String): Equipo? {
        val db = SQLManager(context)
        val manager = db.readableDatabase
        val query = "SELECT * FROM Equipos WHERE EquCod = '$cod'"
        val cursor = manager.rawQuery(query, null)

        var equipo: Equipo? = null

        if (cursor.moveToFirst()) {
            equipo = Equipo(cursor.getString(0), cursor.getString(1), cursor.getInt(2))
        }

        cursor.close()

        return equipo
    }

    fun getEquipos(context: Context): Array<Equipo> {
        var listaEquipos: Array<Equipo> = emptyArray()
        val db = SQLManager(context)
        val manager = db.readableDatabase
        val query = "SELECT * FROM Equipos"
        val cursor = manager.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val equipo = Equipo(cursor.getString(0), cursor.getString(1), cursor.getInt(2))
                listaEquipos = listaEquipos.plus(equipo)
            } while (cursor.moveToNext())
        }

        if (cursor != null && !cursor.isClosed) {
            cursor.close()
        }

        return listaEquipos
    }

    fun addPais(context: Context,pais: Pais): Boolean {
        var response = true
        val contentValues = ContentValues()
        contentValues.put("PaiCod", pais.PaiCod)
        contentValues.put("PaiNom", pais.PaiNom)
        val db = SQLManager(context)
        val manager = db.writableDatabase
        try {
            manager.insert("Paises", null, contentValues)
        } catch (e: Exception) {
            print(e.message)
            response = false
        } finally {
            db.close()
        }
        return response
    }

    fun getOnePais(context: Context, cod: Int): Pais? {
        val db = SQLManager(context)
        val manager = db.readableDatabase
        val query = "SELECT * FROM Paises WHERE PaiCod = $cod"
        val cursor = manager.rawQuery(query, null)

        var pais: Pais? = null

        if (cursor.moveToFirst()) {
            pais = Pais(cursor.getInt(0), cursor.getString(1))
        }

        cursor.close()

        return pais
    }

    fun getPaises(context: Context): Array<Pais> {
        var listaPaises: Array<Pais> = emptyArray()
        val db = SQLManager(context)
        val manager = db.readableDatabase
        val query = "SELECT * FROM Paises"
        val cursor = manager.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val pais = Pais(cursor.getInt(0), cursor.getString(1))
                listaPaises = listaPaises.plus(pais)
            } while (cursor.moveToNext())
        }

        if (cursor != null && !cursor.isClosed) {
            cursor.close()
        }

        return listaPaises
    }

    fun addCamiseta(context: Context,shirt: Shirt ): Boolean {
        var response = true
        val contentValues = ContentValues()
        contentValues.put("CamCod", shirt.CamCod)
        contentValues.put("CamNom", shirt.CamNom)
        contentValues.put("TalCod", shirt.TalCod)
        contentValues.put("CamDor", shirt.CamDor)
        contentValues.put("CamNomJug", shirt.CamNomJug)
        contentValues.put("CamTem", shirt.CamTem)
        contentValues.put("EquCod", shirt.EquCod)
        contentValues.put("CamCan", shirt.CamCan)
        contentValues.put("MarCod", shirt.MarCod)
        contentValues.put("CamIma", shirt.CamIma)
        val db = SQLManager(context)
        val manager = db.writableDatabase
        try {
            manager.insert("Camisetas", null, contentValues)
        } catch (e: Exception) {
            print(e.message)
            response = false
        } finally {
            db.close()
        }
        return response
    }

    fun getOneCamiseta(context: Context, cod: String): Shirt? {
        val db = SQLManager(context)
        val manager = db.readableDatabase
        val query = "SELECT * FROM Camisetas WHERE CamCod = '$cod'"
        val cursor = manager.rawQuery(query, null)

        var camiseta: Shirt? = null

        if (cursor.moveToFirst()) {
            camiseta = Shirt(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getInt(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getInt(7),
                cursor.getInt(8),
                cursor.getString(9),
            )
        }
        cursor.close()

        return camiseta
    }

    fun updateShirt(context: Context, shirt: Shirt): Boolean {
        var response = true
        val args = arrayOf(shirt.CamCod)

        val contentValues = ContentValues()
        contentValues.put("CamNom", shirt.CamNom)
        contentValues.put("TalCod", shirt.TalCod)
        contentValues.put("CamDor", shirt.CamDor)
        contentValues.put("CamNomJug", shirt.CamNomJug)
        contentValues.put("CamTem", shirt.CamTem)
        contentValues.put("EquCod", shirt.EquCod)
        contentValues.put("CamCan", shirt.CamCan)
        contentValues.put("MarCod", shirt.MarCod)
        contentValues.put("CamIma", shirt.CamIma)

        val db = SQLManager(context)
        val manager = db.writableDatabase
        try {
            manager.update("Camisetas", contentValues, "CamCod = ?", args)
        } catch (e: Exception) {
            print(e.message)
            response = false
        } finally {
            db.close()
        }
        return response
    }

    fun deleteShirt(context: Context, codShirt: String): Boolean {
        var response = true
        val args = arrayOf(codShirt)
        val db = SQLManager(context)
        val manager = db.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("Estado", 0)
        try {
            manager.update("Camisetas", contentValues, "CamCod = ?", args)
        } catch (e: Exception) {
            print(e.message)
            response = false
        } finally {
            db.close()
        }
        return response
    }

    fun getCamisetas(context: Context): Array<Shirt> {
        var listaCamisetas: Array<Shirt> = emptyArray()
        val db = SQLManager(context)
        val manager = db.readableDatabase
        val query = "SELECT * FROM Camisetas"
        val cursor = manager.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val camiseta = Shirt(
                    cursor.getString(0), cursor.getString(1), cursor.getString(2),
                    cursor.getInt(3), cursor.getString(4), cursor.getString(5),
                    cursor.getString(6), cursor.getInt(7), cursor.getInt(8),
                    cursor.getString(9), cursor.getInt(10)
                )
                listaCamisetas = listaCamisetas.plus(camiseta)
            } while (cursor.moveToNext())
        }

        if (cursor != null && !cursor.isClosed) {
            cursor.close()
        }

        return listaCamisetas
    }

    fun addSalidaDet(context: Context, salidaDetalle: SalidaDetalle): Boolean {
        var response = true
        val contentValues = ContentValues()
        contentValues.put("SalDetCod", salidaDetalle.SalDetCod)
        contentValues.put("SalCabNum", salidaDetalle.SalCabNum)
        contentValues.put("CamCod", salidaDetalle.CamCod)
        contentValues.put("DetPre", salidaDetalle.DetPre)
        contentValues.put("CanCam", salidaDetalle.CanCam)
        val db = SQLManager(context)
        val manager = db.writableDatabase
        try {
            manager.insert("Salidas_det", null, contentValues)
        } catch (e: Exception) {
            print(e.message)
            response = false
        } finally {
            db.close()
        }
        return response
    }

    fun getOneSalidaDetalle(context: Context, cod: Int): SalidaDetalle? {
        val db = SQLManager(context)
        val manager = db.readableDatabase
        val query = "SELECT * FROM Salidas_det WHERE SalDetCod = $cod"
        val cursor = manager.rawQuery(query, null)

        var salidaDetalle: SalidaDetalle? = null

        if (cursor.moveToFirst()) {
            salidaDetalle = SalidaDetalle(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getString(2),
                cursor.getInt(3),
                cursor.getFloat(4)
            )
        }

        cursor.close()

        return salidaDetalle
    }

    fun getSalidasDetalles(context: Context, codCab : String): Array<SalidaDetalle> {
        var listaSalidasDetalles: Array<SalidaDetalle> = emptyArray()
        val db = SQLManager(context)
        val manager = db.readableDatabase
        val query = "SELECT * FROM Salidas_det WHERE SalCabNum = $codCab"
        val cursor = manager.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val salidaDetalle = SalidaDetalle(
                    cursor.getInt(0), cursor.getInt(1), cursor.getString(2),
                    cursor.getInt(3), cursor.getFloat(4)
                )
                listaSalidasDetalles = listaSalidasDetalles.plus(salidaDetalle)
            } while (cursor.moveToNext())
        }

        if (cursor != null && !cursor.isClosed) {
            cursor.close()
        }

        return listaSalidasDetalles
    }

    fun addCliente(context: Context, cliente: Cliente): Boolean {
        var response = true
        val contentValues = ContentValues()
        contentValues.put("CliCod", cliente.CliCod)
        contentValues.put("CliNom", cliente.CliNom)
        contentValues.put("CliNumTel", cliente.CliNumTel)
        val db = SQLManager(context)
        val manager = db.writableDatabase
        try {
            manager.insert("Cliente", null, contentValues)
        } catch (e: Exception) {
            print(e.message)
            response = false
        } finally {
            db.close()
        }
        return response
    }

    fun getOneCliente(context: Context, cod: String): Cliente? {
        val db = SQLManager(context)
        val manager = db.readableDatabase
        val query = "SELECT * FROM Cliente WHERE CliCod = '$cod'"
        val cursor = manager.rawQuery(query, null)

        var cliente: Cliente? = null

        if (cursor.moveToFirst()) {
            cliente = Cliente(cursor.getString(0), cursor.getString(1), cursor.getString(2))
        }

        cursor.close()

        return cliente
    }

    fun getClientes(context: Context): Array<Cliente> {
        var listaClientes: Array<Cliente> = emptyArray()
        val db = SQLManager(context)
        val manager = db.readableDatabase
        val query = "SELECT * FROM Cliente"
        val cursor = manager.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val cliente = Cliente(cursor.getString(0), cursor.getString(1), cursor.getString(2))
                listaClientes = listaClientes.plus(cliente)
            } while (cursor.moveToNext())
        }

        if (cursor != null && !cursor.isClosed) {
            cursor.close()
        }

        return listaClientes
    }

    fun addSalidaCab(context: Context, salidaCabecera: SalidaCabecera): Boolean {
        var response = true
        val contentValues = ContentValues()
        contentValues.put("SalCabNum", salidaCabecera.SalCabNum)
        contentValues.put("SalCabYear", salidaCabecera.SalCabYear)
        contentValues.put("SalCabMon", salidaCabecera.SalCabMon)
        contentValues.put("SalCabDay", salidaCabecera.SalCabDay)
        contentValues.put("CabPre", salidaCabecera.CabPre)
        contentValues.put("SalCabCli", salidaCabecera.SalCabCli)

        val db = SQLManager(context)
        val manager = db.writableDatabase

        try {
            manager.insert("Salidas_cab", null, contentValues)
        } catch (e: Exception) {
            print(e.message)
            response = false
        } finally {
            db.close()
        }

        return response
    }

    fun getOneSalidaCabecera(context: Context, num: Int): SalidaCabecera? {
        val db = SQLManager(context)
        val manager = db.readableDatabase
        val query = "SELECT * FROM Salidas_cab WHERE SalCabNum = $num"
        val cursor = manager.rawQuery(query, null)

        var salidaCabecera: SalidaCabecera? = null

        if (cursor.moveToFirst()) {
            salidaCabecera = SalidaCabecera(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getInt(2),
                cursor.getInt(3),
                cursor.getFloat(4),
                cursor.getString(5)
            )
        }

        cursor.close()

        return salidaCabecera
    }

    fun getSalidasCabeceras(context: Context): Array<SalidaCabecera> {
        var listaSalidasCabeceras: Array<SalidaCabecera> = emptyArray()
        val db = SQLManager(context)
        val manager = db.readableDatabase
        val query = "SELECT * FROM Salidas_cab"
        val cursor = manager.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val salidaCabecera = SalidaCabecera(
                    cursor.getInt(0), cursor.getInt(1), cursor.getInt(2),
                    cursor.getInt(3), cursor.getFloat(4), cursor.getString(5)
                )
                listaSalidasCabeceras = listaSalidasCabeceras.plus(salidaCabecera)
            } while (cursor.moveToNext())
        }

        if (cursor != null && !cursor.isClosed) {
            cursor.close()
        }

        return listaSalidasCabeceras
    }

}

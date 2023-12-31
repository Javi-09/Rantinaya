package com.example.myapplication.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.myapplication.database.Canton
import com.example.myapplication.database.Empresa
import com.example.myapplication.database.Producto
import java.io.ByteArrayOutputStream
import java.nio.charset.Charset

class DBHelperProducto(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        // Crear tablas
        db.execSQL(Canton.CREATE_TABLE)
        db.execSQL(Empresa.CREATE_TABLE)
        db.execSQL(Producto.CREATE_TABLE)

        // Insertar cantones iniciales
        insertInitialCantones(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Manejar actualizaciones de la base de datos si es necesario
    }

    // Funciones para Cantón
    // En el método insertCanton
    fun insertCanton(canton: Canton, db: SQLiteDatabase? = null): Long {
        val writableDb = db ?: this.writableDatabase
        val values = ContentValues()
        values.put(Canton.COLUMN_NOMBRE_CANTON, canton.nombreCanton)  // Cambiado de "nombre" a "nombre_canton"
        // Otros campos...
        return writableDb.insert(Canton.TABLE_NAME, null, values)
    }

    private fun insertInitialCantones(db: SQLiteDatabase) {
        val cantones = listOf("FRANCISCO DE ORELLANA", "SACHA", "LORETO", "AGUARICO")

        for (cantonNombre in cantones) {
            val canton = Canton(nombreCanton = cantonNombre)
            insertCanton(canton, db)
        }
    }

    fun getCantonByName(nombre: String): Canton? {
        val db = this.readableDatabase
        val cursor = db.query(
            Canton.TABLE_NAME,
            arrayOf(Canton.COLUMN_ID, Canton.COLUMN_NOMBRE_CANTON),
            "${Canton.COLUMN_NOMBRE_CANTON} = ?",
            arrayOf(nombre),
            null,
            null,
            null,
            null
        )
        return if (cursor.moveToFirst()) {
            val idIndex = cursor.getColumnIndex(Canton.COLUMN_ID)
            val nombreIndex = cursor.getColumnIndex(Canton.COLUMN_NOMBRE_CANTON)

            val id = cursor.getLong(idIndex)
            val cantonNombre = cursor.getString(nombreIndex)

            Canton(id, cantonNombre)
        } else {
            null
        }
    }


    fun getAllCantones(): List<Canton> {
        val cantones = mutableListOf<Canton>()
        val db = this.readableDatabase
        val cursor = db.query(
            Canton.TABLE_NAME,
            arrayOf(Canton.COLUMN_ID, Canton.COLUMN_NOMBRE_CANTON),
            null,
            null,
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {
            val idIndex = cursor.getColumnIndex(Canton.COLUMN_ID)
            val nombreIndex = cursor.getColumnIndex(Canton.COLUMN_NOMBRE_CANTON)

            val id = cursor.getLong(idIndex)
            val cantonNombre = cursor.getString(nombreIndex)

            val canton = Canton(id, cantonNombre)
            cantones.add(canton)
        }

        cursor.close()
        return cantones
    }

    // Funciones para Empresa
    fun insertEmpresa(empresa: Empresa): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(Empresa.COLUMN_NOMBRE, empresa.nombre)
        values.put(Empresa.COLUMN_SLOGAN, empresa.slogan)
        values.put(Empresa.COLUMN_NOMBRE_PROPIETARIO, empresa.nombrePropietario)
        values.put(Empresa.COLUMN_FACEBOOK, empresa.facebook)
        values.put(Empresa.COLUMN_INSTAGRAM, empresa.instagram)
        values.put(Empresa.COLUMN_WHATSAPP, empresa.whatsapp)
        values.put(Empresa.COLUMN_LONGITUD, empresa.longitud)
        values.put(Empresa.COLUMN_LATITUD, empresa.latitud)
        // Conversión de imagen de ByteArray a Blob
        values.put(Empresa.COLUMN_IMAGEN_EMPRESA, empresa.imagen_empresa)
        values.put(Empresa.COLUMN_IMAGEN_PROPIETARIO, empresa.imagen_propietario)
        values.put(Empresa.COLUMN_VIDEO_EMPRESA, empresa.video_empresa)
        values.put(Empresa.COLUMN_FK_EMPRESA_CANTON, empresa.fkEmpresaCanton)

        return db.insert(Empresa.TABLE_NAME, null, values)
    }


    //Obtener Empresa de Acuerdo a un Canton
    fun getEmpresasByCantonId(cantonId: Long): List<Empresa> {
        val empresas = mutableListOf<Empresa>()
        val db = this.readableDatabase
        val cursor = db.query(
            Empresa.TABLE_NAME,
            null,
            "${Empresa.COLUMN_FK_EMPRESA_CANTON} = ?",
            arrayOf(cantonId.toString()),
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {
            val idIndex = cursor.getColumnIndex(Empresa.COLUMN_ID)
            val nombreIndex = cursor.getColumnIndex(Empresa.COLUMN_NOMBRE)
            val sloganIndex = cursor.getColumnIndex(Empresa.COLUMN_SLOGAN)
            val nombrePropietarioIndex = cursor.getColumnIndex(Empresa.COLUMN_NOMBRE_PROPIETARIO)
            val facebookIndex = cursor.getColumnIndex(Empresa.COLUMN_FACEBOOK)
            val instagramIndex = cursor.getColumnIndex(Empresa.COLUMN_INSTAGRAM)
            val whatsappIndex = cursor.getColumnIndex(Empresa.COLUMN_WHATSAPP)
            val longitudIndex = cursor.getColumnIndex(Empresa.COLUMN_LONGITUD)
            val latitudIndex = cursor.getColumnIndex(Empresa.COLUMN_LATITUD)
            val imagenEmpresaIndex = cursor.getColumnIndex(Empresa.COLUMN_IMAGEN_EMPRESA)
            val imagenPropietarioIndex = cursor.getColumnIndex(Empresa.COLUMN_IMAGEN_PROPIETARIO)
            val videoEmpresaIndex = cursor.getColumnIndex(Empresa.COLUMN_VIDEO_EMPRESA)
            val fkEmpresaCantonIndex = cursor.getColumnIndex(Empresa.COLUMN_FK_EMPRESA_CANTON)

            val id = cursor.getLong(idIndex)
            val nombre = cursor.getString(nombreIndex)
            val slogan = cursor.getString(sloganIndex)
            val nombrePropietario = cursor.getString(nombrePropietarioIndex)
            val facebook = cursor.getString(facebookIndex)
            val instagram = cursor.getString(instagramIndex)
            val whatsapp = cursor.getString(whatsappIndex)
            val longitud = cursor.getDouble(longitudIndex)
            val latitud = cursor.getDouble(latitudIndex)
            val imagenEmpresa = cursor.getBlob(imagenEmpresaIndex)
            val imagenPropietario = cursor.getBlob(imagenPropietarioIndex)
            val videoEmpresa = cursor.getBlob(videoEmpresaIndex)
            val fkEmpresaCanton = cursor.getLong(fkEmpresaCantonIndex)

            val empresa = Empresa(
                id, nombre, slogan, nombrePropietario, facebook, instagram, whatsapp,
                longitud, latitud, imagenEmpresa, imagenPropietario, videoEmpresa, fkEmpresaCanton
            )

            empresas.add(empresa)
        }

        cursor.close()
        return empresas
    }




    // Funciones para Producto
    // (Similar a Cantón)

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "data_producto"
    }

    // Función auxiliar para convertir Bitmap a ByteArray
    private fun Bitmap.toByteArray(): ByteArray {
        val stream = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.PNG, 0, stream)
        return stream.toByteArray()
    }
}

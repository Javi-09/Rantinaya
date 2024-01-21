package com.example.myapplication.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelperAdministrador(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        // Crear tablas
        db.execSQL(Administrador.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Manejar actualizaciones de la base de datos si es necesario
    }

    fun insertAdministrador(administrador: Administrador): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(Administrador.COLUMN_NOMBRE, administrador.nombre)
        values.put(Administrador.COLUMN_APELLIDO, administrador.apellido)
        values.put(Administrador.COLUMN_CEDULA, administrador.cedula)
        values.put(Administrador.COLUMN_USUARIO, administrador.usuario)
        values.put(Administrador.COLUMN_CONTRASENA, administrador.contrasena)

        return db.insert(Administrador.TABLE_NAME, null, values)
    }

    fun getAdministradorById(adminId: Long): Administrador? {
        val db = this.readableDatabase
        val cursor = db.query(
            Administrador.TABLE_NAME,
            null,
            "${Administrador.COLUMN_ID} = ?",
            arrayOf(adminId.toString()),
            null,
            null,
            null
        )
        return if (cursor.moveToFirst()) {
            val idIndex = cursor.getColumnIndex(Administrador.COLUMN_ID)
            val nombreIndex = cursor.getColumnIndex(Administrador.COLUMN_NOMBRE)
            val apellidoIndex = cursor.getColumnIndex(Administrador.COLUMN_APELLIDO)
            val cedulaIndex = cursor.getColumnIndex(Administrador.COLUMN_CEDULA)
            val usuarioIndex = cursor.getColumnIndex(Administrador.COLUMN_USUARIO)
            val contrasenaIndex = cursor.getColumnIndex(Administrador.COLUMN_CONTRASENA)

            val id = cursor.getLong(idIndex)
            val nombre = cursor.getString(nombreIndex)
            val apellido = cursor.getString(apellidoIndex)
            val cedula = cursor.getString(cedulaIndex)
            val usuario = cursor.getString(usuarioIndex)
            val contrasena = cursor.getString(contrasenaIndex)

            Administrador(id, nombre, apellido, cedula, usuario, contrasena)
        } else {
            null
        }
    }

    // Otras funciones según tus necesidades...

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "data_administrador"
    }
}

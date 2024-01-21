package com.example.myapplication.database

data class Administrador(
    val id: Long = -1,
    val nombre: String,
    val apellido: String,
    val cedula: String,
    val usuario: String,
    val contrasena: String
) {
    companion object {
        const val TABLE_NAME = "administrador"
        const val COLUMN_ID = "id_admin"
        const val COLUMN_NOMBRE = "nombre"
        const val COLUMN_APELLIDO = "apellido"
        const val COLUMN_CEDULA = "cedula"
        const val COLUMN_USUARIO = "usuario"
        const val COLUMN_CONTRASENA = "contrasena"

        const val CREATE_TABLE = "CREATE TABLE $TABLE_NAME " +
                "($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NOMBRE TEXT, " +
                "$COLUMN_APELLIDO TEXT, " +
                "$COLUMN_CEDULA TEXT, " +
                "$COLUMN_USUARIO TEXT, " +
                "$COLUMN_CONTRASENA TEXT)"
    }
}

package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commerce.R
import com.example.myapplication.database.Administrador
import com.example.myapplication.database.DBHelperAdministrador

class IngresarAdministrador : AppCompatActivity() {

    private lateinit var editTextNombre: EditText
    private lateinit var editTextApellido: EditText
    private lateinit var editTextCedula: EditText
    private lateinit var editTextUsuario: EditText
    private lateinit var editTextContrasena: EditText
    private lateinit var btnGuardarAdmin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresar_administrador)

        // Vincular elementos de la interfaz de usuario
        editTextNombre = findViewById(R.id.editTextNombre)
        editTextApellido = findViewById(R.id.editTextApellido)
        editTextCedula = findViewById(R.id.editTextCedula)
        editTextUsuario = findViewById(R.id.editTextUsuario)
        editTextContrasena = findViewById(R.id.editTextContrasena)
        btnGuardarAdmin = findViewById(R.id.btnGuardarAdmin)

        // Configurar el listener del botón
        btnGuardarAdmin.setOnClickListener {
            guardarAdministrador()
        }
    }

    private fun guardarAdministrador() {
        // Obtener datos de los campos de texto
        val nombre = editTextNombre.text.toString()
        val apellido = editTextApellido.text.toString()
        val cedula = editTextCedula.text.toString()
        val usuario = editTextUsuario.text.toString()
        val contrasena = editTextContrasena.text.toString()

        // Validar que los campos no estén vacíos (puedes agregar más validaciones según tus necesidades)
        if (nombre.isNotEmpty() && apellido.isNotEmpty() && cedula.isNotEmpty() &&
            usuario.isNotEmpty() && contrasena.isNotEmpty()) {
            // Crear un objeto Administrador con los datos
            val administrador = Administrador(
                nombre = nombre,
                apellido = apellido,
                cedula = cedula,
                usuario = usuario,
                contrasena = contrasena
            )

            // Insertar el administrador en la base de datos
            val dbHelper = DBHelperAdministrador(this)
            val id = dbHelper.insertAdministrador(administrador)

            // Mostrar mensaje según el resultado de la inserción
            if (id != -1L) {
                mostrarMensaje("Administrador registrado con éxito")
            } else {
                mostrarMensaje("Error al registrar el administrador")
            }
        } else {
            // Mostrar mensaje de error si algún campo está vacío
            mostrarMensaje("Todos los campos son obligatorios")
        }
    }

    private fun mostrarMensaje(mensaje: String) {
        // Puedes personalizar la lógica para mostrar mensajes (Toast, Snackbar, etc.)
        // Aquí se utiliza un Toast para mostrar mensajes cortos
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}

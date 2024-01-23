package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import com.example.e_commerce.R
import com.example.myapplication.database.DBHelperServicio
import com.example.myapplication.database.Empresa

class EliminarServicio : AppCompatActivity() {

    private lateinit var dbHelper: DBHelperServicio
    private lateinit var spinnerCantones: Spinner
    private lateinit var spinnerEmpresas: Spinner
    private lateinit var btnEliminar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eliminar_servicio)

        dbHelper = DBHelperServicio(this)
        spinnerCantones = findViewById(R.id.spinnerCantones)
        spinnerEmpresas = findViewById(R.id.spinnerEmpresas)
        btnEliminar = findViewById(R.id.btnEliminar)

        // Obtener la lista de cantones desde la base de datos
        val cantonesFromDB = dbHelper.getAllCantones()
        val cantones = mutableListOf<String>()
        cantones.add("Seleccione el cantón")
        cantones.addAll(cantonesFromDB.map { it.nombreCanton })

        val EmpresaFromDB = dbHelper.getAllCantones()
        val empresa = mutableListOf<String>()
        empresa.add("Seleccione la empresa")
        empresa.addAll(cantonesFromDB.map { it.nombreCanton })

        // Crear un adaptador para el Spinner de Cantones
        val cantonesAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cantones)
        cantonesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCantones.adapter = cantonesAdapter

        // Crear un adaptador vacío para el Spinner de Empresas
        val empresasAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, mutableListOf<String>())
        empresasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerEmpresas.adapter = empresasAdapter

        // Mostrar mensaje antes de seleccionar el cantón
        Toast.makeText(this, "Seleccione un cantón", Toast.LENGTH_SHORT).show()

        // Manejar la selección del Spinner de Cantones
        spinnerCantones.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position != 0) {
                    val selectedCanton = cantonesFromDB[position - 1]
                    val empresasByCanton = dbHelper.getEmpresasByCantonId(selectedCanton.id)
                    val empresasNombres = empresasByCanton.map { it.nombre }

                    empresasAdapter.clear()
                    empresasAdapter.addAll(empresasNombres)
                } else {
                    empresasAdapter.clear()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Opcional: manejar la situación cuando no se selecciona nada
            }
        })

        // Manejar la selección del Spinner de Empresas
        spinnerEmpresas.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedEmpresa = empresasAdapter.getItem(position)

                // Mostrar mensaje antes de seleccionar la empresa
                if (selectedEmpresa == null || selectedEmpresa == "Seleccione la empresa") {
                    mostrarMensaje("Seleccione una empresa antes de eliminar")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Opcional: manejar la situación cuando no se selecciona nada
            }
        })

        // Configurar el listener del botón Eliminar
        btnEliminar.setOnClickListener {
            val cantonSeleccionado = spinnerCantones.selectedItem.toString()
            val empresaSeleccionada = spinnerEmpresas.selectedItem.toString()

            if (cantonSeleccionado == "Seleccione el cantón" || empresaSeleccionada == "Seleccione la empresa") {
                mostrarMensaje("Seleccione un cantón y una empresa antes de eliminar")
            } else {
                val exito = dbHelper.eliminarServicio(cantonSeleccionado, empresaSeleccionada)

                if (exito) {
                    mostrarMensaje("Servicio eliminado con éxito")
                } else {
                    mostrarMensaje("Error al eliminar el servicio")
                }
            }
        }
    }

    private fun mostrarMensaje(mensaje: String) {
        // Lógica para mostrar mensajes (Toast, Snackbar, etc.)
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}




package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commerce.R
import com.example.myapplication.database.DBHelperProducto
import com.example.myapplication.database.Canton
import com.example.myapplication.database.Empresa

class EliminarEmpresaProducto : AppCompatActivity() {

    private lateinit var dbHelper: DBHelperProducto
    private lateinit var spinnerCantones: Spinner
    private lateinit var spinnerEmpresas: Spinner
    private lateinit var btnEliminarEmpresa: Button
    private lateinit var empresasAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eliminar_empresa_producto)

        dbHelper = DBHelperProducto(this)
        spinnerCantones = findViewById(R.id.spinnerCantones)
        spinnerEmpresas = findViewById(R.id.spinnerEmpresas)
        btnEliminarEmpresa = findViewById(R.id.btnEliminarEmpresa)

        // Obtener la lista de cantones desde la base de datos
        val cantonesFromDB = dbHelper.getAllCantones()
        val cantones = mutableListOf<String>()

        // Agregar "Seleccione el cantón" al principio
        cantones.add("Seleccione el cantón")

        // Agregar los nombres de cantones a la lista
        cantones.addAll(cantonesFromDB.map { it.nombreCanton })

        // Crear un adaptador para el Spinner de Cantones
        val cantonesAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cantones)
        cantonesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCantones.adapter = cantonesAdapter

        // Crear un adaptador vacío para el Spinner de Empresas
        empresasAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, mutableListOf())
        empresasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerEmpresas.adapter = empresasAdapter

        // Manejar la selección del Spinner de Cantones
        spinnerCantones.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position != 0) {
                    val selectedCantonName = spinnerCantones.selectedItem.toString()
                    val selectedCanton = cantonesFromDB.find { it.nombreCanton == selectedCantonName }

                    // Verificar si se encontró el cantón seleccionado
                    if (selectedCanton != null) {
                        val empresasByCanton = dbHelper.getEmpresasByCantonId(selectedCanton.id)

                        // Obtener los nombres de empresas desde la lista de empresasByCanton
                        val empresasNombres = empresasByCanton.map { it.nombre }

                        // Limpiar y agregar las nuevas empresas al Spinner de Empresas
                        empresasAdapter.clear()
                        empresasAdapter.addAll(empresasNombres)
                    }
                } else {
                    // Si se selecciona "Seleccione el cantón", limpia la lista de empresas
                    empresasAdapter.clear()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Opcional: manejar la situación cuando no se selecciona nada
            }
        })

        // Configurar el clic del botón Eliminar Empresa
        btnEliminarEmpresa.setOnClickListener {
            val selectedEmpresaName = spinnerEmpresas.selectedItem.toString()

            // Obtener el ID del cantón seleccionado
            val selectedCantonName = spinnerCantones.selectedItem.toString()
            val selectedCanton = cantonesFromDB.find { it.nombreCanton == selectedCantonName }
            val idCanton = selectedCanton?.id ?: 0L

            // Verificar si se seleccionó una empresa válida
            if (selectedEmpresaName != "Seleccione la empresa" && idCanton != 0L) {
                // Llamar a la función para eliminar la empresa
                eliminarEmpresa(selectedEmpresaName, idCanton)
            } else {
                Toast.makeText(this@EliminarEmpresaProducto, "Seleccione una empresa válida", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Función para manejar la eliminación de la empresa
    private fun eliminarEmpresa(nombreEmpresa: String, idCanton: Long) {
        // Agrega aquí la lógica para eliminar la empresa de la base de datos
        // Puedes utilizar el DBHelperProducto y su función correspondiente
        // Por ejemplo:
        val empresaEliminada = dbHelper.eliminarEmpresaPorNombreYIdCanton(nombreEmpresa, idCanton)

        if (empresaEliminada) {
            Toast.makeText(this, "Empresa eliminada con éxito", Toast.LENGTH_SHORT).show()

            // Limpiar el Spinner de Empresas después de eliminar la empresa
            empresasAdapter.clear()
        } else {
            Toast.makeText(this, "Error al eliminar la empresa", Toast.LENGTH_SHORT).show()
        }
    }
}
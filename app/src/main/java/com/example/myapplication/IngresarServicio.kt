package com.example.myapplication

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commerce.R
import com.example.myapplication.database.Canton
import com.example.myapplication.database.DBHelperServicio
import com.example.myapplication.database.Empresa
import com.example.myapplication.database.Servicio

class IngresarServicio : AppCompatActivity() {

    private lateinit var dbHelper: DBHelperServicio
    private lateinit var spinnerCanton: Spinner
    private lateinit var spinnerEmpresa: Spinner
    private lateinit var editTextNombreServicio: EditText
    private lateinit var editTextPrecio: EditText
    private lateinit var editTextDescripcion: EditText
    private lateinit var btnGuardarServicio: Button
    private lateinit var btnSeleccionarImagenServicio: Button

    private lateinit var cantonesAdapter: ArrayAdapter<String>
    private lateinit var empresasAdapter: ArrayAdapter<String>
    private var imagenSeleccionadaServicio: ByteArray? = null

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                if (isImage(uri)) {
                    val inputStream = contentResolver.openInputStream(uri)
                    imagenSeleccionadaServicio = inputStream?.readBytes()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresar_servicio)

        dbHelper = DBHelperServicio(this)
        spinnerCanton = findViewById(R.id.spinnerCanton)
        spinnerEmpresa = findViewById(R.id.spinnerEmpresa)
        editTextNombreServicio = findViewById(R.id.editTextNombreServicio)
        editTextPrecio = findViewById(R.id.editTextPrecio)
        editTextDescripcion = findViewById(R.id.editTextDescripcion)
        btnGuardarServicio = findViewById(R.id.btnGuardarServicio)
        btnSeleccionarImagenServicio = findViewById(R.id.btnSeleccionarImagenServicio)

        // Obtener la lista de cantones y configurar el Spinner de Cantones
        val cantonesFromDB = dbHelper.getAllCantones()
        val cantones = mutableListOf<String>()
        cantones.add("Seleccione el cantón")
        cantones.addAll(cantonesFromDB.map { it.nombreCanton })
        cantonesAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cantones)
        cantonesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCanton.adapter = cantonesAdapter

        // Configurar el Spinner de Empresas con un adaptador vacío
        empresasAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, mutableListOf())
        empresasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerEmpresa.adapter = empresasAdapter

        // Manejar la selección de imágenes
        btnSeleccionarImagenServicio.setOnClickListener {
            getContent.launch("image/*")
        }

        // Configurar el clic del botón Guardar Servicio
        btnGuardarServicio.setOnClickListener {
            guardarServicio()
        }

        // Manejar la selección del Spinner de Cantones
        spinnerCanton.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position != 0) {
                    val selectedCantonName = spinnerCanton.selectedItem.toString()
                    val selectedCanton = cantonesFromDB.find { it.nombreCanton == selectedCantonName }

                    if (selectedCanton != null) {
                        val empresasByCanton = dbHelper.getEmpresasByCantonId(selectedCanton.id)
                        val empresasNombres = empresasByCanton.map { it.nombre }

                        // Limpiar y agregar las nuevas empresas al Spinner de Empresas
                        empresasAdapter.clear()
                        empresasAdapter.addAll(empresasNombres)
                        empresasAdapter.notifyDataSetChanged()
                    }
                } else {
                    // Si se selecciona "Seleccione el cantón", limpia la lista de empresas
                    empresasAdapter.clear()
                    empresasAdapter.notifyDataSetChanged()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Opcional: manejar la situación cuando no se selecciona nada
            }
        })
    }

    private fun guardarServicio() {
        val nombreServicio = editTextNombreServicio.text.toString()
        val precioServicio = editTextPrecio.text.toString().toDoubleOrNull()
        val descripcionServicio = editTextDescripcion.text.toString()

        if (nombreServicio.isNotEmpty() && precioServicio != null) {
            val selectedCantonName = spinnerCanton.selectedItem.toString()
            val selectedCanton = obtenerCantonPorNombre(selectedCantonName)
            val idCanton = selectedCanton?.id ?: 0L

            val selectedEmpresaName = spinnerEmpresa.selectedItem.toString()
            val selectedEmpresa = obtenerEmpresaPorNombre(selectedEmpresaName, idCanton)

            if (selectedEmpresa != null) {
                val nuevoServicio = Servicio(
                    nombre = nombreServicio,
                    imagen = imagenSeleccionadaServicio,
                    precio = precioServicio,
                    descripcion = descripcionServicio,
                    fkProductoEmpresa = selectedEmpresa.id
                )

                val servicioInsertado = dbHelper.insertServicio(nuevoServicio)

                if (servicioInsertado > 0) {
                    mostrarMensaje("Servicio guardado exitosamente")
                    limpiarCampos()
                } else {
                    mostrarMensaje("Error al guardar el servicio. Inténtalo de nuevo.")
                }
            }
        }
    }

    private fun obtenerCantonPorNombre(nombre: String): Canton? {
        return dbHelper.getCantonByName(nombre)
    }

    private fun obtenerEmpresaPorNombre(nombre: String, idCanton: Long): Empresa? {
        return dbHelper.getEmpresaByNombreYIdCanton(nombre, idCanton)
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun limpiarCampos() {
        editTextNombreServicio.text.clear()
        editTextPrecio.text.clear()
        editTextDescripcion.text.clear()
        imagenSeleccionadaServicio = null
    }

    private fun isImage(uri: Uri): Boolean {
        val type = contentResolver.getType(uri)
        return type?.startsWith("image/") == true
    }
}

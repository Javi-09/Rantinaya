package com.example.myapplication

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commerce.R
import com.example.myapplication.database.Canton
import com.example.myapplication.database.DBHelperProducto
import com.example.myapplication.database.Empresa
import com.example.myapplication.database.Producto

class IngresarProducto : AppCompatActivity() {

    private lateinit var dbHelper: DBHelperProducto
    private lateinit var spinnerCanton: Spinner
    private lateinit var spinnerEmpresa: Spinner
    private lateinit var editTextNombreProducto: EditText
    private lateinit var editTextPrecio: EditText
    private lateinit var editTextDescripcion: EditText
    private lateinit var btnGuardarProducto: Button
    private lateinit var btnSeleccionarImagenProducto: Button

    private lateinit var cantonesAdapter: ArrayAdapter<String>
    private lateinit var empresasAdapter: ArrayAdapter<String>
    private var imagenSeleccionadaProducto: ByteArray? = null

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                if (isImage(uri)) {
                    val inputStream = contentResolver.openInputStream(uri)
                    imagenSeleccionadaProducto = inputStream?.readBytes()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresar_producto)

        dbHelper = DBHelperProducto(this)
        spinnerCanton = findViewById(R.id.spinnerCanton)
        spinnerEmpresa = findViewById(R.id.spinnerEmpresa)
        editTextNombreProducto = findViewById(R.id.editTextNombreProducto)
        editTextPrecio = findViewById(R.id.editTextPrecio)
        editTextDescripcion = findViewById(R.id.editTextDescripcion)
        btnGuardarProducto = findViewById(R.id.btnGuardarProducto)
        btnSeleccionarImagenProducto = findViewById(R.id.btnSeleccionarImagenProducto)

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
        btnSeleccionarImagenProducto.setOnClickListener {
            getContent.launch("image/*")
        }

        // Configurar el clic del botón Guardar Producto
        btnGuardarProducto.setOnClickListener {
            guardarProducto()
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

    private fun guardarProducto() {
        val nombreProducto = editTextNombreProducto.text.toString()
        val precioProducto = editTextPrecio.text.toString().toDoubleOrNull()
        val descripcionProducto = editTextDescripcion.text.toString()

        if (nombreProducto.isNotEmpty() && precioProducto != null) {
            val selectedCantonName = spinnerCanton.selectedItem.toString()
            val selectedCanton = obtenerCantonPorNombre(selectedCantonName)
            val idCanton = selectedCanton?.id ?: 0L

            val selectedEmpresaName = spinnerEmpresa.selectedItem.toString()
            val selectedEmpresa = obtenerEmpresaPorNombre(selectedEmpresaName, idCanton)

            if (selectedEmpresa != null) {
                val nuevoProducto = Producto(
                    nombre = nombreProducto,
                    imagen = imagenSeleccionadaProducto,
                    precio = precioProducto,
                    descripcion = descripcionProducto,
                    fkProductoEmpresa = selectedEmpresa.id
                )

                val productoInsertado = dbHelper.insertProducto(nuevoProducto)

                if (productoInsertado > 0) {
                    mostrarMensaje("Producto guardado exitosamente")
                    limpiarCampos()
                } else {
                    mostrarMensaje("Error al guardar el producto. Inténtalo de nuevo.")
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
        editTextNombreProducto.text.clear()
        editTextPrecio.text.clear()
        editTextDescripcion.text.clear()
        imagenSeleccionadaProducto = null
    }

    private fun isImage(uri: Uri): Boolean {
        val type = contentResolver.getType(uri)
        return type?.startsWith("image/") == true
    }
}

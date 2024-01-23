package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commerce.R
import com.example.myapplication.database.DBHelperProducto
import com.example.myapplication.database.Canton
import com.example.myapplication.database.Empresa

class ActualizarEmpresasProducto : AppCompatActivity() {

    private lateinit var spinnerCanton: Spinner
    private lateinit var spinnerEmpresa: Spinner
    private lateinit var editTextNombreEmpresa: EditText
    private lateinit var editTextSlogan: EditText
    private lateinit var editTextNombrePropietario: EditText
    private lateinit var editVideoUrl: EditText
    private lateinit var editTextFacebook: EditText
    private lateinit var editTextInstagram: EditText
    private lateinit var editTextWhatsapp: EditText
    private lateinit var editDireccion: EditText
    private lateinit var btnSeleccionarImagenEmpresa: Button
    private lateinit var btnSeleccionarImagenPropietario: Button
    private lateinit var btnGuardarEmpresa: Button

    private lateinit var dbHelper: DBHelperProducto
    private lateinit var cantones: List<Canton>
    private lateinit var empresas: List<Empresa>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_empresas_producto)

        dbHelper = DBHelperProducto(this)

        // Inicializar vistas
        spinnerCanton = findViewById(R.id.spinnerCanton)
        spinnerEmpresa = findViewById(R.id.spinnerEmpresa)
        editTextNombreEmpresa = findViewById(R.id.editTextNombreEmpresa)
        editTextSlogan = findViewById(R.id.editTextSlogan)
        editTextNombrePropietario = findViewById(R.id.editTextNombrePropietario)
        editVideoUrl = findViewById(R.id.editVideoUrl)
        editTextFacebook = findViewById(R.id.editTextFacebook)
        editTextInstagram = findViewById(R.id.editTextInstagram)
        editTextWhatsapp = findViewById(R.id.editTextWhatsapp)
        editDireccion = findViewById(R.id.editDireccion)
        btnSeleccionarImagenEmpresa = findViewById(R.id.btnSeleccionarImagenEmpresa)
        btnSeleccionarImagenPropietario = findViewById(R.id.btnSeleccionarImagenPropietario)
        btnGuardarEmpresa = findViewById(R.id.btnGuardarEmpresa)

        // Llenar el spinner de cantones
        cantones = dbHelper.getAllCantones()
        val cantonAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cantones)
        cantonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCanton.adapter = cantonAdapter

        // Configurar el Listener para el spinner de cantones
        spinnerCanton.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                // Cuando se selecciona un cantón, cargar las empresas asociadas
                loadEmpresasForSelectedCanton(cantones[position].id)
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // No se necesita hacer nada aquí
            }
        }

        // Configurar el Listener para el botón de guardar empresa
        btnGuardarEmpresa.setOnClickListener {
            guardarEmpresa()
        }
    }

    private fun loadEmpresasForSelectedCanton(cantonId: Long) {
        // Llenar el spinner de empresas asociadas al cantón seleccionado
        empresas = dbHelper.getEmpresasByCantonId(cantonId)
        val empresaAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, empresas)
        empresaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerEmpresa.adapter = empresaAdapter
    }


    private fun guardarEmpresa() {
        // Validar campos obligatorios
        if (editTextNombreEmpresa.text.isNullOrBlank() || editTextSlogan.text.isNullOrBlank()) {
            Toast.makeText(this, "Nombre y Slogan son campos obligatorios", Toast.LENGTH_SHORT)
                .show()
            return
        }

        try {
            // Obtener datos de las vistas
            val selectedEmpresa = spinnerEmpresa.selectedItem as Empresa
            val nombreEmpresa = editTextNombreEmpresa.text.toString()
            val slogan = editTextSlogan.text.toString()
            val nombrePropietario = editTextNombrePropietario.text.toString()
            val videoUrl = editVideoUrl.text.toString()
            val facebook = editTextFacebook.text.toString()
            val instagram = editTextInstagram.text.toString()
            val whatsapp = editTextWhatsapp.text.toString()
            val direccion = editDireccion.text.toString()

            // Actualizar datos de la empresa en la base de datos
            val updatedEmpresa = Empresa(
                id = selectedEmpresa.id,
                nombre = nombreEmpresa,
                slogan = slogan,
                nombrePropietario = nombrePropietario,
                facebook = facebook,
                instagram = instagram,
                whatsapp = whatsapp,
                direccion = direccion,
                imagen_empresa = selectedEmpresa.imagen_empresa,
                imagen_propietario = selectedEmpresa.imagen_propietario,
                video_url = videoUrl,
                fkEmpresaCanton = selectedEmpresa.fkEmpresaCanton
            )

            dbHelper.updateEmpresa(updatedEmpresa)

            // Actualizar el spinnerEmpresa para reflejar los cambios
            loadEmpresasForSelectedCanton(spinnerCanton.selectedItemPosition.toLong())

            // Mostrar mensaje de éxito
            Toast.makeText(this, "Empresa actualizada correctamente", Toast.LENGTH_SHORT).show()

        } catch (e: Exception) {
            // Manejar cualquier error durante la actualización
            Toast.makeText(this, "Error al actualizar la empresa", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }
}


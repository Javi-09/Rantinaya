package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commerce.R
import com.example.myapplication.database.DBHelperProducto
import com.example.myapplication.database.Canton
import com.example.myapplication.database.Empresa

class ActualizarEmpresasProducto : AppCompatActivity() {

    private lateinit var dbHelper: DBHelperProducto
    private lateinit var spinnerCantones: Spinner
    private lateinit var spinnerEmpresas: Spinner
    private lateinit var editTextNombreEmpresa: EditText
    private lateinit var editTextSlogan: EditText
    private lateinit var editTextNombrePropietario: EditText
    private lateinit var editVideoUrl: EditText
    private lateinit var editTextFacebook: EditText
    private lateinit var editTextInstagram: EditText
    private lateinit var editTextWhatsapp: EditText
    private lateinit var editDireccion: EditText
    private lateinit var btnGuardarEmpresa: Button
    private lateinit var btnSeleccionarImagenEmpresa: Button
    private lateinit var btnSeleccionarImagenPropietario: Button

    private val PICK_IMAGE_REQUEST_EMPRESA = 1
    private val PICK_IMAGE_REQUEST_PROPIETARIO = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actualizar_empresas_producto)

        dbHelper = DBHelperProducto(this)
        spinnerCantones = findViewById(R.id.spinnerCanton)
        spinnerEmpresas = findViewById(R.id.spinnerEmpresa)
        editTextNombreEmpresa = findViewById(R.id.editTextNombreEmpresa)
        editTextSlogan = findViewById(R.id.editTextSlogan)
        editTextNombrePropietario = findViewById(R.id.editTextNombrePropietario)
        editVideoUrl = findViewById(R.id.editVideoUrl)
        editTextFacebook = findViewById(R.id.editTextFacebook)
        editTextInstagram = findViewById(R.id.editTextInstagram)
        editTextWhatsapp = findViewById(R.id.editTextWhatsapp)
        editDireccion = findViewById(R.id.editDireccion)
        btnGuardarEmpresa = findViewById(R.id.btnGuardarEmpresa)
        btnSeleccionarImagenEmpresa = findViewById(R.id.btnSeleccionarImagenEmpresa)
        btnSeleccionarImagenPropietario = findViewById(R.id.btnSeleccionarImagenPropietario)

        // Obtener la lista de cantones desde la base de datos
        val cantonesFromDB = dbHelper.getAllCantones()
        val cantones = mutableListOf<String>()
        cantones.add("Seleccione el cantón")
        cantones.addAll(cantonesFromDB.map { it.nombreCanton })

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
                    mostrarMensaje("Seleccione una empresa antes de actualizar")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Opcional: manejar la situación cuando no se selecciona nada
            }
        })

        btnSeleccionarImagenEmpresa.setOnClickListener {
            abrirGaleriaImagenes(PICK_IMAGE_REQUEST_EMPRESA)
        }

        btnSeleccionarImagenPropietario.setOnClickListener {
            abrirGaleriaImagenes(PICK_IMAGE_REQUEST_PROPIETARIO)
        }

        btnGuardarEmpresa.setOnClickListener {
            val cantonSeleccionado = spinnerCantones.selectedItem.toString()
            val empresaNombreSeleccionada = spinnerEmpresas.selectedItem.toString()

            if (cantonSeleccionado == "Seleccione el cantón" || empresaNombreSeleccionada == "Seleccione la empresa") {
                mostrarMensaje("Seleccione un cantón y una empresa antes de guardar")
            } else {
                // Obtener datos de la empresa seleccionada
                var empresa = dbHelper.getEmpresaByNombre(empresaNombreSeleccionada) // Cambiar a 'var'

                if (empresa != null) {
                    // Actualizar los datos de la empresa (adaptar según tus necesidades)
                    empresa.nombre = editTextNombreEmpresa.text.toString()
                    empresa.slogan = editTextSlogan.text.toString()
                    empresa.nombrePropietario = editTextNombrePropietario.text.toString()
                    empresa.video_url = editVideoUrl.text.toString()
                    empresa.facebook = editTextFacebook.text.toString()
                    empresa.instagram = editTextInstagram.text.toString()
                    empresa.whatsapp = editTextWhatsapp.text.toString()
                    empresa.direccion = editDireccion.text.toString()

                    // Lógica para actualizar la empresa en la base de datos
                    dbHelper.updateEmpresa(empresa)

                    mostrarMensaje("Empresa actualizada con éxito")
                } else {
                    mostrarMensaje("Error al obtener datos de la empresa")
                }
            }
        }
    }

    private fun abrirGaleriaImagenes(requestCode: Int) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null) {
            // Puedes realizar alguna lógica adicional aquí si es necesario
        }
    }

    private fun mostrarMensaje(mensaje: String) {
        // Lógica para mostrar mensajes (Toast, Snackbar, etc.)
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}

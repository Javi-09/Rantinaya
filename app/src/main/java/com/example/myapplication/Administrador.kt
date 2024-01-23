package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commerce.R

class Administrador : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_administrador)


        val btnServicio: Button = findViewById(R.id.btnServicio)
        val btnProducto: Button = findViewById(R.id.btnProducto)
        val btnAgregarAdmin: Button = findViewById(R.id.btnAgregarAdmin)
        val btnEliminarProducto:Button= findViewById(R.id.btnEliminarProducto)
        val btnEliminarServicio:Button= findViewById(R.id.btnEliminarServicio)
        val btnActualizarEmpresa: Button = findViewById(R.id.bntActualizarEmpresa) // Corrected ID
        val bntActualizarEmpresaServicio: Button= findViewById(R.id.bntActualizarEmpresaServicio)



        btnServicio.setOnClickListener {
            // Acción al presionar el botón de Servicio
            val intent = Intent(this, IngresarEmpresaServicio::class.java)
            startActivity(intent)
        }

        btnProducto.setOnClickListener {
            // Acción al presionar el botón de Producto
            val intent = Intent(this, IngresarEmpresaProducto::class.java)
            startActivity(intent)
        }

        btnAgregarAdmin.setOnClickListener {
            // Acción al presionar el botón de Producto
            val intent = Intent(this, IngresarAdministrador::class.java)
            startActivity(intent)
        }

        btnActualizarEmpresa.setOnClickListener {
            // Acción al presionar el botón de Producto
            val intent = Intent(this, ActualizarEmpresasProducto::class.java)
            startActivity(intent)
        }

        bntActualizarEmpresaServicio.setOnClickListener {
            // Acción al presionar el botón de Producto
            val intent = Intent(this, ActualizarEmpresasServicio::class.java)
            startActivity(intent)
        }

        btnEliminarProducto.setOnClickListener {
            // Acción al presionar el botón de Producto
            val intent = Intent(this, EliminarEmpresaProducto::class.java)
            startActivity(intent)
        }

        btnEliminarServicio.setOnClickListener {
            // Acción al presionar el botón de Producto
            val intent = Intent(this, EliminarServicio::class.java)
            startActivity(intent)
        }

    }
}

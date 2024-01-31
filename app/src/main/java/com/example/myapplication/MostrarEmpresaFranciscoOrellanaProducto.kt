package com.example.myapplication

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.R
import com.example.myapplication.database.Canton
import com.example.myapplication.database.DBHelperProducto
import com.example.myapplication.database.Empresa
import java.io.ByteArrayOutputStream

class MostrarEmpresaFranciscoOrellanaProducto : AppCompatActivity(), EmpresaAdapter.ClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EmpresaAdapter
    private lateinit var databaseHelper: DBHelperProducto
    private var imagenNuPropietario: ByteArray? = null
    private var imagenNuEmpresa: ByteArray? = null
    private var imagenNuPropietario2: ByteArray? = null
    private var imagenNuEmpresa2: ByteArray? = null
    private var imagenNuPropietario3: ByteArray? = null
    private var imagenNuEmpresa3: ByteArray? = null
    private var imagenNuPropietario4: ByteArray? = null
    private var imagenNuEmpresa4: ByteArray? = null
    private var imagenNuPropietario5: ByteArray? = null
    private var imagenNuEmpresa5: ByteArray? = null
    private var imagenNuPropietario6: ByteArray? = null
    private var imagenNuEmpresa6: ByteArray? = null
    private fun cargarImagenesDesdeDrawables() {
        // Cargar imagen del drawable para el propietario
        val drawablePropietario: Drawable? = ContextCompat.getDrawable(this, R.drawable.apaik)
        val bitmapPropietario: Bitmap = drawableToBitmap(drawablePropietario)
        imagenNuPropietario = bitmapPropietario.toByteArray()

        // Cargar imagen del drawable para la empresa
        val drawableEmpresa: Drawable? = ContextCompat.getDrawable(this, R.drawable.apaik)
        val bitmapEmpresa: Bitmap = drawableToBitmap(drawableEmpresa)
        imagenNuEmpresa = bitmapEmpresa.toByteArray()
    }
    private fun cargarImagenesDesdeDrawables2() {
        // Cargar imagen del drawable para el propietario
        val drawablePropietario: Drawable? = ContextCompat.getDrawable(this, R.drawable.asoprofa)
        val bitmapPropietario: Bitmap = drawableToBitmap(drawablePropietario)
        imagenNuPropietario2 = bitmapPropietario.toByteArray()

        // Cargar imagen del drawable para la empresa
        val drawableEmpresa: Drawable? = ContextCompat.getDrawable(this, R.drawable.asoprofa)
        val bitmapEmpresa: Bitmap = drawableToBitmap(drawableEmpresa)
        imagenNuEmpresa2 = bitmapEmpresa.toByteArray()
    }
    private fun cargarImagenesDesdeDrawables3() {
        // Cargar imagen del drawable para el propietario
        val drawablePropietario: Drawable? = ContextCompat.getDrawable(this, R.drawable.awak)
        val bitmapPropietario: Bitmap = drawableToBitmap(drawablePropietario)
        imagenNuPropietario3 = bitmapPropietario.toByteArray()

        // Cargar imagen del drawable para la empresa
        val drawableEmpresa: Drawable? = ContextCompat.getDrawable(this, R.drawable.awak)
        val bitmapEmpresa: Bitmap = drawableToBitmap(drawableEmpresa)
        imagenNuEmpresa3 = bitmapEmpresa.toByteArray()
    }
    private fun cargarImagenesDesdeDrawables4() {
        // Cargar imagen del drawable para el propietario
        val drawablePropietario: Drawable? = ContextCompat.getDrawable(this, R.drawable.escapa)
        val bitmapPropietario: Bitmap = drawableToBitmap(drawablePropietario)
        imagenNuPropietario4 = bitmapPropietario.toByteArray()

        // Cargar imagen del drawable para la empresa
        val drawableEmpresa: Drawable? = ContextCompat.getDrawable(this, R.drawable.escapa)
        val bitmapEmpresa: Bitmap = drawableToBitmap(drawableEmpresa)
        imagenNuEmpresa4 = bitmapEmpresa.toByteArray()
    }
    private fun cargarImagenesDesdeDrawables5() {
        // Cargar imagen del drawable para el propietario
        val drawablePropietario: Drawable? = ContextCompat.getDrawable(this, R.drawable.pai)
        val bitmapPropietario: Bitmap = drawableToBitmap(drawablePropietario)
        imagenNuPropietario5 = bitmapPropietario.toByteArray()

        // Cargar imagen del drawable para la empresa
        val drawableEmpresa: Drawable? = ContextCompat.getDrawable(this, R.drawable.pai)
        val bitmapEmpresa: Bitmap = drawableToBitmap(drawableEmpresa)
        imagenNuEmpresa5 = bitmapEmpresa.toByteArray()
    }
    private fun cargarImagenesDesdeDrawables6() {
        // Cargar imagen del drawable para el propietario
        val drawablePropietario: Drawable? = ContextCompat.getDrawable(this, R.drawable.aweid)
        val bitmapPropietario: Bitmap = drawableToBitmap(drawablePropietario)
        imagenNuPropietario6 = bitmapPropietario.toByteArray()

        // Cargar imagen del drawable para la empresa
        val drawableEmpresa: Drawable? = ContextCompat.getDrawable(this, R.drawable.aweid)
        val bitmapEmpresa: Bitmap = drawableToBitmap(drawableEmpresa)
        imagenNuEmpresa6 = bitmapEmpresa.toByteArray()
    }
    private fun drawableToBitmap(drawable: Drawable?): Bitmap {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        val bitmap: Bitmap = if (drawable!!.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        } else {
            Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        }

        val canvas = android.graphics.Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }

    private fun Bitmap.toByteArray(): ByteArray {
        val stream = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_empresa_francisco_orellana_producto)

        recyclerView = findViewById(R.id.recyclerViewEmpresas)
        databaseHelper = DBHelperProducto(this)

        // Obtener y mostrar las empresas de Francisco de Orellana
        val empresasFOP = databaseHelper.getEmpresasByCantonId(1)  // Cambia por el id_canton deseado

        // Configurar el RecyclerView
        adapter = EmpresaAdapter(empresasFOP, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

                // La empresa no existe, entonces la puedes insertar
        if (empresasFOP.isEmpty()) {
            insertarNuevaEmpresaP()
            insertarNuevaEmpresaP2()
            insertarNuevaEmpresaP3()
            insertarNuevaEmpresaP4()
            insertarNuevaEmpresaP5()
            insertarNuevaEmpresaP6()

        }

        }

    override fun onVerEmpresaClick(position: Int) {
        val empresaSeleccionada = adapter.empresas[position]

        // Crear un Intent para la nueva actividad
        val intent = Intent(this, DetalleEmpresaProducto::class.java)

        // Puedes pasar el ID de la empresa a la nueva actividad
        intent.putExtra("empresa_id", empresaSeleccionada.id)

        // Iniciar la nueva actividad
        startActivity(intent)
    }
    private fun insertarNuevaEmpresaP() {
        cargarImagenesDesdeDrawables()
        val nuevaEmpresaS = Empresa(
            nombre = "APAIKA HONGOS DE LA SELVA   ",
            slogan = "Disfruta de lo mejor  de  la  amazonia ",
            nombrePropietario = "Maria Perez ",
            facebook = "https://www.facebook.com/hongosapaika",
            instagram = "https://www.instagram.com/apaika.hongosdelaselva/",
            whatsapp = "593 98 330 8979",
            direccion = "https://maps.app.goo.gl/SJicJyY2Lcqn4m2Y7",
            imagen_empresa = imagenNuEmpresa,
            imagen_propietario = imagenNuPropietario,
            video_url = "https://drive.google.com/uc?export=download&id=1DsW7s2L16u2sy7puEv4nMzVkPLMrNY_R",
            fkEmpresaCanton = 1

        )
        val idNuevaEmpresa = databaseHelper.insertarEmpresaP(nuevaEmpresaS)
        if (idNuevaEmpresa != -1L) {
            // Se insertó correctamente, haz algo si es necesario
        } else {
            // Fallo al insertar, maneja el error
        }
    }
    private fun insertarNuevaEmpresaP2() {
        cargarImagenesDesdeDrawables2()
        val nuevaEmpresaS2 = Empresa(
            nombre = "ASOPROAFY WAEME  ",
            slogan = "Somos un emprendimiento dedicado a transmitir con nuestros productos bienestar, pureza y frescura cultivando y procesando nuestros propios productos.  ",
            nombrePropietario = "Martin Casanoba ",
            facebook = "https://www.instagram.com/asoproafy_waeme/",
            instagram = "",
            whatsapp = "098 330 8979",
            direccion = "google.maps.com",
            imagen_empresa = imagenNuEmpresa2,
            imagen_propietario = imagenNuPropietario2,
            video_url = "https://drive.google.com/uc?export=download&id=1DsW7s2L16u2sy7puEv4nMzVkPLMrNY_R",
            fkEmpresaCanton = 1

        )
        val idNuevaEmpresa = databaseHelper.insertarEmpresaP(nuevaEmpresaS2)
        if (idNuevaEmpresa != -1L) {
            // Se insertó correctamente, haz algo si es necesario
        } else {
            // Fallo al insertar, maneja el error
        }
    }
    private fun insertarNuevaEmpresaP3() {
        cargarImagenesDesdeDrawables3()
        val nuevaEmpresaS3 = Empresa(
            nombre = "AWAK MAKI ",
            slogan = "¡Manos sabias compartiendo Amazonía! ",
            nombrePropietario = "ASOCIACIÓN DE PRODUCCIÓN ARTESANAL \"AWAK MAKI\" MANOS TEJEDORAS\" ",
            facebook = "https://www.facebook.com/asoarte",
            instagram = "https://www.instagram.com/asoproawakmaki/",
            whatsapp = "09876543456",
            direccion = "google.maps.com",
            imagen_empresa = imagenNuEmpresa3,
            imagen_propietario = imagenNuPropietario3,
            video_url = "https://drive.google.com/uc?export=download&id=1DsW7s2L16u2sy7puEv4nMzVkPLMrNY_R",
            fkEmpresaCanton = 1

        )
        val idNuevaEmpresa = databaseHelper.insertarEmpresaP(nuevaEmpresaS3)
        if (idNuevaEmpresa != -1L) {
            // Se insertó correctamente, haz algo si es necesario
        } else {
            // Fallo al insertar, maneja el error
        }
    }
    private fun insertarNuevaEmpresaP4() {
        cargarImagenesDesdeDrawables4()
        val nuevaEmpresaS4 = Empresa(
            nombre = "AWEIDI DEL YASUNI  ",
            slogan = "“AWEIDI DEL YASUNI”  tiene como objetivo fabricar y comercializar.  ",
            nombrePropietario = "Asociación Jurídica",
            facebook = "https://www.facebook.com/aweididelyasuni",
            instagram = "https://www.instagram.com/explore/tags/aweididelyasuni/",
            whatsapp = "0998493579",
            direccion = "google.maps.com",
            imagen_empresa = imagenNuEmpresa4,
            imagen_propietario = imagenNuPropietario4,
            video_url = "https://drive.google.com/uc?export=download&id=1DsW7s2L16u2sy7puEv4nMzVkPLMrNY_R",
            fkEmpresaCanton = 1

        )
        val idNuevaEmpresa = databaseHelper.insertarEmpresaP(nuevaEmpresaS4)
        if (idNuevaEmpresa != -1L) {
            // Se insertó correctamente, haz algo si es necesario
        } else {
            // Fallo al insertar, maneja el error
        }
    }
    private fun insertarNuevaEmpresaP5() {
        cargarImagenesDesdeDrawables5()
        val nuevaEmpresaS5 = Empresa(
            nombre = "ESCAPARATE SANCHEZ  ",
            slogan = "Somo una tienda online que ofrece productos de especialidad en El Coca y envíos a   ",
            nombrePropietario = "Maru Sanchez ",
            facebook = "https://www.facebook.com/escaparate.sanchez",
            instagram = "",
            whatsapp = "099 046 5379",
            direccion = "google.maps.com",
            imagen_empresa = imagenNuEmpresa5,
            imagen_propietario = imagenNuPropietario5,
            video_url = "https://drive.google.com/uc?export=download&id=1DsW7s2L16u2sy7puEv4nMzVkPLMrNY_R",
            fkEmpresaCanton = 1

        )
        val idNuevaEmpresa = databaseHelper.insertarEmpresaP(nuevaEmpresaS5)
        if (idNuevaEmpresa != -1L) {
            // Se insertó correctamente, haz algo si es necesario
        } else {
            // Fallo al insertar, maneja el error
        }
    }
    private fun insertarNuevaEmpresaP6() {
        cargarImagenesDesdeDrawables6()
        val nuevaEmpresaS6 = Empresa(
            nombre = "ASOPROAFY WAEME  ",
            slogan = "Somos un emprendimiento dedicado a transmitir con nuestros productos bienestar, pureza y frescura cultivando y procesando nuestros propios productos.\n ",
            nombrePropietario = "Martin Casanoba ",
            facebook = "https://www.facebook.com/asoarte",
            instagram = "https://www.instagram.com/asoproafy_waeme/",
            whatsapp = "08796543247",
            direccion = "google.maps.com",
            imagen_empresa = imagenNuEmpresa6,
            imagen_propietario = imagenNuPropietario6,
            video_url = "https://drive.google.com/uc?export=download&id=1DsW7s2L16u2sy7puEv4nMzVkPLMrNY_R",
            fkEmpresaCanton = 1

        )
        val idNuevaEmpresa = databaseHelper.insertarEmpresaP(nuevaEmpresaS6)
        if (idNuevaEmpresa != -1L) {
            // Se insertó correctamente, haz algo si es necesario
        } else {
            // Fallo al insertar, maneja el error
        }
    }


}

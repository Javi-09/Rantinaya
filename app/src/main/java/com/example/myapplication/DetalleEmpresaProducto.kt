package com.example.myapplication

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerce.R
import com.example.myapplication.database.DBHelperProducto
import com.example.myapplication.database.Empresa
import com.example.myapplication.database.Producto


class DetalleEmpresaProducto : AppCompatActivity() {

    private lateinit var imageViewEmpresa: ImageView
    private lateinit var textViewNombre: TextView
    private lateinit var textViewPropietario: TextView
    private lateinit var btnVerMapa: Button
    private lateinit var imageViewFacebook: ImageView
    private lateinit var imageViewInstagram: ImageView
    private lateinit var imageViewWhatsapp: ImageView
    private lateinit var videoViewEmpresa: VideoView
    private lateinit var recyclerViewProductos: RecyclerView
    private lateinit var databaseHelper: DBHelperProducto
    private lateinit var adaptadorProductos: AdaptadorProductos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_empresa_producto)

        // Inicializar vistas
        imageViewEmpresa = findViewById(R.id.imageViewPropietario)
        textViewNombre = findViewById(R.id.textViewNombreEmpresa)
        textViewPropietario = findViewById(R.id.textViewNombrePropietario)
        btnVerMapa = findViewById(R.id.btnVerMapa)
        imageViewFacebook = findViewById(R.id.imageViewFacebook)
        imageViewInstagram = findViewById(R.id.imageViewInstagram)
        imageViewWhatsapp = findViewById(R.id.imageViewWhatsapp)
        videoViewEmpresa = findViewById(R.id.videoViewEmpresa)
        recyclerViewProductos = findViewById(R.id.recyclerViewProductos)

        // Inicializar DBHelper
        databaseHelper = DBHelperProducto(this)

        // Obtener ID de la empresa desde el Intent (ajusta según cómo pasas los datos)
        val empresaId = intent.getLongExtra("empresa_id", -1)

        // Obtener datos de la empresa desde la base de datos
        val empresa = databaseHelper.getEmpresaById(empresaId)

        // Configurar RecyclerView para productos
        adaptadorProductos = AdaptadorProductos()
        recyclerViewProductos.adapter = adaptadorProductos
        recyclerViewProductos.layoutManager = LinearLayoutManager(this)

        // Mostrar datos en las vistas
        mostrarDetallesEmpresa(empresa)
    }

    private fun mostrarDetallesEmpresa(empresa: Empresa?) {
        if (empresa != null) {
            // Mostrar algunos detalles básicos
            textViewNombre.text = empresa.nombre
            textViewPropietario.text = empresa.nombrePropietario

            // Cargar imagen en ImageView
            if (empresa.imagen_propietario != null && empresa.imagen_propietario.isNotEmpty()) {
                val bitmap = BitmapFactory.decodeByteArray(empresa.imagen_propietario, 0, empresa.imagen_propietario.size)
                imageViewEmpresa.setImageBitmap(bitmap)
            } else {
                imageViewEmpresa.setImageDrawable(null)
            }

            // Configurar VideoView -------------
            if (!empresa.video_url.isNullOrBlank()) {
                videoViewEmpresa.visibility = View.VISIBLE

                // Configurar MediaController para permitir el control del usuario
                val mediaController = MediaController(this)
                mediaController.setAnchorView(videoViewEmpresa)

                // Configurar la URL del video en el VideoView
                val videoUri = Uri.parse(empresa.video_url)

                // Configurar la fuente del video
                videoViewEmpresa.setVideoURI(videoUri)

                // Añadir estas líneas para configurar el MediaController y el VideoView
                mediaController.setMediaPlayer(videoViewEmpresa)
                videoViewEmpresa.setMediaController(mediaController)

                // Iniciar la reproducción del video
                videoViewEmpresa.start()

                // Añadir un listener para manejar la preparación del video
                videoViewEmpresa.setOnPreparedListener { mediaPlayer ->
                    // Configurar el loop para reproducción continua (opcional)
                    mediaPlayer.isLooping = true
                }

                // Añadir un listener para manejar errores durante la reproducción del video
                videoViewEmpresa.setOnErrorListener { mediaPlayer, what, extra ->
                    Log.e("DetalleEmpresaProducto", "Error durante la reproducción del video. Código: $what, Extra: $extra")
                    false
                }
            } else {
                // Si la URL del video está vacía o nula, ocultar el VideoView
                videoViewEmpresa.visibility = View.GONE
            }

            // Configurar clic del botón "Ver en Mapa"
            btnVerMapa.setOnClickListener {
                val urlDireccion = empresa.direccion
                redireccionarUrl(urlDireccion, "Google Maps")
            }

            // Redireccionamiento a Facebook
            imageViewFacebook.setOnClickListener {
                val urlFacebook = empresa?.facebook
                redireccionarUrl(urlFacebook, "Facebook")
            }

            // Redireccionamiento a Instagram
            imageViewInstagram.setOnClickListener {
                val urlInstagram = empresa?.instagram
                redireccionarUrl(urlInstagram, "Instagram")
            }

            // Redireccionamiento a WhatsApp
            imageViewWhatsapp.setOnClickListener {
                val phoneNumber = empresa?.whatsapp
                redireccionarWhatsapp(phoneNumber)
            }

            // Obtener y mostrar productos relacionados con la empresa
            val productos = databaseHelper.getProductosByEmpresaId(empresa.id)
            adaptadorProductos.actualizarProductos(productos)
        }
    }

    private fun redireccionarUrl(url: String?, appName: String) {
        if (!url.isNullOrBlank()) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Log.e("DetalleEmpresaProducto", "No se encontró una aplicación para manejar la URL de $appName.")
            }
        } else {
            Log.e("DetalleEmpresaProducto", "La URL de $appName está vacía o nula.")
        }
    }

    private fun redireccionarWhatsapp(phoneNumber: String?) {
        if (!phoneNumber.isNullOrBlank()) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=$phoneNumber"))
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Log.e("DetalleEmpresaProducto", "No se encontró una aplicación para manejar la URL de WhatsApp.")
            }
        } else {
            Log.e("DetalleEmpresaProducto", "El número de teléfono de WhatsApp está vacío o nulo.")
        }
    }

    override fun onPause() {
        super.onPause()
        videoViewEmpresa.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        videoViewEmpresa.stopPlayback()
    }
}

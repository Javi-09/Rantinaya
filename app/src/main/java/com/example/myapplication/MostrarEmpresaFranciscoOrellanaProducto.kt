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
import com.example.myapplication.database.Producto
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

            // Insertar productos solo si no existen
            if (!productosYaInsertados()) {
                //primera empresa
                insertarNuevoProducto("PRODUCTO 1", "Nuevos productos elaborados con colaboración de nuestros amigos fungicultores de La Casa del Hongo   a base de los deliciosos hongos orgánicos", 10.0, R.drawable.produ1, 1)
                insertarNuevoProducto("PRODUCTO 2", "Hongos ostra blancos y rosados ", 15.0, R.drawable.produ2, 1)
                insertarNuevoProducto("PRODUCTO 3", "Pleurotus djamor es un alimento saludable que contiene proteínas y proteínas bajas en grasa", 10.0, R.drawable.produ3, 1)
                insertarNuevoProducto("Producto 4", "Crecimiento y cosecha hongos ostra rosados (pleurotus djamor)", 15.0, R.drawable.produ4, 1)
                insertarNuevoProducto("Producto 5", "Nos alegramos de comunicarles que ya crecen en nuestro invernadero  hongos medicinales", 15.0, R.drawable.produ5, 1)
                //segunda empresa.
                insertarNuevoProducto("PRODUCTO 1", " En Waeme, transformamos lo ordinario en extraordinario, llevando la esencia de la jamaica a tu mesa con amor y dedicación. ", 15.0, R.drawable.awe, 2)
                insertarNuevoProducto("PRODUCTO 2", "¡Descubre el sabor único de la flor de Jamaica en cada sorbo! ", 15.0, R.drawable.awe1, 2)
                insertarNuevoProducto("PRODUCTO 3", " Sumérgete en el sabor vibrante de nuestra mermelada de jamaica, donde cada frasco está lleno de la dulzura única de la naturaleza. ", 15.0, R.drawable.awe2, 2)
                insertarNuevoProducto("PRODUCTO 4", " Flor deshidratada de jamaica  de 50g y de 10g ", 15.0, R.drawable.awe3, 2)
                insertarNuevoProducto("PRODUCTO 5", "Flor de jamica producida en Orellana Ines Arango", 15.0, R.drawable.awe4, 2)
                //TERCERA EMPRESA

                insertarNuevoProducto("PRODUCTO 1", " AWAK MAKI manos tejedoras ", 15.0, R.drawable.awr, 3)

                //cuarta empresa
                insertarNuevoProducto("PRODUCTO 1", "  Jabones de glicerina y extractos de plantas amazónicas  ", 15.0, R.drawable.yasu, 4)
                insertarNuevoProducto("PRODUCTO 2", "Relajacion y suavidad  de tu cuerpo jabones de glicerina  con productos naturales ", 15.0, R.drawable.yasu1, 4)
                insertarNuevoProducto("PRODUCTO 3", " Los mejores jabones  con aromas y esencias de la zona  ", 15.0, R.drawable.yasu2, 4)
                insertarNuevoProducto("PRODUCTO 4", "PROPIEDADES DEL JABÓN DE AVENA Y MIEL ", 15.0, R.drawable.yasu4, 4)
                insertarNuevoProducto("PRODUCTO 5", "JABONES NATURALES DE GLICERINA vs. JABONES INDUSTRIALES ", 15.0, R.drawable.yasu3, 4)

                ///quinta empresa
                insertarNuevoProducto("PRODUCTO 1", "  Delicioso Crepe  ", 15.0, R.drawable.pia, 5)
                insertarNuevoProducto("PRODUCTO 2", " Sánduche con pollo a la plancha. ", 15.0, R.drawable.pia1, 5)
                insertarNuevoProducto("PRODUCTO 3", " Sánduche con bebida ", 15.0, R.drawable.pia2, 5)
                insertarNuevoProducto("PRODUCTO 4", "Frappé de Capuccino", 15.0, R.drawable.pia3, 5)
                insertarNuevoProducto("PRODUCTO 5", "Prueba nuestro delicioso cheesecake aquí en cafetería     ", 15.0, R.drawable.pia5, 5)

                //sexta empresa
                insertarNuevoProducto("PRODUCTO 1", "    ", 15.0, R.drawable.imagen_producto1, 6)

                // ... Añadir más productos según sea necesario
            }


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
            whatsapp = "098 330 8979",
            direccion = "google.maps.com",
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


    //Funcion para insertar producto---
    private fun productosYaInsertados(): Boolean {
        // Puedes implementar lógica para verificar si los productos ya existen en la base de datos
        // Por ejemplo, contar el número de productos para una empresa específica y verificar si es mayor que cero
        val count = databaseHelper.contarProductosPorEmpresa(1)  // Reemplaza con el id de empresa correcto
        return count > 0
    }

    private fun insertarNuevoProducto(nombre: String, descripcion: String, precio: Double, imagenResId: Int, fkEmpresa: Long) {
        val imagenByteArray = cargarImagenDesdeDrawable(imagenResId)

        val nuevoProducto = Producto(
            nombre = nombre,
            imagen = imagenByteArray,
            precio = precio,
            descripcion = descripcion,
            fkProductoEmpresa = fkEmpresa
        )

        val idNuevoProducto = databaseHelper.insertarProducto(nuevoProducto)
        if (idNuevoProducto != -1L) {
            // Se insertó correctamente, haz algo si es necesario
        } else {
            // Fallo al insertar, maneja el error
        }
    }

    private fun cargarImagenDesdeDrawable(drawableResId: Int): ByteArray? {
        val drawable: Drawable? = ContextCompat.getDrawable(this, drawableResId)
        val bitmap: Bitmap = drawableToBitmap(drawable)
        return bitmap.toByteArray()
    }
    //--------------------------


}

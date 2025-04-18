package com.example.nixson.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.nixson.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.nixson.api.RetrofitClient
import com.example.nixson.modulos.Producto


class ProductoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_producto)
    }

    fun agregarProducto(v: View) {
        val nombre = findViewById<EditText>(R.id.nombre_producto)
        val cantidad = findViewById<EditText>(R.id.cantidad_producto)
        val fechaEntrada = findViewById<EditText>(R.id.fecha_entrada)
        val fechaVencimiento = findViewById<EditText>(R.id.fecha_vencimiento)
        val proveedor = findViewById<EditText>(R.id.proveedor_producto)
        val categoria = findViewById<EditText>(R.id.categoria_producto)
        val resultado = findViewById<TextView>(R.id.resultado_producto)

        if (!nombre.text.isNullOrEmpty() && !cantidad.text.isNullOrEmpty() && !proveedor.text.isNullOrEmpty()) {
            val nuevoProducto = Producto(
                id = 0,
                nombre = nombre.text.toString(),
                cantidad = cantidad.text.toString().toInt(),
                fechaEntrada = fechaEntrada.text.toString(),
                fechaVencimiento = fechaVencimiento.text.toString(),
                proveedor = proveedor.text.toString(),
                categoria = categoria.text.toString()
            )

            RetrofitClient.instance.agregarProducto(nuevoProducto).enqueue(object : Callback<Producto> {
                override fun onResponse(call: Call<Producto>, response: Response<Producto>) {
                    if (response.isSuccessful) {
                        val producto = response.body()
                        resultado.text = "Producto agregado: ${producto?.getNombre()}"
                        nombre.text.clear()
                        cantidad.text.clear()
                        fechaEntrada.text.clear()
                        fechaVencimiento.text.clear()
                        proveedor.text.clear()
                        categoria.text.clear()
                    } else {
                        resultado.text = "Error al agregar el producto."
                    }
                }

                override fun onFailure(call: Call<Producto>, t: Throwable) {
                    resultado.text = "Error: ${t.message}"
                }
            })
        } else {
            resultado.text = "Por favor, completa todos los campos requeridos."
        }
    }

    fun verProductos(v: View) {
        val listaProductosTextView = findViewById<TextView>(R.id.lista_productos)

        RetrofitClient.instance.getProductos().enqueue(object : Callback<List<Producto>> {
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                if (response.isSuccessful) {
                    val productos = response.body()
                    if (productos != null && productos.isNotEmpty()) {
                        val listaDetalles = productos.joinToString(separator = "\n") {
                            "ID: ${it.getId()}, Nombre: ${it.getNombre()}, Cantidad: ${it.getCantidad()}, " +
                                    "Fecha Entrada: ${it.getFechaEntrada()}, Fecha Vencimiento: ${it.getFechaVencimiento()}, " +
                                    "Proveedor: ${it.getProveedor()}, Categoría: ${it.getCategoria()}"
                        }
                        listaProductosTextView.text = "Productos:\n$listaDetalles"
                    } else {
                        listaProductosTextView.text = "No hay productos registrados."
                    }
                } else {
                    listaProductosTextView.text = "Error al cargar los productos."
                }
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                listaProductosTextView.text = "Error: ${t.message}"
            }
        })
    }

    fun eliminarProducto(v: View) {
        val idInput = findViewById<EditText>(R.id.id_producto_eliminar)
        val resultado = findViewById<TextView>(R.id.resultado_producto)

        if (!idInput.text.isNullOrEmpty()) {
            val id = idInput.text.toString().toInt()

            RetrofitClient.instance.eliminarProducto(id).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        resultado.text = "Producto con ID $id eliminado."
                    } else {
                        resultado.text = "Producto con ID $id no encontrado."
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    resultado.text = "Error: ${t.message}"
                }
            })

            idInput.text.clear()
        } else {
            resultado.text = "Por favor, ingresa un ID de producto."
        }
    }

    fun actualizarProducto(v: View) {
        val idInput = findViewById<EditText>(R.id.id_producto_actualizar)
        val nombre = findViewById<EditText>(R.id.nombre_producto)
        val cantidad = findViewById<EditText>(R.id.cantidad_producto)
        val fechaEntrada = findViewById<EditText>(R.id.fecha_entrada)
        val fechaVencimiento = findViewById<EditText>(R.id.fecha_vencimiento)
        val proveedor = findViewById<EditText>(R.id.proveedor_producto)
        val categoria = findViewById<EditText>(R.id.categoria_producto)
        val resultado = findViewById<TextView>(R.id.resultado_producto)

        if (!idInput.text.isNullOrEmpty() && !nombre.text.isNullOrEmpty() && !cantidad.text.isNullOrEmpty() && !proveedor.text.isNullOrEmpty()) {
            val id = idInput.text.toString().toInt()

            val productoActualizado = Producto(
                id = id,
                nombre = nombre.text.toString(),
                cantidad = cantidad.text.toString().toInt(),
                fechaEntrada = fechaEntrada.text.toString(),
                fechaVencimiento = fechaVencimiento.text.toString(),
                proveedor = proveedor.text.toString(),
                categoria = categoria.text.toString()
            )

            RetrofitClient.instance.actualizarProducto(id, productoActualizado).enqueue(object : Callback<Producto> {
                override fun onResponse(call: Call<Producto>, response: Response<Producto>) {
                    if (response.isSuccessful) {
                        resultado.text = "Producto con ID $id actualizado."
                    } else {
                        resultado.text = "Producto con ID $id no encontrado."
                    }
                }

                override fun onFailure(call: Call<Producto>, t: Throwable) {
                    resultado.text = "Error: ${t.message}"
                }
            })

            idInput.text.clear()
            nombre.text.clear()
            cantidad.text.clear()
            fechaEntrada.text.clear()
            fechaVencimiento.text.clear()
            proveedor.text.clear()
            categoria.text.clear()
        } else {
            resultado.text = "Por favor, completa todos los campos requeridos."
        }
    }

    fun irAMain(v: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
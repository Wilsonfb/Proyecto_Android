package com.example.nixson.ui
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.nixson.R
import com.example.nixson.modulos.Venta
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.nixson.api.RetrofitClient

class VentaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venta)
    }

    fun crearVenta(v: View) {
        val nombreProducto = findViewById<EditText>(R.id.nombreProducto).text.toString()
        val cantidadTexto = findViewById<EditText>(R.id.cantidad).text.toString()
        val resultado = findViewById<TextView>(R.id.resultado_venta)

        if (nombreProducto.isNotEmpty() && cantidadTexto.isNotEmpty()) {
            val cantidad = cantidadTexto.toInt()
            val nuevaVenta = Venta(id = 0, producto = nombreProducto, cantidad = cantidad)

            RetrofitClient.instance.agregarVenta(nuevaVenta)
                .enqueue(object : Callback<Venta> {
                    override fun onResponse(call: Call<Venta>, response: Response<Venta>) {
                        if (response.isSuccessful) {
                            val venta = response.body()
                            resultado.text = "Venta agregada: ID ${venta?.getId()}, Producto: ${venta?.getProducto()}, Cantidad: ${venta?.getCantidad()}"
                        } else {
                            resultado.text = "Error al agregar venta"
                        }
                    }

                    override fun onFailure(call: Call<Venta>, t: Throwable) {
                        resultado.text = "Error: ${t.message}"
                    }
                })
        } else {
            resultado.text = "Por favor, completa todos los campos"
        }
    }

    fun verVentas(v: View) {
        val listaTextView = findViewById<TextView>(R.id.lista_ventas)

        RetrofitClient.instance.getVentas()
            .enqueue(object : Callback<List<Venta>> {
                override fun onResponse(call: Call<List<Venta>>, response: Response<List<Venta>>) {
                    if (response.isSuccessful) {
                        val ventas = response.body()
                        if (!ventas.isNullOrEmpty()) {
                            val texto = ventas.joinToString("\n") {
                                "ID: ${it.getId()}, Producto: ${it.getProducto()}, Cantidad: ${it.getCantidad()}"
                            }
                            listaTextView.text = texto
                        } else {
                            listaTextView.text = "No hay ventas registradas."
                        }
                    } else {
                        listaTextView.text = "Error al cargar ventas"
                    }
                }

                override fun onFailure(call: Call<List<Venta>>, t: Throwable) {
                    listaTextView.text = "Error: ${t.message}"
                }
            })
    }

    fun eliminarVenta(v: View) {
        val idInput = findViewById<EditText>(R.id.id_venta_eliminar)
        val idTexto = idInput.text.toString()
        val resultado = findViewById<TextView>(R.id.resultado_venta)

        if (idTexto.isNotEmpty()) {
            val id = idTexto.toInt()

            RetrofitClient.instance.eliminarVenta(id)
                .enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            resultado.text = "Venta con ID $id eliminada."
                        } else {
                            resultado.text = "No se encontró la venta con ID $id."
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        resultado.text = "Error: ${t.message}"
                    }
                })

            idInput.text.clear()
        } else {
            resultado.text = "Por favor, ingresa un ID de venta."
        }
    }

    fun irAMain(v: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
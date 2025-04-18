package com.example.nixson.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.nixson.R
import com.example.nixson.modulos.Categoria
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.nixson.api.RetrofitClient

class CategoriaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categoria)
    }

    fun agregarCategoria(v: View) {
        val nombre = findViewById<EditText>(R.id.nombre_categoria).text.toString()
        val resultado = findViewById<TextView>(R.id.resultado_categoria)

        if (nombre.isNotEmpty()) {
            val nuevaCategoria = Categoria(nombre = nombre)

            RetrofitClient.instance.agregarCategoria(nuevaCategoria)
                .enqueue(object : Callback<Categoria> {
                    override fun onResponse(call: Call<Categoria>, response: Response<Categoria>) {
                        if (response.isSuccessful) {
                            val categoria = response.body()
                            resultado.text = "Categoría agregada: ID ${categoria?.getId()}, Nombre: ${categoria?.getNombre()}"
                        } else {
                            resultado.text = "Error al agregar categoría"
                        }
                    }

                    override fun onFailure(call: Call<Categoria>, t: Throwable) {
                        resultado.text = "Error: ${t.message}"
                    }
                })
        } else {
            resultado.text = "Por favor, ingresa un nombre para la categoría."
        }
    }

    fun verCategorias(v: View) {
        val listaTextView = findViewById<TextView>(R.id.lista_categorias)

        RetrofitClient.instance.getCategorias()
            .enqueue(object : Callback<List<Categoria>> {
                override fun onResponse(call: Call<List<Categoria>>, response: Response<List<Categoria>>) {
                    if (response.isSuccessful) {
                        val lista = response.body()
                        if (!lista.isNullOrEmpty()) {
                            val texto = lista.joinToString("\n") { "ID: ${it.getId()}, Nombre: ${it.getNombre()}" }
                            listaTextView.text = texto
                        } else {
                            listaTextView.text = "No hay categorías registradas."
                        }
                    } else {
                        listaTextView.text = "Error al cargar categorías"
                    }
                }

                override fun onFailure(call: Call<List<Categoria>>, t: Throwable) {
                    listaTextView.text = "Error: ${t.message}"
                }
            })
    }

    fun eliminarCategoria(v: View) {
        val idInput = findViewById<EditText>(R.id.id_categoria_eliminar)
        val idTexto = idInput.text.toString()
        val resultado = findViewById<TextView>(R.id.resultado_categoria)

        if (idTexto.isNotEmpty()) {
            val id = idTexto.toInt()

            RetrofitClient.instance.eliminarCategoria(id)
                .enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            resultado.text = "Categoría con ID $id eliminada."
                        } else {
                            resultado.text = "No se encontró la categoría con ID $id."
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        resultado.text = "Error: ${t.message}"
                    }
                })

            idInput.text.clear()
        } else {
            resultado.text = "Por favor, ingresa un ID de categoría."
        }
    }
    fun irAMain(v: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
package com.example.nixson.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.nixson.modulos.Usuario
import com.example.nixson.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.nixson.api.RetrofitClient

class UsuarioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario)
    }

    fun agregarUsuario(v: View) {
        val nombres = findViewById<EditText>(R.id.editTextNombres)
        val apellidos = findViewById<EditText>(R.id.editTextApellidos)
        val email = findViewById<EditText>(R.id.editTextEmail)
        val telefono = findViewById<EditText>(R.id.editTextTelefono)
        val direccion = findViewById<EditText>(R.id.editTextDireccion)
        val rolEditText = findViewById<EditText>(R.id.editTextRol)
        val resultado = findViewById<TextView>(R.id.textViewUsuarios)

        val rolTexto = rolEditText.text.toString().trim()

        if (!nombres.text.isNullOrEmpty() && !apellidos.text.isNullOrEmpty() && !email.text.isNullOrEmpty() &&
            !telefono.text.isNullOrEmpty() && !direccion.text.isNullOrEmpty() && rolTexto.isNotEmpty()) {

            val rolId = try {
                rolTexto.toInt()
            } catch (e: NumberFormatException) {
                resultado.text = "El rol debe ser un número (1 para ADMIN, 2 para EMPLEADO)."
                return
            }

            val nuevoUsuario = Usuario(
                id = 0,
                nombres = nombres.text.toString(),
                apellidos = apellidos.text.toString(),
                email = email.text.toString(),
                telefono = telefono.text.toString().toInt(),
                direccion = direccion.text.toString(),
                rolId = rolId
            )

            RetrofitClient.instance.agregarUsuario(nuevoUsuario).enqueue(object : Callback<Usuario> {
                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                    if (response.isSuccessful) {
                        val usuario = response.body()
                        resultado.text = "Usuario agregado: ${usuario?.getNombres()} ${usuario?.getApellidos()}"
                        nombres.text.clear()
                        apellidos.text.clear()
                        email.text.clear()
                        telefono.text.clear()
                        direccion.text.clear()
                        rolEditText.text.clear()
                    } else {
                        resultado.text = "Error al agregar el usuario. Verifica los datos."
                    }
                }

                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                    resultado.text = "Error: ${t.message}"
                }
            })
        } else {
            resultado.text = "Por favor, completa todos los campos requeridos."
        }
    }

    fun verUsuarios(v: View) {
        val listaUsuariosTextView = findViewById<TextView>(R.id.textViewUsuarios)

        RetrofitClient.instance.getUsuarios().enqueue(object : Callback<List<Usuario>> {
            override fun onResponse(call: Call<List<Usuario>>, response: Response<List<Usuario>>) {
                if (response.isSuccessful) {
                    val usuarios = response.body()
                    if (usuarios != null && usuarios.isNotEmpty()) {
                        val listaDetalles = usuarios.joinToString(separator = "\n") {
                            "ID: ${it.getId()}, Nombres: ${it.getNombres()} ${it.getApellidos()}, Email: ${it.getEmail()}, " +
                                    "Teléfono: ${it.getTelefono()}, Dirección: ${it.getDireccion()}, Rol ID: ${it.rolId}"
                        }
                        listaUsuariosTextView.text = "Usuarios:\n$listaDetalles"
                    } else {
                        listaUsuariosTextView.text = "No hay usuarios registrados."
                    }
                } else {
                    listaUsuariosTextView.text = "Error al cargar los usuarios."
                }
            }

            override fun onFailure(call: Call<List<Usuario>>, t: Throwable) {
                listaUsuariosTextView.text = "Error: ${t.message}"
            }
        })
    }

    fun eliminarUsuario(v: View) {
        val idInput = findViewById<EditText>(R.id.editTextIdEliminar)
        val resultado = findViewById<TextView>(R.id.textViewUsuarios)

        if (!idInput.text.isNullOrEmpty()) {
            val id = idInput.text.toString().toInt()

            RetrofitClient.instance.eliminarUsuario(id).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        resultado.text = "Usuario con ID $id eliminado."
                    } else {
                        resultado.text = "Usuario con ID $id no encontrado."
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    resultado.text = "Error: ${t.message}"
                }
            })

            idInput.text.clear()
        } else {
            resultado.text = "Por favor, ingresa un ID de usuario."
        }
    }

    fun actualizarUsuario(v: View) {
        val idInput = findViewById<EditText>(R.id.editTextIdActualizar)
        val nombres = findViewById<EditText>(R.id.editTextNombres)
        val apellidos = findViewById<EditText>(R.id.editTextApellidos)
        val email = findViewById<EditText>(R.id.editTextEmail)
        val telefono = findViewById<EditText>(R.id.editTextTelefono)
        val direccion = findViewById<EditText>(R.id.editTextDireccion)
        val rolEditText = findViewById<EditText>(R.id.editTextRol)
        val resultado = findViewById<TextView>(R.id.textViewUsuarios)

        val rolTexto = rolEditText.text.toString().trim()

        if (!idInput.text.isNullOrEmpty() && !nombres.text.isNullOrEmpty() && !apellidos.text.isNullOrEmpty() &&
            !email.text.isNullOrEmpty() && !telefono.text.isNullOrEmpty() && !direccion.text.isNullOrEmpty()) {

            val id = idInput.text.toString().toInt()

            val rolId = try {
                rolTexto.toInt()
            } catch (e: NumberFormatException) {
                resultado.text = "El rol debe ser un número (1 para ADMIN, 2 para EMPLEADO)."
                return
            }

            val usuarioActualizado = Usuario(
                id = id,
                nombres = nombres.text.toString(),
                apellidos = apellidos.text.toString(),
                email = email.text.toString(),
                telefono = telefono.text.toString().toInt(),
                direccion = direccion.text.toString(),
                rolId = rolId
            )

            RetrofitClient.instance.actualizarUsuario(id, usuarioActualizado).enqueue(object : Callback<Usuario> {
                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                    if (response.isSuccessful) {
                        resultado.text = "Usuario con ID $id actualizado."
                    } else {
                        resultado.text = "Usuario con ID $id no encontrado."
                    }
                }

                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                    resultado.text = "Error: ${t.message}"
                }
            })

            idInput.text.clear()
            nombres.text.clear()
            apellidos.text.clear()
            email.text.clear()
            telefono.text.clear()
            direccion.text.clear()
        } else {
            resultado.text = "Por favor, completa todos los campos requeridos."
        }
    }

    fun irAMain(v: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
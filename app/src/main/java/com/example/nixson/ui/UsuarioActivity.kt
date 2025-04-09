package com.example.nixson.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nixson.controller.UsuarioController
import com.example.nixson.modulos.Usuario
import com.example.nixson.modulos.Rol
import com.example.nixson.R
import android.widget.Spinner
import android.widget.ArrayAdapter

class UsuarioActivity : AppCompatActivity() {
    private val usuarioController = UsuarioController()
    private lateinit var editTextNombres: EditText
    private lateinit var editTextApellidos: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextTelefono: EditText
    private lateinit var editTextDireccion: EditText
    private lateinit var editTextIdEliminar: EditText
    private lateinit var buttonAgregar: Button
    private lateinit var buttonEliminar: Button
    private lateinit var textViewUsuarios: TextView
    private lateinit var spinnerRol: Spinner

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario)

        editTextNombres = findViewById(R.id.editTextNombres)
        editTextApellidos = findViewById(R.id.editTextApellidos)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextTelefono = findViewById(R.id.editTextTelefono)
        editTextDireccion = findViewById(R.id.editTextDireccion)
        editTextIdEliminar = findViewById(R.id.editTextIdEliminar)
        buttonAgregar = findViewById(R.id.buttonAgregar)
        buttonEliminar = findViewById(R.id.buttonEliminar)
        textViewUsuarios = findViewById(R.id.textViewUsuarios)
        spinnerRol = findViewById(R.id.spinnerRol)

        val roles = Rol.values().map { it.name }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, roles)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerRol.adapter = adapter

        buttonAgregar.setOnClickListener { agregarUsuario() }
        buttonEliminar.setOnClickListener { eliminarUsuario() }

        agregarUsuarioAdmin()

        verUsuarios()
    }

    private fun agregarUsuarioAdmin() {
        val adminUsuario = Usuario(
            id = 1,
            nombres = "Nixson",
            apellidos = "Admin",
            email = "nixson.admin@example.com",
            telefono = 1234567890,
            direccion = "Admin Street 123",
            rol = Rol.ADMIN
        )

        usuarioController.agregarUsuario(adminUsuario)
    }

    private fun agregarUsuario() {
        val nombres = editTextNombres.text.toString()
        val apellidos = editTextApellidos.text.toString()
        val email = editTextEmail.text.toString()
        val telefono = editTextTelefono.text.toString().toIntOrNull() ?: 0
        val direccion = editTextDireccion.text.toString()
        val rolSeleccionado = spinnerRol.selectedItem.toString()

        if (nombres.isEmpty() || apellidos.isEmpty() || email.isEmpty() || telefono == 0 || direccion.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
            return
        }

        val nuevoUsuario = Usuario(
            id = usuarioController.obtenerTodosLosUsuarios().size + 1,
            nombres = nombres,
            apellidos = apellidos,
            email = email,
            telefono = telefono,
            direccion = direccion,
            rol = Rol.valueOf(rolSeleccionado)
        )

        usuarioController.agregarUsuario(nuevoUsuario)

        editTextNombres.text.clear()
        editTextApellidos.text.clear()
        editTextEmail.text.clear()
        editTextTelefono.text.clear()
        editTextDireccion.text.clear()

        verUsuarios()
    }

    private fun eliminarUsuario() {
        val id = editTextIdEliminar.text.toString().toIntOrNull()
        if (id == null) {
            Toast.makeText(this, "Por favor, ingresa un ID válido.", Toast.LENGTH_SHORT).show()
            return
        }

        val eliminado = usuarioController.eliminarUsuario(id)
        if (eliminado) {
            Toast.makeText(this, "Usuario eliminado con éxito.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "No se encontró un usuario con ese ID.", Toast.LENGTH_SHORT).show()
        }

        editTextIdEliminar.text.clear()
        verUsuarios()
    }

    fun verUsuarios() {
        val usuarios = usuarioController.obtenerTodosLosUsuarios()
        if (usuarios.isNotEmpty()) {
            val listaDetalles = usuarios.joinToString(separator = "\n") {
                "ID: ${it.id}, Nombres: ${it.nombres} ${it.apellidos}, Email: ${it.email}, Teléfono: ${it.telefono}, Dirección: ${it.direccion}, Rol: ${it.rol}"
            }
            textViewUsuarios.text = "Usuarios Registrados:\n$listaDetalles"
        } else {
            textViewUsuarios.text = "No hay usuarios registrados."
        }
    }
}
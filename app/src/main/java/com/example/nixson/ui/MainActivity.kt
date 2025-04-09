package com.example.nixson.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.nixson.R
import com.example.nixson.modulos.Venta
import com.example.nixson.modulos.Producto
import com.example.nixson.modulos.Categoria
import com.example.nixson.modulos.Usuario
import java.sql.Connection
import com.example.nixson.db.DatabaseConnection
import com.example.nixson.modulos.Rol

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun irAVentas(v: View) {
        val intent = Intent(this, VentaActivity::class.java)
        startActivity(intent)
    }

    fun irAHistorial(v: View) {
        val intent = Intent(this, HistorialVentasActivity::class.java)
        startActivity(intent)
    }

    fun irACategorias(v: View) {
        val intent = Intent(this, CategoriaActivity::class.java)
        startActivity(intent)
    }

    fun irAAdministrarProductos(v: View) {
        val intent = Intent(this, ProductoActivity::class.java)
        startActivity(intent)
    }

    fun irAAdministrarUsuarios(v: View) {
        val intent = Intent(this, UsuarioActivity::class.java)
        startActivity(intent)
    }
}
fun main() {
    val connection: Connection? = DatabaseConnection.getConnection()
    
    if (connection != null) {
        try {
            val usuarios = obtenerUsuarios(connection)
            val categorias = obtenerCategorias(connection)
            val productos = obtenerProductos(connection)
            val ventas = obtenerVentas(connection)

            println("Usuarios:")
            usuarios.forEach { println(it) }

            println("\nCategorías:")
            categorias.forEach { println(it) }

            println("\nProductos:")
            productos.forEach { println(it) }

            println("\nVentas:")
            ventas.forEach { println(it) }

            connection.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    } else {
        println("Error: No se pudo establecer la conexión a la base de datos.")
    }
}

fun obtenerUsuarios(connection: Connection): List<Usuario> {
    val sql = "SELECT * FROM Usuario"
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery(sql)
    val usuarios = mutableListOf<Usuario>()

    while (resultSet.next()) {
        val rolString = resultSet.getString("rol")
        val rol = try {
            Rol.valueOf(rolString)
        } catch (e: IllegalArgumentException) {

            Rol.ADMIN
        }

        val usuario = Usuario(
            id = resultSet.getInt("id"),
            nombres = resultSet.getString("nombres"),
            apellidos = resultSet.getString("apellidos"),
            email = resultSet.getString("email"),
            telefono = resultSet.getInt("telefono"),
            direccion = resultSet.getString("direccion"),
            rol = rol
        )

        usuarios.add(usuario)
    }

    resultSet.close()
    statement.close()
    return usuarios
}

fun obtenerCategorias(connection: Connection): List<Categoria> {
    val sql = "SELECT * FROM Categoria"
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery(sql)
    val categorias = mutableListOf<Categoria>()

    while (resultSet.next()) {
        val categoria = Categoria(
            id = resultSet.getInt("id"),
            nombre = resultSet.getString("nombre")
        )
        categorias.add(categoria)
    }

    resultSet.close()
    statement.close()
    return categorias
}

fun obtenerProductos(connection: Connection): List<Producto> {
    val sql = "SELECT * FROM Producto"
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery(sql)
    val productos = mutableListOf<Producto>()

    while (resultSet.next()) {
        val producto = Producto(
            id = resultSet.getInt("id"),
            nombre = resultSet.getString("nombre"),
            cantidad = resultSet.getInt("cantidad"),
            fechaEntrada = resultSet.getString("fechaEntrada"),
            fechaVencimiento = resultSet.getString("fechaVencimiento"),
            proveedor = resultSet.getString("proveedor"),
            categoria = resultSet.getString("categoria")
        )
        productos.add(producto)
    }

    resultSet.close()
    statement.close()
    return productos
}

fun obtenerVentas(connection: Connection): List<Venta> {
    val sql = "SELECT * FROM Venta"
    val statement = connection.createStatement()
    val resultSet = statement.executeQuery(sql)
    val ventas = mutableListOf<Venta>()

    while (resultSet.next()) {
        val venta = Venta(
            id = resultSet.getInt("id"),
            producto = resultSet.getString("producto"),
            cantidad = resultSet.getInt("cantidad")
        )
        ventas.add(venta)
    }

    resultSet.close()
    statement.close()
    return ventas
}
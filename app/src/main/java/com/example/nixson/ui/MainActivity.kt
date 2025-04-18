package com.example.nixson.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.nixson.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun irAVentas(v: View) {
        val intent = Intent(this, VentaActivity::class.java)
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
package com.example.nixson.modulos

import com.google.gson.annotations.SerializedName

data class Usuario(
    private var id: Int = 0,
    private var nombres: String,
    private var apellidos: String,
    private var email: String,
    private var telefono: Int,
    private var direccion: String,
    @SerializedName("rol_id")
    val rolId: Int
) {
    fun getId(): Int {
        return id
    }

    fun getNombres(): String {
        return nombres
    }

    fun getApellidos(): String {
        return apellidos
    }

    fun getEmail(): String {
        return email
    }

    fun getTelefono(): Int {
        return telefono
    }

    fun getDireccion(): String {
        return direccion
    }

}
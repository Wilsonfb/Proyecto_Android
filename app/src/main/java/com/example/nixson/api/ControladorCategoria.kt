package com.example.nixson.api

import com.example.nixson.modulos.Categoria
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Body

interface ControladorCategoria {
    @GET("categoria")
    fun obtenerCategorias(): Call<List<Categoria>>

    @POST("categoria")
    fun agregarCategoria(@Body categoria: Categoria): Call<Categoria>
}
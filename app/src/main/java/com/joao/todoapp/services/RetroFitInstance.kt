package com.joao.todoapp.services

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroFitInstance {
    val baseUrl = "https://simples-todo-node.herokuapp.com"
    val instance = retroFit().create(TodoService::class.java)
    private fun retroFit () = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}
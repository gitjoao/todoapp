package com.joao.todoapp.services

import retrofit2.Call
import retrofit2.http.*

interface TodoService {
    @GET("/todo")
    fun getAllTodo(): Call<List<TodoModel>>

    @POST("/todo")
    fun createTodo(@Body todo: TodoModelRequest): Call<TodoModel>

    @PUT("/todo/{id}")
    fun editTodo(@Body info: TodoModelRequest, @Path("id") id: String): Call<TodoModel>

    @DELETE("/todo/{id}")
    fun deleteTodo(@Path("id") id: String): Call<TodoModel>
}
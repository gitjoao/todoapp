package com.joao.todoapp.services

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TodoModel (val id: String, val info: String)

data class TodoModelRequest (val info: String)

data class TodoModelEdit (val id: String, val info: String): Serializable
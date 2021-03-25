package com.joao.todoapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.joao.todoapp.databinding.ActivityCreateBinding
import com.joao.todoapp.services.RetroFitInstance
import com.joao.todoapp.services.TodoModel
import com.joao.todoapp.services.TodoModelEdit
import com.joao.todoapp.services.TodoModelRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateActivity:AppCompatActivity() {
    lateinit var binding: ActivityCreateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val itemTodo: TodoModelEdit? = intent?.extras?.getSerializable("todoItem") as TodoModelEdit?
        val texto: String? = intent?.extras?.getSerializable("titulo") as String?

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create)
        binding.buttonSave.setOnClickListener {
            saveTodo(binding.inputTodo.text.trim().toString())
        }

        if (itemTodo != null) {
            binding.toolbarDefault.textoTitulo.text = "Editar"
            binding.inputTodo.setText(itemTodo.info)
            binding.stringTodo.text = texto
            binding.buttonSave.setOnClickListener {
                val infoEdit = TodoModelRequest (binding.inputTodo.text.trim().toString())
                editTodo(infoEdit, itemTodo.id)
            }
        } else {
            binding.toolbarDefault.textoTitulo.text = "Criar"
            binding.buttonSave.setOnClickListener {
                saveTodo(binding.inputTodo.text.trim().toString())
            }
        }
        binding.toolbarDefault.buttonBack.setOnClickListener {
            finish()
        }

    }

    fun saveTodo(info: String) {
        val retroFit = RetroFitInstance.instance
        val bodyReques: TodoModelRequest = TodoModelRequest( info)
        retroFit.createTodo(bodyReques).enqueue(object: Callback<TodoModel>{
            override fun onResponse(call: Call<TodoModel>, response: Response<TodoModel>) {
                if(response.isSuccessful) {
                    finish()
                }
            }

            override fun onFailure(call: Call<TodoModel>, t: Throwable) {
                Toast.makeText(applicationContext,"Nao salvou", Toast.LENGTH_LONG).show()
            }
        })
    }
    fun editTodo(info: TodoModelRequest, id: String) {
        val retroFit = RetroFitInstance.instance
        retroFit.editTodo(info, id).enqueue(object: Callback<TodoModel>{
            override fun onResponse(call: Call<TodoModel>, response: Response<TodoModel>) {
                if(response.isSuccessful) {
                    finish()
                }
            }

            override fun onFailure(call: Call<TodoModel>, t: Throwable) {
                Toast.makeText(applicationContext,"Nao salvou", Toast.LENGTH_LONG).show()
            }
        })
    }
}
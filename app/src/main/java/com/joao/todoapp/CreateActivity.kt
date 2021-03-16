package com.joao.todoapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.joao.todoapp.services.RetroFitInstance
import com.joao.todoapp.services.TodoModel
import com.joao.todoapp.services.TodoModelEdit
import com.joao.todoapp.services.TodoModelRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateActivity:AppCompatActivity() {
    lateinit var input_todo: EditText
    lateinit var buttonSave: Button
    lateinit var string_todo: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val itemTodo: TodoModelEdit? = intent?.extras?.getSerializable("todoItem") as TodoModelEdit?
        val texto: String? = intent?.extras?.getSerializable("titulo") as String?

        setContentView(R.layout.activity_create)
        input_todo = findViewById(R.id.input_todo)
        buttonSave = findViewById(R.id.button_save)

        string_todo = findViewById(R.id.string_todo)

        buttonSave.setOnClickListener {
            saveTodo(input_todo.text.trim().toString())
        }

        if (itemTodo != null) {
            input_todo.setText(itemTodo.info)
            string_todo.text = texto
            buttonSave.setOnClickListener {
                val infoEdit = TodoModelRequest (input_todo.text.trim().toString())
                editTodo(infoEdit, itemTodo.id)
            }
        } else {
            buttonSave.setOnClickListener {
                saveTodo(input_todo.text.trim().toString())
            }
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
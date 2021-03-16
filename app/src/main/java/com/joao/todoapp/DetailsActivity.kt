package com.joao.todoapp

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
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

class DetailsActivity:AppCompatActivity(), View.OnClickListener {
    lateinit var buttonEdit: Button
    lateinit var string_todo: TextView
    lateinit var buttonDelete: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        buttonEdit = findViewById(R.id.button_edit)
        string_todo = findViewById(R.id.string_todo)

        buttonDelete = findViewById(R.id.button_delete)

        val itemTodo:TodoModelEdit? = intent?.extras?.getSerializable("todoItem") as TodoModelEdit?
        if (itemTodo != null){
            string_todo.text = itemTodo.info
            buttonDelete.setOnClickListener {
                deleteTodo(itemTodo.id)
            }
            buttonEdit.setOnClickListener {
                val intent: Intent = Intent(applicationContext, CreateActivity::class.java)
                val bundle: Bundle = Bundle()
                bundle.putSerializable("todoItem", itemTodo)
                bundle.putString("titulo", "Editar todo")
                intent.putExtras(bundle)
                startActivity(intent)
                finish()
            }
        }
    }

    fun deleteTodo(id: String) {
        val retroFit = RetroFitInstance.instance
        retroFit.deleteTodo(id).enqueue(object: Callback<TodoModel> {
            override fun onResponse(call: Call<TodoModel>, response: Response<TodoModel>) {
                if(response.isSuccessful) {
                    finish()
                }
            }

            override fun onFailure(call: Call<TodoModel>, t: Throwable) {
                Toast.makeText(applicationContext,"Nao deletou", Toast.LENGTH_LONG).show()
            }
        })
    }
    override fun onClick(v: View) {
        val intent = Intent(v.context, CreateActivity::class.java)
        startActivity(intent)
    }
}
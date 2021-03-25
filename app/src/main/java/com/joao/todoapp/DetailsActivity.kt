package com.joao.todoapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.joao.todoapp.databinding.ActivityDetailsBinding
import com.joao.todoapp.services.RetroFitInstance
import com.joao.todoapp.services.TodoModel
import com.joao.todoapp.services.TodoModelEdit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsActivity:AppCompatActivity(), View.OnClickListener {
   lateinit var binding: ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)

        val itemTodo:TodoModelEdit? = intent?.extras?.getSerializable("todoItem") as TodoModelEdit?
        if (itemTodo != null){
            binding.stringTodo.text = itemTodo.info
            binding.buttonDelete.setOnClickListener {
                deleteTodo(itemTodo.id)
            }
            binding.buttonEdit.setOnClickListener {
                val intent: Intent = Intent(applicationContext, CreateActivity::class.java)
                val bundle: Bundle = Bundle()
                bundle.putSerializable("todoItem", itemTodo)
                bundle.putString("titulo", "Editar todo")
                intent.putExtras(bundle)
                startActivity(intent)
                finish()
            }
        }
        binding.toolbarDefault.textoTitulo.text = "Detalhes"
        binding.toolbarDefault.buttonBack.setOnClickListener {
            finish()
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
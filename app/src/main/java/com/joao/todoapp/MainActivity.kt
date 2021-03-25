package com.joao.todoapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joao.todoapp.adapter.TodoAdapter
import com.joao.todoapp.databinding.ActivityMainBinding
import com.joao.todoapp.databinding.ToolbarDefaultBinding
import com.joao.todoapp.services.RetroFitInstance
import com.joao.todoapp.services.TodoModel
import com.joao.todoapp.services.TodoModelEdit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.Instant

class MainActivity:AppCompatActivity(), View.OnClickListener, TodoAdapter.Click {
    lateinit var adpter: TodoAdapter
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        adpter = TodoAdapter(this)
        binding.buttonOpen.setOnClickListener(this)

        binding.toolbarDefault.textoTitulo.text = "Lista"
        binding.toolbarDefault.buttonBack.visibility = View.GONE
        setUp()
    }

    override fun onResume() {
        super.onResume()
        val retroFit = RetroFitInstance.instance
        retroFit.getAllTodo().enqueue(object : Callback<List<TodoModel>> {
            override fun onFailure(call: Call<List<TodoModel>>, t: Throwable) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call<List<TodoModel>>, response: Response<List<TodoModel>>) {
                if (response.isSuccessful) {
                    val paramListTodoModel: ArrayList<TodoModel> = response.body() as ArrayList<TodoModel>
                    adpter.setList(paramListTodoModel)
                }
            }

        })
    }
    fun setUp() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = adpter
    }

    override fun getItem(todoItem: TodoModel) {
        val todoItem:TodoModelEdit = TodoModelEdit(todoItem.id, todoItem.info)
        val intent: Intent = Intent(applicationContext, DetailsActivity::class.java)
        val bundle: Bundle = Bundle()
        bundle.putSerializable("todoItem", todoItem)
        intent.putExtras(bundle)
        startActivity(intent)

    }

    override fun onClick(v: View) {
        val intent: Intent = Intent(v.context, CreateActivity::class.java)
        startActivity(intent)
    }
}
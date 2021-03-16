package com.joao.todoapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joao.todoapp.adapter.TodoAdapter
import com.joao.todoapp.services.RetroFitInstance
import com.joao.todoapp.services.TodoModel
import com.joao.todoapp.services.TodoModelEdit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.Instant

class MainActivity:AppCompatActivity(), View.OnClickListener, TodoAdapter.Click {
    lateinit var buttonOpen: Button
    lateinit var recyclerViewTodo: RecyclerView
    lateinit var adpter: TodoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adpter = TodoAdapter(arrayListOf(), this)

        buttonOpen = findViewById(R.id.button_open)
        buttonOpen.setOnClickListener(this)
        recyclerViewTodo = findViewById(R.id.recyclerView)
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
        recyclerViewTodo.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerViewTodo.adapter = adpter
    }

    override fun getItem(todoItem: TodoModel) {
        val todoItem:TodoModelEdit = TodoModelEdit(todoItem.id, todoItem.info)
        val intent: Intent = Intent(applicationContext, DetailsActivity::class.java)
        val bundle: Bundle = Bundle()
        bundle.putSerializable("todoItem", todoItem)
        intent.putExtras(bundle)
        startActivity(intent)

        Toast.makeText(applicationContext, todoItem.id, Toast.LENGTH_LONG).show()
    }

    override fun onClick(v: View) {
        val intent: Intent = Intent(v.context, CreateActivity::class.java)
        startActivity(intent)
    }
}
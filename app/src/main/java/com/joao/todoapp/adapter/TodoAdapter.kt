package com.joao.todoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.joao.todoapp.R
import com.joao.todoapp.services.TodoModel

class TodoAdapter (val callBack: Click):RecyclerView.Adapter<TodoAdapter.ViewHolder>() {
    private val list: ArrayList<TodoModel> = arrayListOf()
    interface Click {
     fun getItem (todoItem: TodoModel)
    }
    fun setList (list: ArrayList<TodoModel>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
     class ViewHolder(val view: View): RecyclerView.ViewHolder(view) {
        fun bind(item: TodoModel) {
            val view = view.findViewById<TextView>(R.id.item_todo)
            view.text = item.info
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.itemView.setOnClickListener { callBack.getItem(item) }
        holder.bind(item)
    }
}
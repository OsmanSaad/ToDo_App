package com.example.todo.ui.fragments.todoList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.data.models.Task
import com.example.todo.databinding.TodoItemBinding

class toDoList_Adapter(var data:List<Task>?):RecyclerView.Adapter<toDoList_Adapter.viewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        var binding = TodoItemBinding.inflate(LayoutInflater.from(parent.context) , parent,false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
       data?.let {
           var item = it[position]
           holder.binding.item = item
       }

    }

    override fun getItemCount(): Int = data?.size ?: 0


    fun setdata(newData:List<Task>?){
        newData?.let {
            val diffutill = DiffUtil.calculateDiff(ToDoTaskDiffUtil(this.data!!,newData))
            this.data = newData
            diffutill.dispatchUpdatesTo(this)
        }
    }




    class viewHolder(val binding:TodoItemBinding):RecyclerView.ViewHolder(binding.root){

    }


}
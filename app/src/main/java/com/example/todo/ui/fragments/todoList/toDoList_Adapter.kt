package com.example.todo.ui.fragments.todoList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.data.models.Task
import com.example.todo.databinding.TodoItemBinding
import com.example.todo.ui.todoDeatailsActivity

class toDoList_Adapter(var data:List<Task>?,val listner:OnItemClickListner):RecyclerView.Adapter<toDoList_Adapter.viewHolder>(){
     var onItemDoneClickListner: OnItemDoneClickListner? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        var binding = TodoItemBinding.inflate(LayoutInflater.from(parent.context) , parent,false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
       data?.let {
           var item = it[position]
           holder.binding.item = item
           if(item.isdone){
               holder.binding.verticalView.setBackgroundColor(ContextCompat.getColor(holder.binding.root.context,R.color.done_color))
               holder.binding.taskTitle.setTextColor(ContextCompat.getColor(holder.binding.root.context,R.color.done_color))
               holder.binding.done.visibility = View.VISIBLE
               holder.binding.isdone.visibility = View.INVISIBLE
           }
           else{
               holder.binding.verticalView.setBackgroundColor(ContextCompat.getColor(holder.binding.root.context,R.color.blue))
               holder.binding.taskTitle.setTextColor(ContextCompat.getColor(holder.binding.root.context,R.color.blue))
               holder.binding.done.visibility = View.INVISIBLE
               holder.binding.isdone.visibility = View.VISIBLE
           }
            holder.binding.root.setOnClickListener {
           listner.onItemClick(position,item)
       }
           holder.binding.isdone.setOnClickListener {
               var itemIsDone = item.copy()
               itemIsDone.isdone = true
               onItemDoneClickListner?.OnItemDoneClick(itemIsDone)
           }

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
package com.example.todo.ui.fragments.todoList

import androidx.recyclerview.widget.DiffUtil
import com.example.todo.data.models.Task

class ToDoTaskDiffUtil(val oldList:List<Task>,val newList:List<Task>):DiffUtil.Callback() {
    override fun getOldListSize(): Int  = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}
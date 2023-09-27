package com.example.todo.ui.fragments.todoList.listner

import com.example.todo.data.models.Task

interface OnItemClickListner {
    fun onItemClick(position:Int,task:Task)
}
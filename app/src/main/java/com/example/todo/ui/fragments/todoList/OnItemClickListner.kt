package com.example.todo.ui.fragments.todoList

import com.example.todo.data.models.Task

interface OnItemClickListner {
    fun onItemClick(position:Int,task:Task)
}
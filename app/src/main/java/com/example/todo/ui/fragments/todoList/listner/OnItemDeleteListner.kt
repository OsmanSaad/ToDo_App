package com.example.todo.ui.fragments.todoList.listner

import com.example.todo.data.models.Task

interface OnItemDeleteListner {
    fun onItemDelete(task:Task)
}
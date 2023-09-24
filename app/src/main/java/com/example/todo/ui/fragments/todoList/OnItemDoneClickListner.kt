package com.example.todo.ui.fragments.todoList

import com.example.todo.data.models.Task

interface OnItemDoneClickListner {
    fun OnItemDoneClick(task: Task)
}
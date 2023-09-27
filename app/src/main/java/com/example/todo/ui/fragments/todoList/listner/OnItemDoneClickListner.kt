package com.example.todo.ui.fragments.todoList.listner

import com.example.todo.data.models.Task

interface OnItemDoneClickListner {
    fun OnItemDoneClick(task: Task)
}
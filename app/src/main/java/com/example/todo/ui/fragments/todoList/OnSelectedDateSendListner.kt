package com.example.todo.ui.fragments.todoList

import java.time.LocalDate

interface OnSelectedDateSendListner {
    fun onSelectedDateSend(isSend:Boolean,selectedDate:LocalDate?)
}
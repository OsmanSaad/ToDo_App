package com.example.todo.ui.fragments.todoList.listner

import java.time.LocalDate

interface OnSelectedDateSendListner {
    fun onSelectedDateSend(isSend:Boolean,selectedDate:LocalDate?)
}
package com.example.todo.ui.fragments.todoList

import java.time.LocalDate

interface OnSelectedDateListner {
    fun onSelectedDate(selectedDate:LocalDate?)
}
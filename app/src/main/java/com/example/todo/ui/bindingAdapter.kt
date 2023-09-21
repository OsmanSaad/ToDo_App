package com.example.todo.ui

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.data.models.Task
import com.example.todo.ui.fragments.todoList.toDoList_Adapter
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.Locale

@BindingAdapter("app:error")
fun setErrorMessage(textInputLayout: TextInputLayout, errorMessage: String?) {
    textInputLayout.error = errorMessage
}

@BindingAdapter("app:text")
fun setDate(textview:TextView,date:Long){
    var sdf = SimpleDateFormat("dd-M-yyyy", Locale.getDefault())
    var dateText = sdf.format(date)
    textview.text = dateText
}

@BindingAdapter("itemList")
fun setDataList (recyclerView:RecyclerView,data:List<Task>?){
   var adapter =  recyclerView.adapter as toDoList_Adapter

    //adapter.data = data
    if(data!=null)
        adapter.setdata(data)
    else{
        adapter.setdata(emptyList())
    }

}
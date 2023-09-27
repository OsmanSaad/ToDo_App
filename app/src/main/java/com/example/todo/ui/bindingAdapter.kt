package com.example.todo.ui

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.data.models.Task
import com.example.todo.domain.DateCasting
import com.example.todo.ui.fragments.todoList.adapter.toDoList_Adapter
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
    if(data!=null)
        adapter.setdata(data)
    else{
        adapter.setdata(emptyList())
    }

}

@BindingAdapter("DateFormate")
fun castDate(textdateView:TextView,task:Task){
    var date = DateCasting.formateDate(task.date.toString())
    textdateView.text = date
}
//@SuppressLint("ResourceAsColor")
//@BindingAdapter("app:isdone")
//fun isDone(view: View,task:Task){
//    var viewColor:Int
//    if(task.isdone){
//        viewColor = R.color.done_color
//        if(view is ImageButton)
//            view.visibility = View.INVISIBLE
//        if(view is TextView){
//            view.visibility = View.VISIBLE
//            view.setTextColor(viewColor.toInt())
//        }
//
//        else
//            view.setBackgroundColor(viewColor.toInt())
//    }
//}
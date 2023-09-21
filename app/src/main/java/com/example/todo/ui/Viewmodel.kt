package com.example.todo.ui


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todo.convertTimeToZero
import com.example.todo.data.database.todo.MyDatabase
import com.example.todo.data.models.Task
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class Viewmodel:ViewModel() {


    private var _todoItem =  MutableLiveData<List<Task>>()
    val todoItem:LiveData<List<Task>> = _todoItem

    val todo_title =  MutableLiveData<String>()
    val todo_description =  MutableLiveData<String>()
    val todo_date =  MutableLiveData(formateDate(Date()))
    val todo_longDate =  MutableLiveData<Long>()
    val calendar =  MutableLiveData<Calendar>()
    /** Validate Live Data*/
    private var _validateTitle =  MutableLiveData<String>()
    val validateTitle:LiveData<String> = _validateTitle

    private var _validateDescription =  MutableLiveData<String>()
    val validateDescription:LiveData<String> = _validateDescription

    val dao = MyDatabase.getInstance().getDao()

    init {
       getAllData()
    }

    fun saveToDo(onComplete:()->Unit){
        //convertDate(calendar.value!!)
        if(validateData(todo_title.value, todo_description.value)){
            dao.insertTodo(Task(
                title = todo_title.value!!,
                description = todo_description.value!!,
                date = todo_longDate.value!!
            ))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        onComplete()
                    },
                    {
                        Log.d("Tag",it.message.toString())
                    }
                )
        }

    }

    fun validateData(title:String?,description:String?):Boolean{
        if(title.isNullOrBlank()){
            _validateTitle.postValue("Title is Required")
            return false
        }
        else{
            _validateTitle.value = ""
        }
        if(description.isNullOrBlank()){
            _validateDescription.postValue("Description is Required")
            return false
        }
        else{
            _validateDescription.value = ""
        }
        return true
    }

    fun postDate(date:Date){
        var currentDate:Date
        if(date.equals(null)){
            currentDate = Date()
        }
        currentDate = date
       val formatedDate =  formateDate(currentDate)
        todo_date.postValue(formatedDate)
    }
    fun postLongDate(date:Date){
        todo_longDate.postValue(date.time)
    }
    fun formateDate(date:Date):String{
        val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy")
        val inputDate = inputFormat.parse(date.toString())
        val outputFormat = SimpleDateFormat("dd-MM-yyyy")
        val outputDateString = outputFormat.format(inputDate)
        return outputDateString
    }

    fun convertDate(calender: Calendar){
        if(!calender.equals(null)){
            calender.convertTimeToZero(calender)
            postLongDate(calender.time)
            postDate(calender.time)
        }
        else return
    }

    fun getAllData(){
       dao.gatAllTodo()
           .subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe(
               {data->
                   _todoItem.postValue(data)
               },
               {
                Log.d("Tag",it.message.toString())
               }
           )
    }

}
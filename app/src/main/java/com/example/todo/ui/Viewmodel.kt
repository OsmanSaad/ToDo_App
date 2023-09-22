package com.example.todo.ui


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todo.convertTimeToZero
import com.example.todo.data.database.todo.MyDatabase
import com.example.todo.data.models.Task
import com.example.todo.ui.fragments.todoList.OnSelectedDateSendListner
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Month
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit

@RequiresApi(Build.VERSION_CODES.O)
class Viewmodel:ViewModel(),OnSelectedDateSendListner {
    /** Items Live Data*/
    private var _todoItem =  MutableLiveData<List<Task>>()
    val todoItem:LiveData<List<Task>> = _todoItem

    /**Task Details */
    val todo_title =  MutableLiveData<String>()
    val todo_description =  MutableLiveData<String>()
    val todo_date =  MutableLiveData(formateDate(Date()))
    val todo_longDate =  MutableLiveData<Long>()


    /** Validate Live Data*/
    private var _validateTitle =  MutableLiveData<String>()
    val validateTitle:LiveData<String> = _validateTitle

    /**Validation*/
    private var _validateDescription =  MutableLiveData<String>()
    val validateDescription:LiveData<String> = _validateDescription

    /**Dao*/
    val dao = MyDatabase.getInstance().getDao()

    var changeselected:LocalDate? = null
    init {
        Log.d("Tag",_todoItem.value.toString() )
        getAllData()
        Log.d("Tag",_todoItem.value.toString() )
    }


    fun saveToDo(onComplete:()->Unit){
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
                        Log.d("Tag",todo_longDate.value.toString())
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
        todo_date.postValue(null)
        var currentDate:Date
        if(date.equals(null)){
            currentDate = Date()
        }
        currentDate = date
       val formatedDate =  formateDate(currentDate)
        todo_date.postValue(formatedDate)
    }
    fun postLongDate(date:Date){
        todo_longDate.postValue(null)
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
        _todoItem.postValue(null)
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

    @RequiresApi(Build.VERSION_CODES.O)
     fun convertDateToEpoch(date:LocalDate?):String{
        val calendar = Calendar.getInstance()
        date?.let {
            calendar.set(
                date.year,
                date.monthValue.minus(1),
                date.dayOfMonth
            )
            calendar.convertTimeToZero(calendar)
            return calendar.time.time.toString()
        }
        return ""
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getToDoByDate(date:LocalDate?){
        var newDate = convertDateToEpoch(date)
        if(newDate != "" ){
            dao.getTodoByDate(newDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {data->
                        _todoItem.postValue(data)
                    },
                    {
                        Log.d("Tag",it.message.toString())
                    },

                )
        }
    }
    override fun onSelectedDateSend(isSend: Boolean, selectedDate: LocalDate?) {
        if(isSend){
            changeselected  = selectedDate
            getToDoByDate(selectedDate)
        }
        else{
            getAllData()
        }
    }
}
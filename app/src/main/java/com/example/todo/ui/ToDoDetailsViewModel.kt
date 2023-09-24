package com.example.todo.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todo.data.database.todo.MyDatabase
import com.example.todo.data.models.Task
import com.example.todo.domain.DataValidation

import com.example.todo.domain.DateCasting
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

import java.util.Calendar
import java.util.Date

class ToDoDetailsViewModel:ViewModel() {

    val todo_title = MutableLiveData<String>()

    val todo_description = MutableLiveData<String>()

    val todo_dateFormated = MutableLiveData<String>()
    val todo_id = MutableLiveData<Int>()

    private var _todo_date = MutableLiveData<Date>()
    val todo_date:LiveData<Date> = _todo_date

    /** Validate Live Data*/
    private var _validateTitle =  MutableLiveData<String>()
    val validateTitle:LiveData<String> = _validateTitle

    /**Validation*/
    private var _validateDescription =  MutableLiveData<String>()
    val validateDescription:LiveData<String> = _validateDescription

    val dao = MyDatabase.getInstance().getDao()
    fun updateToDo(oncomplete:()->Unit){
        if(DataValidation.validateData(todo_title.value,todo_description.value,_validateTitle,_validateDescription)){
            dao.updateTodo(Task(
                id = todo_id.value!!,
                title = todo_title.value!!,
                description = todo_description.value!!,
                date = todo_date.value!!
            )).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    oncomplete()
                }
        }
    }

    fun initialData(task: Task){
        todo_title.postValue(task.title)
        todo_description.postValue(task.description)
        todo_dateFormated.postValue(DateCasting.formateDate(task.date))
        _todo_date.postValue(task.date)
        todo_id.postValue(task.id)

    }

    fun InitialDate(calendar: Calendar){
        val (todoLongDate,todoDate) = DateCasting.convertDate(calendar)
        todo_dateFormated.postValue(todoDate)
        _todo_date.postValue(todoLongDate)
    }

    fun isDoneUpdate(task: Task) {
        dao.updateTodo(task)
    }

}
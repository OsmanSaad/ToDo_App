package com.example.todo.ui.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todo.data.database.todo.MyDatabase
import com.example.todo.data.models.Task
import com.example.todo.domain.DataValidation
import com.example.todo.domain.DateCasting
import com.example.todo.ui.fragments.todoList.listner.OnItemDeleteListner
import com.example.todo.ui.fragments.todoList.listner.OnItemDoneClickListner
import com.example.todo.ui.fragments.todoList.listner.OnSelectedDateSendListner
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.time.LocalDate
import java.util.Calendar
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
class Viewmodel : ViewModel(), OnSelectedDateSendListner, OnItemDoneClickListner,
    OnItemDeleteListner {
    var compositeDisposable = CompositeDisposable()

    /** Items Live Data*/
    private var _todoItem = MutableLiveData<List<Task>>()
    val todoItem: LiveData<List<Task>> = _todoItem

    /**Task Details */
    val todo_title = MutableLiveData<String>()
    val todo_description = MutableLiveData<String>()
    val todo_dateFormated = MutableLiveData(DateCasting.formateDate(Date().toString()))
    val todo_TimeSpam = MutableLiveData<Date>()
    val todo_isdone = MutableLiveData<Boolean>()

    /** Validate Live Data*/
    private var _validateTitle = MutableLiveData<String>()
    val validateTitle: LiveData<String> = _validateTitle

    /**Validation*/
    private var _validateDescription = MutableLiveData<String>()
    val validateDescription: LiveData<String> = _validateDescription

    /**Dao*/
    val dao = MyDatabase.getInstance().getDao()

    /**Show Loading*/
    private var _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading


    init {
        getAllData()
    }

    fun saveToDo(onComplete: () -> Unit) {
        if (DataValidation.validateData(
                todo_title.value,
                todo_description.value,
                _validateTitle,
                _validateDescription
            )
        ) {
            compositeDisposable.add(
                dao.insertTodo(
                    Task(
                        title = todo_title.value!!,
                        description = todo_description.value!!,
                        date = todo_TimeSpam.value!!
                    )
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            onComplete()
                        },
                        {
                            Log.d("Tag", it.message.toString())
                        }
                    )
            )
        }

    }

    fun getAllData() {
        _loading.postValue(true)
        _todoItem.postValue(null)
        compositeDisposable.add(
            dao.gatAllTodo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { data ->
                        _loading.postValue(false)

                        _todoItem.postValue(data)

                    },
                    {
                        Log.d("Tag", it.message.toString())
                    }
                )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getToDoByDate(date: LocalDate?) {
        var newDate = DateCasting.convertLocaleDateToDate(date)
        if (newDate != null) {
            compositeDisposable.add(
                dao.getTodoByDate(newDate)
                    .subscribeOn(Schedulers.io())
                    //  .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { data ->
                            _todoItem.postValue(data)
                        },
                        {
                            Log.d("Tag", it.message.toString())
                        },

                        )
            )
        }
    }

    override fun onSelectedDateSend(isSend: Boolean, selectedDate: LocalDate?) {
        if (isSend)
            getToDoByDate(selectedDate)
        else
            getAllData()
    }

    fun InitialDate(calendar: Calendar) {
        val (todoLongDate, todoDate) = DateCasting.convertDate(calendar)
        todo_TimeSpam.postValue(todoLongDate)
        todo_dateFormated.postValue(todoDate)
    }

    private fun updateIsDone(task: Task) {
        compositeDisposable.add(
            dao.updateTodo(task).subscribeOn(Schedulers.io())
                .subscribe()
        )

    }

    private fun deleteTODO(task: Task) {
        compositeDisposable.add(
            dao.deleteTodo(task)
                .subscribeOn(Schedulers.io())
                .subscribe()
        )
    }

    override fun OnItemDoneClick(task: Task) {
        updateIsDone(task)
    }

    override fun onItemDelete(task: Task) {
        deleteTODO(task)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
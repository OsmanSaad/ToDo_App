package com.example.todo.data.database.todo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todo.data.models.Task
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import java.util.Date

@Dao
interface Dao {

    @Insert
    fun insertTodo(toDo: Task):Completable

    @Delete
    fun deleteTodo(toDo: Task):Completable

    @Update
    fun updateTodo(toDo: Task):Completable

    @Query("SELECT * FROM Task")
    fun gatAllTodo():Observable<List<Task>>

    @Query("SELECT * FROM Task WHERE date ==:date ")
    fun getTodoByDate(date: Date):Observable<List<Task>>
}
package com.example.todo.data.database.todo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todo.data.models.Task

@Database(entities = [Task::class], version = 1)
abstract class MyDatabase:RoomDatabase() {

    abstract fun getDao(): Dao

    companion object{
        private var instant: MyDatabase?=null

        fun getInstance(context: Context): MyDatabase {
            if (instant ==null){
                instant = Room
                    .databaseBuilder(context.applicationContext, MyDatabase::class.java,"ToDo")
                    .build()
            }
            return instant!!
        }

        fun getInstance():MyDatabase{
            return instant!!
        }
    }
}
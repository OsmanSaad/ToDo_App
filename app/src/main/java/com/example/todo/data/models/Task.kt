package com.example.todo.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id:Int? = null,
    val title:String,
    val description:String,
    val date:Long,
    val isdone:Boolean = false
)


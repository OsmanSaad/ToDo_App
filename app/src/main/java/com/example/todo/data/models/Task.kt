package com.example.todo.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Entity
@Parcelize
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id:Int? = null,
    val title:String,
    val description:String,
    val date: Date,
    var isdone:Boolean = false
):Parcelable


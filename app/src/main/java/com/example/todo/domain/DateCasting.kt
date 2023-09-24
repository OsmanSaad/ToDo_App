package com.example.todo.domain

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.todo.convertTimeToZero
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date

object DateCasting {
    fun formateDate(date: Date):String{
        val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy")
        val inputDate = inputFormat.parse(date.toString())
        val outputFormat = SimpleDateFormat("dd-MM-yyyy")
        val outputDateString = outputFormat.format(inputDate)
        return outputDateString
    }

    fun postDate(date:Date):String{
        var currentDate:Date
        if(date.equals(null)){
            currentDate = Date()
        }
        currentDate = date
        val formatedDate =  formateDate(currentDate)
        return formatedDate
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertLocaleDateToDate(date: LocalDate?):Date?{
        val calendar = Calendar.getInstance()
        date?.let {
            calendar.set(
                date.year,
                date.monthValue.minus(1),
                date.dayOfMonth
            )
            calendar.convertTimeToZero(calendar)
            return calendar.time
        }
        return null
    }

    fun postLongDate(date:Date):Long{
        return date.time
    }

    fun convertDate(calender: Calendar):Pair<Date?,String?>{
        if(!calender.equals(null)){
            calender.convertTimeToZero(calender)
            val firstValue = calender.time
            val secoundValue = postDate(calender.time)
            return Pair(firstValue,secoundValue)
        }
        else
            return Pair(null,null)
    }

    fun convertDate2(calendar:Calendar):Date{
       calendar.convertTimeToZero(calendar)
        return calendar.time

    }
}
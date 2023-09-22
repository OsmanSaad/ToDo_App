package com.example.todo

import java.util.Calendar


fun Calendar.convertTimeToZero(calendar: Calendar){
    calendar.set(Calendar.SECOND,0)
    calendar.set(Calendar.MILLISECOND,0)
    calendar.set(Calendar.MINUTE,0)
    calendar.set(Calendar.HOUR,0)
    calendar.set(Calendar.HOUR_OF_DAY,0)
}
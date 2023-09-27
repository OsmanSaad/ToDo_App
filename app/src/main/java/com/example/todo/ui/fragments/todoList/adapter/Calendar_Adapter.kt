package com.example.todo.ui.fragments.todoList.adapter

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.todo.R
import com.example.todo.databinding.CalenderItemsBinding
import com.example.todo.domain.Constatns
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.view.ViewContainer
import com.kizitonwose.calendar.view.WeekDayBinder
import java.time.format.TextStyle
import java.util.Locale
import com.example.todo.ui.fragments.todoList.adapter.Calendar_Adapter.DayViewContainer
import com.example.todo.ui.fragments.todoList.listner.OnSelectedDateListner
import com.kizitonwose.calendar.view.WeekCalendarView
import java.time.LocalDate

class Calendar_Adapter(val calendarView: WeekCalendarView, val context:Context): WeekDayBinder<DayViewContainer> {
    private var selectedDate: LocalDate? = null
    lateinit var onSelectedDateListner: OnSelectedDateListner
     var sharedPreferences = context.getSharedPreferences("AppSettings",Context.MODE_PRIVATE)

    override fun create(view: View):DayViewContainer {
        return DayViewContainer(CalenderItemsBinding.bind(view))
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun bind(container: DayViewContainer, data: WeekDay) {
        container.binding.dayName.text = data.date.dayOfWeek.getDisplayName(
            TextStyle.SHORT,
            Locale(sharedPreferences.getString(Constatns.LANGUAGE_KEY,"en"))
        )
        container.binding.daydigit.text = data.date.dayOfMonth.toString()

        container.view.setOnClickListener {
            manageDateSelectionClick(data)
            if(selectedDate!=null){
                onSelectedDateListner.onSelectedDate(selectedDate!!)
            }
            else{
                onSelectedDateListner.onSelectedDate(null)
            }
        }

        manageSelectedDateClor(container,data)

    }
    inner class DayViewContainer(var binding: CalenderItemsBinding): ViewContainer(binding.root)


    private fun manageDateSelectionClick(data: WeekDay){
        val currentSelection = selectedDate
        if (currentSelection == data.date) {
            // If the user clicks the same date, clear selection.
            selectedDate = null
            // Reload this date so the dayBinder is called
            // and we can REMOVE the selection background.
            calendarView.notifyDateChanged(currentSelection)
        } else {
            selectedDate = data.date
            Log.d("Tag","Class $selectedDate")
            // Reload the newly selected date so the dayBinder is
            // called and we can ADD the selection background.
            calendarView.notifyDateChanged(data.date)
            if (currentSelection != null) {
                // We need to also reload the previously selected
                // date so we can REMOVE the selection background.
                calendarView.notifyDateChanged(currentSelection)
            }
        }
    }


    private fun manageSelectedDateClor(container: DayViewContainer, data: WeekDay){
        if (data.date == selectedDate) {
            calanderItemColor(container,R.color.blue)
        }
        else {
            if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES){
                calanderItemColor(container,R.color.white)
            }
            else
                calanderItemColor(container,R.color.black)
        }
    }

    private fun calanderItemColor(container:DayViewContainer,color:Int){
        container.binding.dayName
            .setTextColor(ContextCompat.getColor(context, color))
        container.binding.daydigit
            .setTextColor(ContextCompat.getColor(context, color))
    }
}
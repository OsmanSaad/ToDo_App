package com.example.todo.ui.fragments.todoList

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.todo.R
import com.example.todo.databinding.CalenderItemsBinding
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.view.ViewContainer
import com.kizitonwose.calendar.view.WeekDayBinder
import java.time.format.TextStyle
import java.util.Locale
import com.example.todo.ui.fragments.todoList.Calendar_Adapter.DayViewContainer
import com.kizitonwose.calendar.view.CalendarView
import com.kizitonwose.calendar.view.WeekCalendarView
import java.time.LocalDate

class Calendar_Adapter(val calendarView: WeekCalendarView, val context:Context): WeekDayBinder<DayViewContainer> {
    private var selectedDate: LocalDate? = null
    override fun create(view: View):DayViewContainer {
        return DayViewContainer(CalenderItemsBinding.bind(view))
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun bind(container: DayViewContainer, data: WeekDay) {
        container.binding.dayName.text = data.date.dayOfWeek.getDisplayName(
            TextStyle.SHORT,
            Locale.getDefault()
        )
        container.binding.daydigit.text = data.date.dayOfMonth.toString()

        container.view.setOnClickListener {
            manageDateSelectionClick(data)
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
            container.binding.dayName
                .setTextColor(ContextCompat.getColor(context, R.color.blue))
            container.binding.daydigit
                .setTextColor(ContextCompat.getColor(context, R.color.blue))

        } else {
            container.binding.dayName
                .setTextColor(ContextCompat.getColor(context, R.color.black))
            container.binding.daydigit
                .setTextColor(ContextCompat.getColor(context, R.color.black))
        }
    }
}
package com.example.todo.ui.fragments.todoList

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.todo.R
import com.example.todo.databinding.FragmentTodoListBinding
import com.example.todo.ui.Viewmodel
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth


class TodoListFragment : Fragment() {
   lateinit var binding:FragmentTodoListBinding
   lateinit var adapter : toDoList_Adapter
   lateinit var viewmodel:Viewmodel
   lateinit var adabter2:Calendar_Adapter
   @RequiresApi(Build.VERSION_CODES.O)
   override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_todo_list,container,false)
       adapter = toDoList_Adapter(emptyList())

       //setup calendar
         adabter2 = Calendar_Adapter(binding.calenderview,requireContext())
       binding.calenderview.dayBinder = adabter2
       val currentDate = LocalDate.now()
       val currentMonth = YearMonth.now()
       val startDate = currentMonth.minusMonths(100).atStartOfMonth() // Adjust as needed
       val endDate = currentMonth.plusMonths(100).atEndOfMonth()  // Adjust as needed
       val firstDayOfWeek = firstDayOfWeekFromLocale() // Available from the library
       binding.calenderview.setup(startDate, endDate, firstDayOfWeek)
       binding.calenderview.scrollToWeek(currentDate)
        //
       Log.d("Tag","OnCreateView")
       return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //
        Log.d("Tag","OnViewCreated")
        binding.listRecycler.adapter = adapter
        binding.lifecycleOwner = this
        viewmodel = ViewModelProvider(this).get(Viewmodel::class.java)
        binding.viewmodel = viewmodel

        adabter2.onSelectedDateListner = object :OnSelectedDateListner{
            override fun onSelectedDate(selectedDate: LocalDate?) {
                var isSend = false
                if(selectedDate!=null){
                    isSend = true
                    viewmodel.onSelectedDateSend(isSend,selectedDate)
                }else{
                    viewmodel.onSelectedDateSend(isSend,null)
                }
        }

        }

    }


}

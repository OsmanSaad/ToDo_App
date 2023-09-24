package com.example.todo.ui


import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.todo.data.models.Task
import com.example.todo.databinding.ActivityTodoDeatailsBinding
import com.example.todo.domain.Constatns
import com.example.todo.ui.fragments.todoList.OnItemDoneClickListner
import java.util.Calendar


class todoDeatailsActivity : AppCompatActivity(),OnItemDoneClickListner {
    lateinit var binding: ActivityTodoDeatailsBinding
    lateinit var viewmodelDeatails:ToDoDetailsViewModel
    var calender = Calendar.getInstance()
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoDeatailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewmodelDeatails = ViewModelProvider(this).get(ToDoDetailsViewModel::class.java)
        binding.viewmodeldetails = viewmodelDeatails
        binding.lifecycleOwner = this
        val task  = intent.getParcelableExtra<Task>(Constatns.TASK_DETAILS)
        task?.let {
            viewmodelDeatails.initialData(task)
        }

        binding.taskDate.setOnClickListener {
            showDatePicker(calender)
        }

        binding.saveTask.setOnClickListener {
            viewmodelDeatails.updateToDo {
                finish()
            }
        }

        binding.arrowBack.setOnClickListener { finish() }

    }

    fun showDatePicker(calendar: Calendar) {
        val datePickerDialog = DatePickerDialog(this)
        datePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(year,month,dayOfMonth)
            viewmodelDeatails.InitialDate(calendar)

        }

        datePickerDialog.show()
    }

    override fun OnItemDoneClick(task: Task) {
        viewmodelDeatails.isDoneUpdate(task)
    }

}
package com.example.todo.ui.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.todo.R
import com.example.todo.databinding.FragmentBottomSheetBinding
import com.example.todo.ui.Viewmodel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import java.util.Calendar


class bottomSheetFragment : BottomSheetDialogFragment() {
    lateinit var calender:Calendar
    lateinit var binding: FragmentBottomSheetBinding
     private lateinit var viewModel: Viewmodel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_sheet,container,false)
        viewModel = ViewModelProvider(this).get(Viewmodel::class.java)
        binding.viewModel = viewModel

        binding.lifecycleOwner = this
        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    viewModel.todo_title.observe(viewLifecycleOwner) {
        Log.d("Tag", it.toString())
    }

        calender = Calendar.getInstance()
        binding.taskDate.setOnClickListener {
            showDatePicker(calender)
        }
        viewModel.convertDate(calender)





        binding.saveTask.setOnClickListener {
            viewModel.saveToDo {
                Snackbar.make(binding.root,"Inserted",Snackbar.LENGTH_LONG).show()
                dismiss()
            }

        }
    }

    fun showDatePicker(calendar: Calendar) {

        val datePickerDialog = DatePickerDialog(requireContext())
        datePickerDialog.setOnDateSetListener { view, year, month, dayOfMonth ->
             calendar.set(year,month,dayOfMonth)
            viewModel.convertDate(calender)

        }

        datePickerDialog.show()
    }

}
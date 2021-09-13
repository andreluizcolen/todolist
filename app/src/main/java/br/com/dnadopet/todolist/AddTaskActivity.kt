package br.com.dnadopet.todolist

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.dnadopet.todolist.databinding.ActivityAddTaskBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

// Tem que colocar a instruççao no Gradle para iniciar o BINDING
class AddTaskActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        insertListerners()
    }

    private fun insertListerners() {

        binding.tilDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Selecione Data da Tarefa")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build()
            datePicker.addOnPositiveButtonClickListener {
                // Para corrigir o erro da data vir com o dia anterior
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                binding.tilDate.text = Date(it + offset).format()
            }
            datePicker.show(supportFragmentManager,"DATE_PICKER_TAG")
        }

        binding.tilHours.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setTitleText("Selecione a Hora da Tarefa")
                .build()
            timePicker.addOnPositiveButtonClickListener {
                val hours = if (timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour
                val minutes = if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
                binding.tilHours.text = "$hours:$minutes"
            }
            timePicker.show(supportFragmentManager,null)
        }
    }
}
package com.example.indoor
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import com.example.indoor.databinding.ActivityCalenderBinding
import com.example.indoor.receiver.AlarmReceiver
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*



class CalenderActivity : AppCompatActivity(){

    private lateinit var binding: ActivityCalenderBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var picker: MaterialTimePicker
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalenderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        auth = Firebase.auth

        createNotificationChannel()

        binding.button3.setOnClickListener{
            showTimePickerAndSetAlarm()
        }


    }

    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val name = "indoorReminderChannel"
            val descriptionText = "Channel For Alarm Manager"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("indoorChannel", name, importance).apply {
                description = descriptionText
            }

            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun setAlarm(timeInMillis: Long) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                timeInMillis,
                pendingIntent
            )

        Toast.makeText(this, "Alarm set successfully", Toast.LENGTH_SHORT).show()
    }

    private fun showTimePickerAndSetAlarm(){

        val myCalendar = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener { datePicker, year, month, dayOfMonth ->

            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        }
        val timePicker = TimePickerDialog.OnTimeSetListener { timePicker, hour, min ->

            myCalendar.set(Calendar.HOUR_OF_DAY, hour)
            myCalendar.set(Calendar.MINUTE, min)


        }
        // Creacion de un alertDialog --> Ventana q se abre dentro de un mismo activity.
        // Se pasan los valores
        val builder = AlertDialog.Builder(this@CalenderActivity)
        val view = layoutInflater.inflate(R.layout.dialog_calendar, null)
        // Se pasa la vista al builder
        builder.setView(view)
        //Se crea el Dialog
        val dialog = builder.create()
        dialog.show()
        //Se recuperan los valores del dialog

        val titleDialog = view.findViewById<EditText>(R.id.titleET)
        val messageDialog = view.findViewById<EditText>(R.id.messageET)
        val btnDialog = view.findViewById<AppCompatButton>(R.id.btnDialogCalendar)

        // Al apretar btnDialog se setea la alarma con los valores obtenidos

        btnDialog.setOnClickListener {

            setAlarm(myCalendar.timeInMillis)
            dialog.cancel()

        }

        //Se abre el calendario y el reloj para seleccionar los valores

        TimePickerDialog(this, timePicker, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true).show()

        DatePickerDialog(this, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)).show()


    }


    private fun cancelAlarm(){

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)

        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

        alarmManager.cancel(pendingIntent)

        Toast.makeText(this, "Alarm Cancelled", Toast.LENGTH_LONG).show()

    }

}

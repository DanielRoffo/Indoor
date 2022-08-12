package com.example.indoor

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.indoor.databinding.ActivitySetNewParametersBinding
import java.math.RoundingMode.valueOf

class SetNewParametersActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySetNewParametersBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySetNewParametersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Cambiar titulo del action bar, sacarle linea divisoria

        title = "Set New Parameters"
        val actionbar = supportActionBar
        actionbar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0f

        openCards()
        dayOfWeekSelect()

        binding.addingBtn.setOnClickListener {
            var error = checkParameters()
            if (error == 0){
                addParameters()
                val intent = Intent(this, MainActivity2::class.java)
                this.startActivity(intent)

            }
        }

    }

    private fun addParameters() {

        var plantsInfoDao = PlantsInfoDao()

        val plantName = binding.plantName.text.toString()
        val substrate = binding.substrate.text.toString()
        val fertilizers = binding.fertilizers.text.toString()
        val pesticides = binding.pesticides.text.toString()
        val description = binding.description.text.toString()
        var harvestTimeDays: Int? = null
        var harvestTimeMonths: Int? = null
        var harvestTimeYears: Int? = null
        if (binding.harvestTimeDays.text.isNotBlank()){
            harvestTimeDays = binding.harvestTimeDays.text.toString().toInt()
        }
        if (binding.harvestTimeMonths.text.isNotBlank()){
            harvestTimeMonths = binding.harvestTimeMonths.text.toString().toInt()
        }
        if (binding.harvestTimeYears.text.isNotBlank()){
            harvestTimeYears = binding.harvestTimeYears.text.toString().toInt()
        }

        val minTempDay = binding.MinTempDay.text.toString().toDouble()
        val maxTempDay = binding.MaxTempDay.text.toString().toDouble()
        val minTempNight = binding.MinTempNight.text.toString().toDouble()
        val maxTempNight = binding.MaxTempNight.text.toString().toDouble()

        val minHumDay = binding.MinHumDay.text.toString().toDouble()
        val maxHumDay = binding.MaxTempDay.text.toString().toDouble()
        val minHumNight = binding.MinTempNight.text.toString().toDouble()
        val maxHumNight = binding.MaxTempNight.text.toString().toDouble()

        val dayStartTimeHour = binding.dayStartTimeHour.text.toString().toInt()
        val dayStartTimeMin = binding.dayStartTimeMin.text.toString().toInt()
        val nightStartTimeHour = binding.nightStartTimeHour.text.toString().toInt()
        val nightStartTimeMin = binding.nightStartTimeMin.text.toString().toInt()

        var ventTime: Int? = null
        var ventIntervals: Int? = null
        if (binding.ventTime.text.isNotBlank()){
            ventTime = binding.ventTime.text.toString().toInt()
        }
        if (binding.ventIntervals.text.isNotBlank()){
            ventIntervals = binding.ventIntervals.text.toString().toInt()
        }

        var irriStartTimeHour: Int? = null
        var irriStartTimeMin: Int? = null
        var irriTime: Int? = null
        var dayMo: Int = 0
        var dayTu: Int = 0
        var dayWe: Int = 0
        var dayTh: Int = 0
        var dayFr: Int = 0
        var daySa: Int = 0
        var daySu: Int = 0

        if (binding.monday.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
            || binding.tuesday.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
            || binding.wednesday.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
            || binding.thursday.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
            || binding.friday.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
            || binding.saturday.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
            || binding.sunday.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))){

                irriStartTimeHour = binding.irriStartTimeHour.text.toString().toInt()
                irriStartTimeMin = binding.irriStartTimeMin.text.toString().toInt()
                irriTime = binding.irriTime.text.toString().toInt()

                if (binding.monday.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))){
                    dayMo = 1
                }
                if (binding.tuesday.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))){
                    dayTu = 1
                }
                if (binding.wednesday.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))){
                    dayWe = 1
                }
                if (binding.thursday.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))){
                    dayTh = 1
                }
                if (binding.friday.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))){
                    dayFr = 1
                }
                if (binding.saturday.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))){
                    daySa = 1
                }
                if (binding.sunday.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))){
                    daySu = 1
                }

            }

        plantsInfoDao.addPlantInfo(plantName, substrate, fertilizers, pesticides, description, harvestTimeDays, harvestTimeMonths, harvestTimeYears,
                                    minTempDay, maxTempDay, minTempNight, maxTempNight, minHumDay, maxHumDay, minHumNight, maxHumNight,
                                    dayStartTimeHour, dayStartTimeMin, nightStartTimeHour, nightStartTimeMin, ventTime, ventIntervals,
                                    irriStartTimeHour, irriStartTimeMin, irriTime, dayMo, dayTu, dayWe, dayTh, dayFr, daySa, daySu)

        Toast.makeText(this,"Plant parameters successfully added",Toast.LENGTH_SHORT).show()

    }

    private fun checkParameters(): Int {

        //Error Found  Variable
        var error= 0
        //General Info Variables
        var plantName: String ; var substrate: String ; var fertilizers: String ; var pesticides: String ;var description: String ;
        var harvestTimeDays: Int ; var harvestTimeMonths: Int ; var harvestTimeYears: Int
        //Temperature Variables
        var minTempDay : Double ; var maxTempDay : Double ; var minTempNight : Double ; var maxTempNight : Double ;
        //Humidity Variables
        var minHumDay : Double ; var maxHumDay : Double ; var minHumNight : Double ; var maxHumNight : Double ;
        //Photoperiod Variables
        var dayStartTimeHour: Int ; var dayStartTimeMin: Int ; var nightStartTimeHour: Int ; var nightStartTimeMin: Int ;
        //Ventilation Variables
        var ventTime: Int ; var ventIntervals: Int
        //Photoperiod Variables
        var irriStartTimeHour: Int ; var irriStartTimeMin: Int ; var irriTime: Int

        //General Info
        plantName = binding.plantName.text.toString()
        if (binding.plantName.text.isBlank()){
            Toast.makeText(this, "Plant Name is missing, please enter one", Toast.LENGTH_SHORT).show()
            error = 1
        }
        if (binding.harvestTimeDays.text.isNotBlank()){
            harvestTimeDays = binding.harvestTimeDays.text.toString().toInt()
            if ((harvestTimeDays < 0) || (harvestTimeDays > 31)){
                Toast.makeText(this, "Days in \"Harvest Time\" must be between 0 - 31 days", Toast.LENGTH_SHORT).show()
                error = 1
            }
        }
        if (binding.harvestTimeMonths.text.isNotBlank()){
            harvestTimeMonths = binding.harvestTimeMonths.text.toString().toInt()
            if ((harvestTimeMonths < 0) || (harvestTimeMonths > 12)){
                Toast.makeText(this, "Months in \"Harvest Time\" must be between 0 - 12 months", Toast.LENGTH_SHORT).show()
                error = 1
            }
        }
        if (binding.harvestTimeYears.text.isNotBlank()){
            harvestTimeYears = binding.harvestTimeYears.text.toString().toInt()
            if ((harvestTimeYears < 0)){
                Toast.makeText(this, "Years in \"Harvest Time\" must be positive numbers", Toast.LENGTH_SHORT).show()
                error = 1
            }
        }
        substrate = binding.substrate.text.toString()
        fertilizers = binding.fertilizers.text.toString()
        pesticides = binding.pesticides.text.toString()
        description = binding.description.text.toString()

        //Temperature

        //Comprobaciones Empty/NotEmpty y rangos de temperatura
        if (binding.MinTempDay.text.isNotBlank()){
            minTempDay = binding.MinTempDay.text.toString().toDouble()
            if ((minTempDay < 0) || (minTempDay > 40)){
                Toast.makeText(this, "Minimum temperature (Day) must be between 0 - 40 °C", Toast.LENGTH_SHORT).show()
                error = 2
            }
        }else{
            Toast.makeText(this, "Minimum temperature (Day) is missing", Toast.LENGTH_SHORT).show()
            error = 2
        }

        if (binding.MaxTempDay.text.isNotBlank()){
            maxTempDay = binding.MaxTempDay.text.toString().toDouble()
            if ((maxTempDay < 0) || (maxTempDay > 40)){
                Toast.makeText(this, "Maximum temperature (Day) must be between 0 - 40 °C", Toast.LENGTH_SHORT).show()
                error = 2
            }
        }else{
            Toast.makeText(this, "Maximum temperature (Day) is missing", Toast.LENGTH_SHORT).show()
            error = 2
        }

        if (binding.MinTempNight.text.isNotBlank()){
            minTempNight = binding.MinTempNight.text.toString().toDouble()
            if ((minTempNight < 0) || (minTempNight > 40)){
                Toast.makeText(this, "Minimum temperature (Night) must be between 0 - 40 °C", Toast.LENGTH_SHORT).show()
                error = 2
            }
        }else{
            Toast.makeText(this, "Minimum temperature (Night) is missing", Toast.LENGTH_SHORT).show()
            error = 2
        }
        if (binding.MaxTempNight.text.isNotBlank()){
            maxTempNight = binding.MaxTempNight.text.toString().toDouble()
            if ((maxTempNight < 0) || (maxTempNight > 40)){
                Toast.makeText(this, "Maximum temperature (Night) must be between 0 - 40 °C", Toast.LENGTH_SHORT).show()
                error = 2
            }
        }else{
            Toast.makeText(this, "Maximum temperature (Night) is missing", Toast.LENGTH_SHORT).show()
            error = 2
        }

        //Comprobaciones para que el menor valor no supere al mayor valor de temperatura
        //Y que tengan un rango de 3 Centigrados de diferencia.

        if (error != 2){
            var total: Double
            minTempDay = binding.MinTempDay.text.toString().toDouble()
            maxTempDay = binding.MaxTempDay.text.toString().toDouble()
            minTempNight = binding.MinTempNight.text.toString().toDouble()
            maxTempNight = binding.MaxTempNight.text.toString().toDouble()

            total = maxTempDay - minTempDay
            if (total < 3){
                Toast.makeText(this, "Maximum temperature (Day) must be at least 3°C higher than the Minimum temperature (Day)", Toast.LENGTH_SHORT).show()
                error = 2
            }

            total = maxTempNight - minTempNight
            if (total < 3){
                Toast.makeText(this, "Maximum temperature (Night) must be at least 3°C higher than the Minimum temperature (Night)", Toast.LENGTH_SHORT).show()
                error = 2
            }

        }

        //Humidity

        //Comprobaciones Empty/NotEmpty y rangos de Humedad
        if (binding.MinHumDay.text.isNotBlank()){
            minHumDay = binding.MinHumDay.text.toString().toDouble()
            if ((minHumDay < 0) || (minHumDay > 100)){
                Toast.makeText(this, "Minimum humidity (Day) must be between 0 - 100%", Toast.LENGTH_SHORT).show()
                error = 3
            }
        }else{
            Toast.makeText(this, "Minimum humidity (Day) is missing", Toast.LENGTH_SHORT).show()
            error = 3
        }
        if (binding.MaxHumDay.text.isNotBlank()){
            maxHumDay = binding.MaxHumDay.text.toString().toDouble()
            if ((maxHumDay < 0) || (maxHumDay > 100)){
                Toast.makeText(this, "Maximum humidity (Day) must be between 0 - 100%", Toast.LENGTH_SHORT).show()
                error = 3
            }
        }else{
            Toast.makeText(this, "Maximum humidity (Day) is missing", Toast.LENGTH_SHORT).show()
            error = 3
        }

        if (binding.MinHumNight.text.isNotBlank()){
            minHumNight = binding.MinHumNight.text.toString().toDouble()
            if ((minHumNight < 0) || (minHumNight > 100)){
                Toast.makeText(this, "Minimum humidity (Night) must be between 0 - 100%", Toast.LENGTH_SHORT).show()
                error = 3
            }
        }else{
            Toast.makeText(this, "Minimum humidity (Night) is missing", Toast.LENGTH_SHORT).show()
            error = 3
        }
        if (binding.MaxHumNight.text.isNotBlank()){
            maxHumNight = binding.MaxHumNight.text.toString().toDouble()
            if ((maxHumNight < 0) || (maxHumNight > 100)){
                Toast.makeText(this, "Maximum humidity (Night) must be between 0 - 100%", Toast.LENGTH_SHORT).show()
                error = 3
            }
        }else{
            Toast.makeText(this, "Maximum humidity (Night) is missing", Toast.LENGTH_SHORT).show()
            error = 3
        }

        //Comprobaciones para que el menor valor no supere al mayor valor de Humedad
        //Y que tengan un rango de 5% de diferencia.

        if (error != 3){
            var total: Double
            minHumDay = binding.MinTempDay.text.toString().toDouble()
            maxHumDay = binding.MaxTempDay.text.toString().toDouble()
            minHumNight = binding.MinTempNight.text.toString().toDouble()
            maxHumNight = binding.MaxTempNight.text.toString().toDouble()

            total = maxHumDay - minHumDay
            if (total < 5){
                Toast.makeText(this, "Maximum humidity (Day) must be at least 5% higher than the Minimum humidity (Day)", Toast.LENGTH_SHORT).show()
                error = 3
            }

            total = maxHumNight - minHumNight
            if (total < 5){
                Toast.makeText(this, "Maximum humidity (Night) must be at least 5% higher than the Minimum humidity (Night)", Toast.LENGTH_SHORT).show()
                error = 3
            }

        }

        //Photoperiod

        if (binding.dayStartTimeHour.text.isNotBlank()){
            dayStartTimeHour = binding.dayStartTimeHour.text.toString().toInt()
            if ((dayStartTimeHour < 0) || (dayStartTimeHour > 23)){
                Toast.makeText(this, "Start of daylight (Hours) must be between 0 - 23 hs", Toast.LENGTH_SHORT).show()
                error = 4
            }
        }else{
            Toast.makeText(this, "Start of daylight (Hours) is missing", Toast.LENGTH_SHORT).show()
            error = 4
        }
        if (binding.dayStartTimeMin.text.isNotBlank()){
            dayStartTimeMin = binding.dayStartTimeMin.text.toString().toInt()
            if ((dayStartTimeMin < 0) || (dayStartTimeMin > 59)){
                Toast.makeText(this, "Start of daylight (Minutes) must be between 0 - 59 min", Toast.LENGTH_SHORT).show()
                error = 4
            }
        }else{
            Toast.makeText(this, "Start of daylight (Minutes) is missing", Toast.LENGTH_SHORT).show()
            error = 4
        }

        if (binding.nightStartTimeHour.text.isNotBlank()){
            nightStartTimeHour = binding.nightStartTimeHour.text.toString().toInt()
            if ((nightStartTimeHour < 0) || (nightStartTimeHour > 23)){
                Toast.makeText(this, "End of daylight (Hours) must be between 0 - 23 hs", Toast.LENGTH_SHORT).show()
                error = 4
            }
        }else{
            Toast.makeText(this, "End of daylight (Hours) is missing", Toast.LENGTH_SHORT).show()
            error = 4
        }

        if (binding.nightStartTimeMin.text.isNotBlank()){
            nightStartTimeMin = binding.nightStartTimeMin.text.toString().toInt()
            if ((nightStartTimeMin < 0) || (nightStartTimeMin > 59)){
                Toast.makeText(this, "End of daylight (Minutes) must be between 0 - 59 min", Toast.LENGTH_SHORT).show()
                error = 4
            }
        }else{
            Toast.makeText(this, "End of daylight (Minutes) is missing", Toast.LENGTH_SHORT).show()
            error = 4
        }

        //Ventilation

        if (binding.ventTime.text.isNotBlank()){
            ventTime = binding.ventTime.text.toString().toInt()
        }
        if (binding.ventIntervals.text.isNotBlank()){
            ventIntervals = binding.ventIntervals.text.toString().toInt()
        }

        //Irrigation

        if (binding.monday.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
            || binding.tuesday.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
            || binding.wednesday.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
            || binding.thursday.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
            || binding.friday.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
            || binding.saturday.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
            || binding.sunday.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))){

            if (binding.irriStartTimeHour.text.isNotBlank()){
                irriStartTimeHour = binding.irriStartTimeHour.text.toString().toInt()
                if ((irriStartTimeHour < 0) || (irriStartTimeHour > 23)){
                    Toast.makeText(this, "Irrigation start time (Hours) must be between 0 - 24 Hours", Toast.LENGTH_SHORT).show()
                    error = 5
                }
            }else{
                Toast.makeText(this, "Irrigation start time (Hours) is missing", Toast.LENGTH_SHORT).show()
                error = 5
            }
            if (binding.irriStartTimeMin.text.isNotBlank()){
                irriStartTimeMin = binding.irriStartTimeMin.text.toString().toInt()
                if ((irriStartTimeMin < 0) || (irriStartTimeMin > 59)){
                    Toast.makeText(this, "Irrigation start time (Minutes) must be between 0 - 59 Minutes", Toast.LENGTH_SHORT).show()
                    error = 5
                }
            }else{
                Toast.makeText(this, "Irrigation start time (Minutes) is missing", Toast.LENGTH_SHORT).show()
                error = 5
            }
            if (binding.irriTime.text.isNotBlank()){
                irriTime = binding.irriTime.text.toString().toInt()
                if ((irriTime < 1) || (irriTime > 600)){
                    Toast.makeText(this, "Irrigation time must be between 1 - 600 Seconds", Toast.LENGTH_SHORT).show()
                    error = 5
                }
            }else{
                Toast.makeText(this, "Irrigation time is missing", Toast.LENGTH_SHORT).show()
                error = 5
            }
        }

        return error
    }

    private fun openCards(){

        var relativeLayout1 = findViewById<RelativeLayout>(R.id.expandable_layout_1)
        var relativeLayout2 = findViewById<RelativeLayout>(R.id.expandable_layout_2)
        var relativeLayout3 = findViewById<RelativeLayout>(R.id.expandable_layout_3)
        var relativeLayout4 = findViewById<RelativeLayout>(R.id.expandable_layout_4)
        var relativeLayout5 = findViewById<RelativeLayout>(R.id.expandable_layout_5)
        var relativeLayout6 = findViewById<RelativeLayout>(R.id.expandable_layout_6)


        binding.linearLayout1.setOnClickListener {
            if (relativeLayout1.visibility == GONE){
                relativeLayout1.visibility = VISIBLE
                binding.arrow1.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
                binding.line1T.setBackgroundColor(Color.parseColor("#86F4F3E4"))
                binding.line1M.setBackgroundColor(Color.parseColor("#86F4F3E4"))
                binding.line1B.setBackgroundColor(Color.parseColor("#86F4F3E4"))
                binding.line1T.layoutParams.height = 20
                binding.line1M.layoutParams.height = 20
                binding.line1B.layoutParams.height = 20
                binding.linearLayout1.setBackgroundColor(Color.parseColor("#86FEFAE0"))

            }else{
                relativeLayout1.visibility = GONE
                binding.arrow1.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                binding.linearLayout1.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binding.line1T.setBackgroundColor(Color.parseColor("#DCDCDC"))
                binding.line1M.setBackgroundColor(Color.parseColor("#DCDCDC"))
                binding.line1B.setBackgroundColor(Color.parseColor("#DCDCDC"))
                binding.line1T.layoutParams.height = 1
                binding.line1M.layoutParams.height = 1
                binding.line1B.layoutParams.height = 1
                relativeLayout1.hideKeyboard()
            }
        }

        binding.linearLayout2.setOnClickListener {
            if (relativeLayout2.visibility == GONE){
                relativeLayout2.visibility = VISIBLE
                binding.arrow2.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
                binding.line2T.setBackgroundColor(Color.parseColor("#86F4F3E4"))
                binding.line2M.setBackgroundColor(Color.parseColor("#86F4F3E4"))
                binding.line2B.setBackgroundColor(Color.parseColor("#86F4F3E4"))
                binding.line2T.layoutParams.height = 20
                binding.line2M.layoutParams.height = 20
                binding.line2B.layoutParams.height = 20
                binding.linearLayout2.setBackgroundColor(Color.parseColor("#86FEFAE0"))

            }else{
                relativeLayout2.visibility = GONE
                binding.arrow2.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                binding.linearLayout2.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binding.line2T.setBackgroundColor(Color.parseColor("#DCDCDC"))
                binding.line2M.setBackgroundColor(Color.parseColor("#DCDCDC"))
                binding.line2B.setBackgroundColor(Color.parseColor("#DCDCDC"))
                binding.line2T.layoutParams.height = 1
                binding.line2M.layoutParams.height = 1
                binding.line2B.layoutParams.height = 1
                relativeLayout2.hideKeyboard()
            }
        }

        binding.linearLayout3.setOnClickListener {
            if (relativeLayout3.visibility == GONE){
                relativeLayout3.visibility = VISIBLE
                binding.arrow3.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
                binding.line3T.setBackgroundColor(Color.parseColor("#86F4F3E4"))
                binding.line3M.setBackgroundColor(Color.parseColor("#86F4F3E4"))
                binding.line3B.setBackgroundColor(Color.parseColor("#86F4F3E4"))
                binding.line3T.layoutParams.height = 20
                binding.line3M.layoutParams.height = 20
                binding.line3B.layoutParams.height = 20
                binding.linearLayout3.setBackgroundColor(Color.parseColor("#86FEFAE0"))


            }else{
                relativeLayout3.visibility = GONE
                binding.arrow3.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                binding.linearLayout3.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binding.line3T.setBackgroundColor(Color.parseColor("#DCDCDC"))
                binding.line3M.setBackgroundColor(Color.parseColor("#DCDCDC"))
                binding.line3B.setBackgroundColor(Color.parseColor("#DCDCDC"))
                binding.line3T.layoutParams.height = 1
                binding.line3M.layoutParams.height = 1
                binding.line3B.layoutParams.height = 1
                relativeLayout3.hideKeyboard()
            }
        }

        binding.linearLayout4.setOnClickListener {
            if (relativeLayout4.visibility == GONE){
                relativeLayout4.visibility = VISIBLE
                binding.arrow4.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
                binding.line4T.setBackgroundColor(Color.parseColor("#86F4F3E4"))
                binding.line4M.setBackgroundColor(Color.parseColor("#86F4F3E4"))
                binding.line4B.setBackgroundColor(Color.parseColor("#86F4F3E4"))
                binding.line4T.layoutParams.height = 20
                binding.line4M.layoutParams.height = 20
                binding.line4B.layoutParams.height = 20
                binding.linearLayout4.setBackgroundColor(Color.parseColor("#86FEFAE0"))

            }else{
                relativeLayout4.visibility = GONE
                binding.arrow4.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                binding.linearLayout4.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binding.line4T.setBackgroundColor(Color.parseColor("#DCDCDC"))
                binding.line4M.setBackgroundColor(Color.parseColor("#DCDCDC"))
                binding.line4B.setBackgroundColor(Color.parseColor("#DCDCDC"))
                binding.line4T.layoutParams.height = 1
                binding.line4M.layoutParams.height = 1
                binding.line4B.layoutParams.height = 1
                relativeLayout4.hideKeyboard()
            }
        }

        binding.linearLayout5.setOnClickListener {
            if (relativeLayout5.visibility == GONE){
                relativeLayout5.visibility = VISIBLE
                binding.arrow5.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
                binding.line5T.setBackgroundColor(Color.parseColor("#86F4F3E4"))
                binding.line5M.setBackgroundColor(Color.parseColor("#86F4F3E4"))
                binding.line5B.setBackgroundColor(Color.parseColor("#86F4F3E4"))
                binding.line5T.layoutParams.height = 20
                binding.line5M.layoutParams.height = 20
                binding.line5B.layoutParams.height = 20
                binding.linearLayout5.setBackgroundColor(Color.parseColor("#86FEFAE0"))

            }else{
                relativeLayout5.visibility = GONE
                binding.arrow5.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                binding.linearLayout5.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binding.line5T.setBackgroundColor(Color.parseColor("#DCDCDC"))
                binding.line5M.setBackgroundColor(Color.parseColor("#DCDCDC"))
                binding.line5B.setBackgroundColor(Color.parseColor("#DCDCDC"))
                binding.line5T.layoutParams.height = 1
                binding.line5M.layoutParams.height = 1
                binding.line5B.layoutParams.height = 1
                relativeLayout5.hideKeyboard()
            }
        }

        binding.linearLayout6.setOnClickListener {
            if (relativeLayout6.visibility == GONE){
                relativeLayout6.visibility = VISIBLE
                binding.arrow6.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
                binding.line6T.setBackgroundColor(Color.parseColor("#86F4F3E4"))
                binding.line6M.setBackgroundColor(Color.parseColor("#86F4F3E4"))
                binding.line6B.setBackgroundColor(Color.parseColor("#86F4F3E4"))
                binding.line6T.layoutParams.height = 20
                binding.line6M.layoutParams.height = 20
                binding.line6B.layoutParams.height = 20
                binding.linearLayout6.setBackgroundColor(Color.parseColor("#86FEFAE0"))

            }else{
                relativeLayout6.visibility = GONE
                binding.arrow6.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                binding.linearLayout6.setBackgroundColor(Color.parseColor("#FFFFFF"))
                binding.line6T.setBackgroundColor(Color.parseColor("#DCDCDC"))
                binding.line6M.setBackgroundColor(Color.parseColor("#DCDCDC"))
                binding.line6B.setBackgroundColor(Color.parseColor("#DCDCDC"))
                binding.line6T.layoutParams.height = 1
                binding.line6M.layoutParams.height = 1
                binding.line6B.layoutParams.height = 1
                relativeLayout6.hideKeyboard()
            }
        }
    }

    private fun dayOfWeekSelect(){

        val monday = binding.monday
        val tuesday = binding.tuesday
        val wednesday = binding.wednesday
        val thursday = binding.thursday
        val friday = binding.friday
        val saturday = binding.saturday
        val sunday = binding.sunday

        monday.setOnClickListener {
            if (monday.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#DCDCDC")) ){

                monday.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
            }else{
                monday.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#DCDCDC"))
            }
        }

        tuesday.setOnClickListener {
            if (tuesday.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#DCDCDC")) ){

                tuesday.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
            }else{
                tuesday.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#DCDCDC"))
            }
        }

        wednesday.setOnClickListener {
            if (wednesday.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#DCDCDC")) ){

                wednesday.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
            }else{
                wednesday.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#DCDCDC"))
            }
        }

        thursday.setOnClickListener {
            if (thursday.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#DCDCDC")) ){

                thursday.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
            }else{
                thursday.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#DCDCDC"))
            }
        }

        friday.setOnClickListener {
            if (friday.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#DCDCDC")) ){

                friday.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
            }else{
                friday.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#DCDCDC"))
            }
        }

        saturday.setOnClickListener {
            if (saturday.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#DCDCDC")) ){

                saturday.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
            }else{
                saturday.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#DCDCDC"))
            }
        }

        sunday.setOnClickListener {
            if (sunday.backgroundTintList == ColorStateList.valueOf(Color.parseColor("#DCDCDC")) ){

                sunday.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
            }else{
                sunday.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#DCDCDC"))
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

}
package com.example.indoor

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlin.coroutines.coroutineContext

class PlantsInfoAdapter(val c:Fragment,val plantsList:ArrayList<UserActualParameters>):RecyclerView.Adapter<PlantsInfoAdapter.PlantsViewHolder>() {

    private lateinit var auth: FirebaseAuth

    inner class PlantsViewHolder(val v:View):RecyclerView.ViewHolder(v){
        var plantName:TextView
        var description:TextView
        var mMenus:ImageView
        var playBnt:ImageView



        init {
            auth = Firebase.auth
            plantName = v.findViewById<TextView>(R.id.PlantName)
            description = v.findViewById<TextView>(R.id.description)
            mMenus = v.findViewById(R.id.mMenus)
            playBnt = v.findViewById(R.id.playBtn)
            mMenus.setOnClickListener { popupMenus(it) }
            playBnt.setOnClickListener{ setActualParameters(v) }
            itemView.setOnClickListener { popParameters(v) }
        }

        private fun setActualParameters(v:View){
            val position = plantsList[adapterPosition]
            val currentUserId = auth.currentUser!!.uid
            Toast.makeText(c.context, "New Parameters set", Toast.LENGTH_SHORT).show()
            //PlantsInfoDao().plantsCollection.document(currentUserId).collection("Parameters").document(position.plantName).get().addOnSuccessListener { result ->
            //}
        }

        private fun popParameters(v:View) {
            val position = plantsList[adapterPosition]
            val currentUserId = auth.currentUser!!.uid
            val v = LayoutInflater.from(c.context).inflate(R.layout.dialog_show_parameters,null)
            var substrate = v.findViewById<TextView>(R.id.substrate)
            var fertilizers = v.findViewById<TextView>(R.id.fertilizers)
            var pesticides = v.findViewById<TextView>(R.id.pesticides)
            var harvestDays = v.findViewById<TextView>(R.id.harvestDays)
            var harvestMonths = v.findViewById<TextView>(R.id.harvestMonths)
            var harvestYears = v.findViewById<TextView>(R.id.harvestYears)
            var tempDayMin = v.findViewById<TextView>(R.id.tempDayMin)
            var tempDayMax = v.findViewById<TextView>(R.id.tempDayMax)
            var tempNightMin = v.findViewById<TextView>(R.id.tempNightMin)
            var tempNightMax = v.findViewById<TextView>(R.id.tempNightMax)
            var humDayMin = v.findViewById<TextView>(R.id.humDayMin)
            var humDayMax = v.findViewById<TextView>(R.id.humDayMax)
            var humNightMin = v.findViewById<TextView>(R.id.humNightMin)
            var humNightMax = v.findViewById<TextView>(R.id.humNightMax)
            var lightsOnHour = v.findViewById<TextView>(R.id.lightsOnHour)
            var lightsOnMin = v.findViewById<TextView>(R.id.lightsOnMin)
            var lightsOffHour = v.findViewById<TextView>(R.id.lightsOffHour)
            var lightsOffMin = v.findViewById<TextView>(R.id.lightsOffMin)
            var ventTimer = v.findViewById<TextView>(R.id.ventTimer)
            var ventInter = v.findViewById<TextView>(R.id.ventInter)
            var monday = v.findViewById<CardView>(R.id.monday)
            var tuesday = v.findViewById<CardView>(R.id.tuesday)
            var wednesday = v.findViewById<CardView>(R.id.wednesday)
            var thursday = v.findViewById<CardView>(R.id.thursday)
            var friday = v.findViewById<CardView>(R.id.friday)
            var saturday = v.findViewById<CardView>(R.id.saturday)
            var sunday = v.findViewById<CardView>(R.id.sunday)
            var irriStartsHour = v.findViewById<TextView>(R.id.irriStartsHour)
            var irriStartsMin = v.findViewById<TextView>(R.id.irriStartsMin)
            var irriTimer = v.findViewById<TextView>(R.id.irriTimer)


            PlantsInfoDao().plantsCollection.document(currentUserId).collection("Parameters").document(position.plantName).get().addOnSuccessListener { result ->


                if (result.data?.getValue("substrate").toString() == ""){
                    substrate.text = "-"
                }else{
                    substrate.text = result.data?.getValue("substrate").toString()
                }
                if (result.data?.getValue("fertilizers").toString() == ""){
                    fertilizers.text = "-"
                }else{
                    fertilizers.text = result.data?.getValue("fertilizers").toString()
                }
                if (result.data?.getValue("pesticides").toString() == ""){
                    pesticides.text = "-"
                }else{
                    pesticides.text = result.data?.getValue("pesticides").toString()
                }
                if (result.data?.getValue("harvestTimeDays").toString() == "null"){
                    harvestDays.text = "-"
                }else{
                    harvestDays.text = result.data?.getValue("harvestTimeDays").toString()
                }
                if (result.data?.getValue("harvestTimeMonths").toString() == "null"){
                    harvestMonths.text = "-"
                }else{
                    harvestMonths.text = result.data?.getValue("harvestTimeMonths").toString()
                }
                if (result.data?.getValue("harvestTimeYears").toString() == "null"){
                    harvestYears.text = "-"
                }else{
                    harvestYears.text = result.data?.getValue("harvestTimeYears").toString()
                }

                tempDayMin.text = result.data?.getValue("minTempDay").toString()
                tempDayMax.text = result.data?.getValue("maxTempDay").toString()
                tempNightMin.text = result.data?.getValue("minTempNight").toString()
                tempNightMax.text = result.data?.getValue("maxTempNight").toString()
                humDayMin.text = result.data?.getValue("minHumDay").toString()
                humDayMax.text = result.data?.getValue("maxHumDay").toString()
                humNightMin.text = result.data?.getValue("minHumNight").toString()
                humNightMax.text = result.data?.getValue("maxHumNight").toString()
                lightsOnHour.text = result.data?.getValue("dayStartTimeHour").toString()
                lightsOnMin.text = result.data?.getValue("dayStartTimeMin").toString()
                lightsOffHour.text = result.data?.getValue("nightStartTimeHour").toString()
                lightsOffMin.text = result.data?.getValue("nightStartTimeMin").toString()

                if (result.data?.getValue("ventTime").toString() == "null"){
                    ventTimer.text = "-"
                }else{
                    ventTimer.text = result.data?.getValue("ventTime").toString()
                }
                if (result.data?.getValue("ventIntervals").toString() == "null"){
                    ventInter.text = "-"
                }else{
                    ventInter.text = result.data?.getValue("ventIntervals").toString()
                }
                if (result.data?.getValue("irriStartTimeHour").toString() == "null"){
                    irriStartsHour.text = "-"
                }else{
                    irriStartsHour.text = result.data?.getValue("irriStartTimeHour").toString()
                }
                if (result.data?.getValue("irriStartTimeMin").toString() == "null"){
                    irriStartsMin.text = "-"
                }else{
                    irriStartsMin.text = result.data?.getValue("irriStartTimeMin").toString()
                }
                if (result.data?.getValue("irriTime").toString() == "null"){
                    irriTimer.text = "-"
                }else{
                    irriTimer.text = result.data?.getValue("irriTime").toString()
                }

                if (result.data?.getValue("dayMo").toString().toInt() == 1){
                    monday.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
                }
                if (result.data?.getValue("dayTu").toString().toInt() == 1){
                    tuesday.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
                }
                if (result.data?.getValue("dayWe").toString().toInt() == 1){
                    wednesday.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
                }
                if (result.data?.getValue("dayTh").toString().toInt() == 1){
                    thursday.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
                }
                if (result.data?.getValue("dayFr").toString().toInt() == 1){
                    friday.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
                }
                if (result.data?.getValue("daySa").toString().toInt() == 1){
                    saturday.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
                }
                if (result.data?.getValue("daySu").toString().toInt() == 1){
                    sunday.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
                }

            }

            AlertDialog.Builder(c.context)
                .setView(v)
                .setPositiveButton("Ok"){
                        dialog,_->
                    dialog.dismiss()
                }
                .create()
                .show()
        }

        private fun popupMenus(v:View) {
            val position = plantsList[adapterPosition]
            val popupMenus = PopupMenu(c.context,v)
            val currentUserId = auth.currentUser!!.uid
            popupMenus.inflate(R.menu.show_menu)
            popupMenus.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.editText->{
                        val v = LayoutInflater.from(c.context).inflate(R.layout.dialog_change_parameters,null)
                        dayOfWeekSelect(v)
                        var plantName = v.findViewById<EditText>(R.id.plantName)
                        var description = v.findViewById<EditText>(R.id.description)
                        var substrate = v.findViewById<EditText>(R.id.substrate)
                        var fertilizers = v.findViewById<EditText>(R.id.fertilizers)
                        var pesticides = v.findViewById<EditText>(R.id.pesticides)
                        var harvestDays = v.findViewById<EditText>(R.id.harvestTimeDays)
                        var harvestMonths = v.findViewById<EditText>(R.id.harvestTimeMonths)
                        var harvestYears = v.findViewById<EditText>(R.id.harvestTimeYears)
                        var tempDayMin = v.findViewById<EditText>(R.id.MinTempDay)
                        var tempDayMax = v.findViewById<EditText>(R.id.MaxTempDay)
                        var tempNightMin = v.findViewById<EditText>(R.id.MinTempNight)
                        var tempNightMax = v.findViewById<EditText>(R.id.MaxTempNight)
                        var humDayMin = v.findViewById<EditText>(R.id.MinHumDay)
                        var humDayMax = v.findViewById<EditText>(R.id.MaxHumDay)
                        var humNightMin = v.findViewById<EditText>(R.id.MinHumNight)
                        var humNightMax = v.findViewById<EditText>(R.id.MaxHumNight)
                        var lightsOnHour = v.findViewById<EditText>(R.id.dayStartTimeHour)
                        var lightsOnMin = v.findViewById<EditText>(R.id.dayStartTimeMin)
                        var lightsOffHour = v.findViewById<EditText>(R.id.nightStartTimeHour)
                        var lightsOffMin = v.findViewById<EditText>(R.id.nightStartTimeMin)
                        var ventTimer = v.findViewById<EditText>(R.id.ventTime)
                        var ventInter = v.findViewById<EditText>(R.id.ventIntervals)
                        var monday = v.findViewById<CardView>(R.id.monday)
                        var tuesday = v.findViewById<CardView>(R.id.tuesday)
                        var wednesday = v.findViewById<CardView>(R.id.wednesday)
                        var thursday = v.findViewById<CardView>(R.id.thursday)
                        var friday = v.findViewById<CardView>(R.id.friday)
                        var saturday = v.findViewById<CardView>(R.id.saturday)
                        var sunday = v.findViewById<CardView>(R.id.sunday)
                        var irriStartsHour = v.findViewById<EditText>(R.id.irriStartTimeHour)
                        var irriStartsMin = v.findViewById<EditText>(R.id.irriStartTimeMin)
                        var irriTimer = v.findViewById<EditText>(R.id.irriTime)

                        //Cargo los datos a modificar en los editTexts
                        PlantsInfoDao().plantsCollection.document(currentUserId).collection("Parameters").document(position.plantName).get().addOnSuccessListener { result ->

                            plantName.setText(result.data?.getValue("plantName").toString())
                            description.setText(result.data?.getValue("description").toString())
                            if (result.data?.getValue("substrate").toString() == ""){
                                substrate.setText("")
                            }else{
                                substrate.setText(result.data?.getValue("substrate").toString())
                            }
                            if (result.data?.getValue("fertilizers").toString() == ""){
                                fertilizers.setText("")
                            }else{
                                fertilizers.setText(result.data?.getValue("fertilizers").toString())
                            }
                            if (result.data?.getValue("pesticides").toString() == ""){
                                pesticides.setText("")
                            }else{
                                pesticides.setText(result.data?.getValue("pesticides").toString())
                            }
                            if (result.data?.getValue("harvestTimeDays").toString() == "null"){
                                harvestDays.setText("")
                            }else{
                                harvestDays.setText(result.data?.getValue("harvestTimeDays").toString())
                            }
                            if (result.data?.getValue("harvestTimeMonths").toString() == "null"){
                                harvestMonths.setText("")
                            }else{
                                harvestMonths.setText(result.data?.getValue("harvestTimeMonths").toString())
                            }
                            if (result.data?.getValue("harvestTimeYears").toString() == "null"){
                                harvestYears.setText("")
                            }else{
                                harvestYears.setText(result.data?.getValue("harvestTimeYears").toString())
                            }

                            tempDayMin.setText(result.data?.getValue("minTempDay").toString())
                            tempDayMax.setText(result.data?.getValue("maxTempDay").toString())
                            tempNightMin.setText(result.data?.getValue("minTempNight").toString())
                            tempNightMax.setText(result.data?.getValue("maxTempNight").toString())
                            humDayMin.setText(result.data?.getValue("minHumDay").toString())
                            humDayMax.setText(result.data?.getValue("maxHumDay").toString())
                            humNightMin.setText(result.data?.getValue("minHumNight").toString())
                            humNightMax.setText(result.data?.getValue("maxHumNight").toString())
                            lightsOnHour.setText(result.data?.getValue("dayStartTimeHour").toString())
                            lightsOnMin.setText(result.data?.getValue("dayStartTimeMin").toString())
                            lightsOffHour.setText(result.data?.getValue("nightStartTimeHour").toString())
                            lightsOffMin.setText(result.data?.getValue("nightStartTimeMin").toString())

                            if (result.data?.getValue("ventTime").toString() == "null"){
                                ventTimer.setText("")
                            }else{
                                ventTimer.setText(result.data?.getValue("ventTime").toString())
                            }
                            if (result.data?.getValue("ventIntervals").toString() == "null"){
                                ventInter.setText("")
                            }else{
                                ventInter.setText(result.data?.getValue("ventIntervals").toString())
                            }
                            if (result.data?.getValue("irriStartTimeHour").toString() == "null"){
                                irriStartsHour.setText("")
                            }else{
                                irriStartsHour.setText(result.data?.getValue("irriStartTimeHour").toString())
                            }
                            if (result.data?.getValue("irriStartTimeMin").toString() == "null"){
                                irriStartsMin.setText("")
                            }else{
                                irriStartsMin.setText(result.data?.getValue("irriStartTimeMin").toString())
                            }
                            if (result.data?.getValue("irriTime").toString() == "null"){
                                irriTimer.setText("")
                            }else{
                                irriTimer.setText(result.data?.getValue("irriTime").toString())
                            }

                            if (result.data?.getValue("dayMo").toString().toInt() == 1){
                                monday.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
                            }
                            if (result.data?.getValue("dayTu").toString().toInt() == 1){
                                tuesday.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
                            }
                            if (result.data?.getValue("dayWe").toString().toInt() == 1){
                                wednesday.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
                            }
                            if (result.data?.getValue("dayTh").toString().toInt() == 1){
                                thursday.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
                            }
                            if (result.data?.getValue("dayFr").toString().toInt() == 1){
                                friday.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
                            }
                            if (result.data?.getValue("daySa").toString().toInt() == 1){
                                saturday.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
                            }
                            if (result.data?.getValue("daySu").toString().toInt() == 1){
                                sunday.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
                            }

                        }


                        AlertDialog.Builder(c.context)
                            .setView(v)
                            .setPositiveButton("Ok"){
                                    dialog,_->
                                if (checkParameters(v) == 0){
                                    PlantsInfoDao().plantsCollection.document(currentUserId).collection("Parameters").document(position.plantName).delete()
                                    notifyDataSetChanged()
                                    addParameters(v)
                                    notifyDataSetChanged()
                                    dialog.dismiss()
                                }

                            }
                            .setNegativeButton("Cancel"){
                                    dialog,_->
                                dialog.dismiss()

                            }
                            .create()
                            .show()

                        true
                    }
                    R.id.delete->{
                        /**set delete*/
                        val currentUserId = auth.currentUser!!.uid
                        AlertDialog.Builder(c.context)
                            .setTitle("Delete")
                            .setIcon(R.drawable.ic_warning)
                            .setMessage("Are you sure you want to delete this Information?")
                            .setPositiveButton("Yes"){
                                    dialog,_->
                                plantsList.removeAt(adapterPosition)
                                PlantsInfoDao().plantsCollection.document(currentUserId).collection("Parameters").document(position.plantName).delete()
                                notifyDataSetChanged()
                                Toast.makeText(c.context,"Information Deleted",Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            }
                            .setNegativeButton("No"){
                                    dialog,_->
                                dialog.dismiss()
                            }
                            .create()
                            .show()

                        true
                    }
                    else-> true
                }

            }
            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java)
                .invoke(menu,true)
        }


        fun View.hideKeyboard() {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(windowToken, 0)
        }

        private fun addParameters(v: View) {

            var plantsInfoDao = PlantsInfoDao()

            val plantName = v.findViewById<EditText>(R.id.plantName).text.toString()
            val substrate = v.findViewById<EditText>(R.id.substrate).text.toString()
            val fertilizers = v.findViewById<EditText>(R.id.fertilizers).text.toString()
            val pesticides = v.findViewById<EditText>(R.id.pesticides).text.toString()
            val description = v.findViewById<EditText>(R.id.description).text.toString()
            var harvestTimeDays: Int? = null
            var harvestTimeMonths: Int? = null
            var harvestTimeYears: Int? = null
            if (v.findViewById<EditText>(R.id.harvestTimeDays).text.isNotBlank()){
                harvestTimeDays = v.findViewById<EditText>(R.id.harvestTimeDays).text.toString().toInt()
            }
            if (v.findViewById<EditText>(R.id.harvestTimeMonths).text.isNotBlank()){
                harvestTimeMonths = v.findViewById<EditText>(R.id.harvestTimeMonths).text.toString().toInt()
            }
            if (v.findViewById<EditText>(R.id.harvestTimeYears).text.isNotBlank()){
                harvestTimeYears = v.findViewById<EditText>(R.id.harvestTimeYears).text.toString().toInt()
            }

            val minTempDay = v.findViewById<EditText>(R.id.MinTempDay).text.toString().toDouble()
            val maxTempDay = v.findViewById<EditText>(R.id.MaxTempDay).text.toString().toDouble()
            val minTempNight = v.findViewById<EditText>(R.id.MinTempNight).text.toString().toDouble()
            val maxTempNight = v.findViewById<EditText>(R.id.MaxTempNight).text.toString().toDouble()

            val minHumDay = v.findViewById<EditText>(R.id.MinHumDay).text.toString().toDouble()
            val maxHumDay = v.findViewById<EditText>(R.id.MaxTempDay).text.toString().toDouble()
            val minHumNight = v.findViewById<EditText>(R.id.MinTempNight).text.toString().toDouble()
            val maxHumNight = v.findViewById<EditText>(R.id.MaxTempNight).text.toString().toDouble()

            val dayStartTimeHour = v.findViewById<EditText>(R.id.dayStartTimeHour).text.toString().toInt()
            val dayStartTimeMin = v.findViewById<EditText>(R.id.dayStartTimeMin).text.toString().toInt()
            val nightStartTimeHour = v.findViewById<EditText>(R.id.nightStartTimeHour).text.toString().toInt()
            val nightStartTimeMin = v.findViewById<EditText>(R.id.nightStartTimeMin).text.toString().toInt()

            var ventTime: Int? = null
            var ventIntervals: Int? = null
            if (v.findViewById<EditText>(R.id.ventTime).text.isNotBlank()){
                ventTime = v.findViewById<EditText>(R.id.ventTime).text.toString().toInt()
            }
            if (v.findViewById<EditText>(R.id.ventIntervals).text.isNotBlank()){
                ventIntervals = v.findViewById<EditText>(R.id.ventIntervals).text.toString().toInt()
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

            if (v.findViewById<CardView>(R.id.monday).backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
                || v.findViewById<CardView>(R.id.tuesday).backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
                || v.findViewById<CardView>(R.id.wednesday).backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
                || v.findViewById<CardView>(R.id.thursday).backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
                || v.findViewById<CardView>(R.id.friday).backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
                || v.findViewById<CardView>(R.id.saturday).backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
                || v.findViewById<CardView>(R.id.sunday).backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))){

                irriStartTimeHour = v.findViewById<EditText>(R.id.irriStartTimeHour).text.toString().toInt()
                irriStartTimeMin = v.findViewById<EditText>(R.id.irriStartTimeMin).text.toString().toInt()
                irriTime = v.findViewById<EditText>(R.id.irriTime).text.toString().toInt()

                if (v.findViewById<CardView>(R.id.monday).backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))){
                    dayMo = 1
                }
                if (v.findViewById<CardView>(R.id.tuesday).backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))){
                    dayTu = 1
                }
                if (v.findViewById<CardView>(R.id.wednesday).backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))){
                    dayWe = 1
                }
                if (v.findViewById<CardView>(R.id.thursday).backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))){
                    dayTh = 1
                }
                if (v.findViewById<CardView>(R.id.friday).backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))){
                    dayFr = 1
                }
                if (v.findViewById<CardView>(R.id.saturday).backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))){
                    daySa = 1
                }
                if (v.findViewById<CardView>(R.id.sunday).backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))){
                    daySu = 1
                }

            }

            plantsInfoDao.addPlantInfo(plantName, substrate, fertilizers, pesticides, description, harvestTimeDays, harvestTimeMonths, harvestTimeYears,
                minTempDay, maxTempDay, minTempNight, maxTempNight, minHumDay, maxHumDay, minHumNight, maxHumNight,
                dayStartTimeHour, dayStartTimeMin, nightStartTimeHour, nightStartTimeMin, ventTime, ventIntervals,
                irriStartTimeHour, irriStartTimeMin, irriTime, dayMo, dayTu, dayWe, dayTh, dayFr, daySa, daySu)

            Toast.makeText(c.context,"Plant parameters successfully added",Toast.LENGTH_SHORT).show()

        }

        private fun dayOfWeekSelect(v:View){

            val monday = v.findViewById<CardView>(R.id.monday)
            val tuesday = v.findViewById<CardView>(R.id.tuesday)
            val wednesday = v.findViewById<CardView>(R.id.wednesday)
            val thursday = v.findViewById<CardView>(R.id.thursday)
            val friday = v.findViewById<CardView>(R.id.friday)
            val saturday = v.findViewById<CardView>(R.id.saturday)
            val sunday = v.findViewById<CardView>(R.id.sunday)

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

        private fun checkParameters(v:View): Int {

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
            plantName = v.findViewById<EditText>(R.id.plantName).text.toString()
            if (v.findViewById<EditText>(R.id.plantName).text.isBlank()){
                Toast.makeText(c.context, "Plant Name is missing, please enter one", Toast.LENGTH_SHORT).show()
                error = 1
            }
            if (v.findViewById<EditText>(R.id.harvestTimeDays).text.isNotBlank()){
                harvestTimeDays = v.findViewById<EditText>(R.id.harvestTimeDays).text.toString().toInt()
                if ((harvestTimeDays < 0) || (harvestTimeDays > 31)){
                    Toast.makeText(c.context, "Days in \"Harvest Time\" must be between 0 - 31 days", Toast.LENGTH_SHORT).show()
                    error = 1
                }
            }
            if (v.findViewById<EditText>(R.id.harvestTimeMonths).text.isNotBlank()){
                harvestTimeMonths = v.findViewById<EditText>(R.id.harvestTimeMonths).text.toString().toInt()
                if ((harvestTimeMonths < 0) || (harvestTimeMonths > 12)){
                    Toast.makeText(c.context, "Months in \"Harvest Time\" must be between 0 - 12 months", Toast.LENGTH_SHORT).show()
                    error = 1
                }
            }
            if (v.findViewById<EditText>(R.id.harvestTimeYears).text.isNotBlank()){
                harvestTimeYears = v.findViewById<EditText>(R.id.harvestTimeYears).text.toString().toInt()
                if ((harvestTimeYears < 0)){
                    Toast.makeText(c.context, "Years in \"Harvest Time\" must be positive numbers", Toast.LENGTH_SHORT).show()
                    error = 1
                }
            }
            substrate = v.findViewById<EditText>(R.id.substrate).text.toString()
            fertilizers = v.findViewById<EditText>(R.id.fertilizers).text.toString()
            pesticides = v.findViewById<EditText>(R.id.pesticides).text.toString()
            description = v.findViewById<EditText>(R.id.description).text.toString()

            //Temperature

            //Comprobaciones Empty/NotEmpty y rangos de temperatura
            if (v.findViewById<EditText>(R.id.MinTempDay).text.isNotBlank()){
                minTempDay = v.findViewById<EditText>(R.id.MinTempDay).text.toString().toDouble()
                if ((minTempDay < 0) || (minTempDay > 40)){
                    Toast.makeText(c.context, "Minimum temperature (Day) must be between 0 - 40 °C", Toast.LENGTH_SHORT).show()
                    error = 2
                }
            }else{
                Toast.makeText(c.context, "Minimum temperature (Day) is missing", Toast.LENGTH_SHORT).show()
                error = 2
            }

            if (v.findViewById<EditText>(R.id.MaxTempDay).text.isNotBlank()){
                maxTempDay = v.findViewById<EditText>(R.id.MaxTempDay).text.toString().toDouble()
                if ((maxTempDay < 0) || (maxTempDay > 40)){
                    Toast.makeText(c.context, "Maximum temperature (Day) must be between 0 - 40 °C", Toast.LENGTH_SHORT).show()
                    error = 2
                }
            }else{
                Toast.makeText(c.context, "Maximum temperature (Day) is missing", Toast.LENGTH_SHORT).show()
                error = 2
            }

            if (v.findViewById<EditText>(R.id.MinTempNight).text.isNotBlank()){
                minTempNight = v.findViewById<EditText>(R.id.MinTempNight).text.toString().toDouble()
                if ((minTempNight < 0) || (minTempNight > 40)){
                    Toast.makeText(c.context, "Minimum temperature (Night) must be between 0 - 40 °C", Toast.LENGTH_SHORT).show()
                    error = 2
                }
            }else{
                Toast.makeText(c.context, "Minimum temperature (Night) is missing", Toast.LENGTH_SHORT).show()
                error = 2
            }
            if (v.findViewById<EditText>(R.id.MaxTempNight).text.isNotBlank()){
                maxTempNight = v.findViewById<EditText>(R.id.MaxTempNight).text.toString().toDouble()
                if ((maxTempNight < 0) || (maxTempNight > 40)){
                    Toast.makeText(c.context, "Maximum temperature (Night) must be between 0 - 40 °C", Toast.LENGTH_SHORT).show()
                    error = 2
                }
            }else{
                Toast.makeText(c.context, "Maximum temperature (Night) is missing", Toast.LENGTH_SHORT).show()
                error = 2
            }

            //Comprobaciones para que el menor valor no supere al mayor valor de temperatura
            //Y que tengan un rango de 3 Centigrados de diferencia.

            if (error != 2){
                var total: Double
                minTempDay = v.findViewById<EditText>(R.id.MinTempDay).text.toString().toDouble()
                maxTempDay = v.findViewById<EditText>(R.id.MaxTempDay).text.toString().toDouble()
                minTempNight = v.findViewById<EditText>(R.id.MinTempNight).text.toString().toDouble()
                maxTempNight = v.findViewById<EditText>(R.id.MaxTempNight).text.toString().toDouble()

                total = maxTempDay - minTempDay
                if (total < 3){
                    Toast.makeText(c.context, "Maximum temperature (Day) must be at least 3°C higher than the Minimum temperature (Day)", Toast.LENGTH_SHORT).show()
                    error = 2
                }

                total = maxTempNight - minTempNight
                if (total < 3){
                    Toast.makeText(c.context, "Maximum temperature (Night) must be at least 3°C higher than the Minimum temperature (Night)", Toast.LENGTH_SHORT).show()
                    error = 2
                }

            }

            //Humidity

            //Comprobaciones Empty/NotEmpty y rangos de Humedad
            if (v.findViewById<EditText>(R.id.MinHumDay).text.isNotBlank()){
                minHumDay = v.findViewById<EditText>(R.id.MinHumDay).text.toString().toDouble()
                if ((minHumDay < 0) || (minHumDay > 100)){
                    Toast.makeText(c.context, "Minimum humidity (Day) must be between 0 - 100%", Toast.LENGTH_SHORT).show()
                    error = 3
                }
            }else{
                Toast.makeText(c.context, "Minimum humidity (Day) is missing", Toast.LENGTH_SHORT).show()
                error = 3
            }
            if (v.findViewById<EditText>(R.id.MaxHumDay).text.isNotBlank()){
                maxHumDay = v.findViewById<EditText>(R.id.MaxHumDay).text.toString().toDouble()
                if ((maxHumDay < 0) || (maxHumDay > 100)){
                    Toast.makeText(c.context, "Maximum humidity (Day) must be between 0 - 100%", Toast.LENGTH_SHORT).show()
                    error = 3
                }
            }else{
                Toast.makeText(c.context, "Maximum humidity (Day) is missing", Toast.LENGTH_SHORT).show()
                error = 3
            }

            if (v.findViewById<EditText>(R.id.MinHumNight).text.isNotBlank()){
                minHumNight = v.findViewById<EditText>(R.id.MinHumNight).text.toString().toDouble()
                if ((minHumNight < 0) || (minHumNight > 100)){
                    Toast.makeText(c.context, "Minimum humidity (Night) must be between 0 - 100%", Toast.LENGTH_SHORT).show()
                    error = 3
                }
            }else{
                Toast.makeText(c.context, "Minimum humidity (Night) is missing", Toast.LENGTH_SHORT).show()
                error = 3
            }
            if (v.findViewById<EditText>(R.id.MaxHumNight).text.isNotBlank()){
                maxHumNight = v.findViewById<EditText>(R.id.MaxHumNight).text.toString().toDouble()
                if ((maxHumNight < 0) || (maxHumNight > 100)){
                    Toast.makeText(c.context, "Maximum humidity (Night) must be between 0 - 100%", Toast.LENGTH_SHORT).show()
                    error = 3
                }
            }else{
                Toast.makeText(c.context, "Maximum humidity (Night) is missing", Toast.LENGTH_SHORT).show()
                error = 3
            }

            //Comprobaciones para que el menor valor no supere al mayor valor de Humedad
            //Y que tengan un rango de 5% de diferencia.

            if (error != 3){
                var total: Double
                minHumDay = v.findViewById<EditText>(R.id.MinTempDay).text.toString().toDouble()
                maxHumDay = v.findViewById<EditText>(R.id.MaxTempDay).text.toString().toDouble()
                minHumNight = v.findViewById<EditText>(R.id.MinTempNight).text.toString().toDouble()
                maxHumNight = v.findViewById<EditText>(R.id.MaxTempNight).text.toString().toDouble()

                total = maxHumDay - minHumDay
                if (total < 5){
                    Toast.makeText(c.context, "Maximum humidity (Day) must be at least 5% higher than the Minimum humidity (Day)", Toast.LENGTH_SHORT).show()
                    error = 3
                }

                total = maxHumNight - minHumNight
                if (total < 5){
                    Toast.makeText(c.context, "Maximum humidity (Night) must be at least 5% higher than the Minimum humidity (Night)", Toast.LENGTH_SHORT).show()
                    error = 3
                }

            }

            //Photoperiod

            if (v.findViewById<EditText>(R.id.dayStartTimeHour).text.isNotBlank()){
                dayStartTimeHour = v.findViewById<EditText>(R.id.dayStartTimeHour).text.toString().toInt()
                if ((dayStartTimeHour < 0) || (dayStartTimeHour > 23)){
                    Toast.makeText(c.context, "Start of daylight (Hours) must be between 0 - 23 hs", Toast.LENGTH_SHORT).show()
                    error = 4
                }
            }else{
                Toast.makeText(c.context, "Start of daylight (Hours) is missing", Toast.LENGTH_SHORT).show()
                error = 4
            }
            if (v.findViewById<EditText>(R.id.dayStartTimeMin).text.isNotBlank()){
                dayStartTimeMin = v.findViewById<EditText>(R.id.dayStartTimeMin).text.toString().toInt()
                if ((dayStartTimeMin < 0) || (dayStartTimeMin > 59)){
                    Toast.makeText(c.context, "Start of daylight (Minutes) must be between 0 - 59 min", Toast.LENGTH_SHORT).show()
                    error = 4
                }
            }else{
                Toast.makeText(c.context, "Start of daylight (Minutes) is missing", Toast.LENGTH_SHORT).show()
                error = 4
            }

            if (v.findViewById<EditText>(R.id.nightStartTimeHour).text.isNotBlank()){
                nightStartTimeHour = v.findViewById<EditText>(R.id.nightStartTimeHour).text.toString().toInt()
                if ((nightStartTimeHour < 0) || (nightStartTimeHour > 23)){
                    Toast.makeText(c.context, "End of daylight (Hours) must be between 0 - 23 hs", Toast.LENGTH_SHORT).show()
                    error = 4
                }
            }else{
                Toast.makeText(c.context, "End of daylight (Hours) is missing", Toast.LENGTH_SHORT).show()
                error = 4
            }

            if (v.findViewById<EditText>(R.id.nightStartTimeMin).text.isNotBlank()){
                nightStartTimeMin = v.findViewById<EditText>(R.id.nightStartTimeMin).text.toString().toInt()
                if ((nightStartTimeMin < 0) || (nightStartTimeMin > 59)){
                    Toast.makeText(c.context, "End of daylight (Minutes) must be between 0 - 59 min", Toast.LENGTH_SHORT).show()
                    error = 4
                }
            }else{
                Toast.makeText(c.context, "End of daylight (Minutes) is missing", Toast.LENGTH_SHORT).show()
                error = 4
            }

            //Ventilation

            if (v.findViewById<EditText>(R.id.ventTime).text.isNotBlank()){
                ventTime = v.findViewById<EditText>(R.id.ventTime).text.toString().toInt()
            }
            if (v.findViewById<EditText>(R.id.ventIntervals).text.isNotBlank()){
                ventIntervals = v.findViewById<EditText>(R.id.ventIntervals).text.toString().toInt()
            }

            //Irrigation

            if (v.findViewById<CardView>(R.id.monday).backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
                || v.findViewById<CardView>(R.id.tuesday).backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
                || v.findViewById<CardView>(R.id.wednesday).backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
                || v.findViewById<CardView>(R.id.thursday).backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
                || v.findViewById<CardView>(R.id.friday).backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
                || v.findViewById<CardView>(R.id.saturday).backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))
                || v.findViewById<CardView>(R.id.sunday).backgroundTintList == ColorStateList.valueOf(Color.parseColor("#8BE1B5"))){

                if (v.findViewById<EditText>(R.id.irriStartTimeHour).text.isNotBlank()){
                    irriStartTimeHour = v.findViewById<EditText>(R.id.irriStartTimeHour).text.toString().toInt()
                    if ((irriStartTimeHour < 0) || (irriStartTimeHour > 23)){
                        Toast.makeText(c.context, "Irrigation start time (Hours) must be between 0 - 24 Hours", Toast.LENGTH_SHORT).show()
                        error = 5
                    }
                }else{
                    Toast.makeText(c.context, "Irrigation start time (Hours) is missing", Toast.LENGTH_SHORT).show()
                    error = 5
                }
                if (v.findViewById<EditText>(R.id.irriStartTimeMin).text.isNotBlank()){
                    irriStartTimeMin = v.findViewById<EditText>(R.id.irriStartTimeMin).text.toString().toInt()
                    if ((irriStartTimeMin < 0) || (irriStartTimeMin > 59)){
                        Toast.makeText(c.context, "Irrigation start time (Minutes) must be between 0 - 59 Minutes", Toast.LENGTH_SHORT).show()
                        error = 5
                    }
                }else{
                    Toast.makeText(c.context, "Irrigation start time (Minutes) is missing", Toast.LENGTH_SHORT).show()
                    error = 5
                }
                if (v.findViewById<EditText>(R.id.irriTime).text.isNotBlank()){
                    irriTime = v.findViewById<EditText>(R.id.irriTime).text.toString().toInt()
                    if ((irriTime < 1) || (irriTime > 600)){
                        Toast.makeText(c.context, "Irrigation time must be between 1 - 600 Seconds", Toast.LENGTH_SHORT).show()
                        error = 5
                    }
                }else{
                    Toast.makeText(c.context, "Irrigation time is missing", Toast.LENGTH_SHORT).show()
                    error = 5
                }
            }

            return error
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v  = inflater.inflate(R.layout.card_plants_info,parent,false)
        return PlantsViewHolder(v)
    }

    override fun onBindViewHolder(holder: PlantsViewHolder, position: Int) {
        val newList = plantsList[position]
        holder.plantName.text = newList.plantName
        if (newList.description == ""){
            holder.description.text = "-"
        }else{
            holder.description.text = newList.description
        }


    }

    override fun getItemCount(): Int {
        return  plantsList.size
    }

}


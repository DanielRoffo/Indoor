package com.example.indoor

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.sql.Timestamp

class PlantsInfoDao {

    private val db = FirebaseFirestore.getInstance()
    val plantsCollection = db.collection("Users")

    private val auth = Firebase.auth

    fun addPlantInfo(plantName: String, substrate: String, fertilizers: String, pesticides: String, description: String,
                     harvestTimeDays: Int?, harvestTimeMonths: Int?, harvestTimeYears: Int?, minTempDay: Double,
                     maxTempDay: Double, minTempNight: Double, maxTempNight: Double, minHumDay: Double,
                     maxHumDay: Double, minHumNight: Double, maxHumNight: Double, dayStartTimeHour: Int,
                     dayStartTimeMin: Int, nightStartTimeHour: Int, nightStartTimeMin: Int, ventTime: Int?,
                     ventIntervals: Int?, irriStartTimeHour: Int?, irriStartTimeMin: Int?, irriTime: Int?,
                     dayMo: Int, dayTu: Int, dayWe: Int, dayTh: Int, dayFr: Int, daySa: Int, daySu: Int){

        val currentUserId = auth.currentUser!!.uid
        val plants =    UserActualParameters(plantName, substrate, fertilizers, pesticides, description ,
                                            harvestTimeDays, harvestTimeMonths, harvestTimeYears, minTempDay,
                                            maxTempDay, minTempNight, maxTempNight, minHumDay,
                                            maxHumDay, minHumNight, maxHumNight, dayStartTimeHour,
                                            dayStartTimeMin, nightStartTimeHour, nightStartTimeMin, ventTime,
                                            ventIntervals, irriStartTimeHour, irriStartTimeMin, irriTime,
                                            dayMo, dayTu, dayWe, dayTh, dayFr, daySa, daySu)

        plantsCollection.document(currentUserId).collection("Parameters").document(plantName.toString()).set(plants)

    }
}
package com.example.indoor

import java.sql.Timestamp

data class UserActualParameters(var plantName: String, var substrate: String, var fertilizers: String, var pesticides: String, var description: String,
                                var harvestTimeDays: Int?, var harvestTimeMonths: Int?, var harvestTimeYears: Int?, var minTempDay: Double,
                                var maxTempDay: Double, var minTempNight: Double, var maxTempNight: Double, var minHumDay: Double,
                                var maxHumDay: Double, var minHumNight: Double, var maxHumNight: Double, var dayStartTimeHour: Int,
                                var dayStartTimeMin: Int, var nightStartTimeHour: Int, var nightStartTimeMin: Int, var ventTime: Int?,
                                var ventIntervals: Int?, var irriStartTimeHour: Int?, var irriStartTimeMin: Int?, var irriTime: Int?,
                                var dayMo: Int, var dayTu: Int, var dayWe: Int, var dayTh: Int, var dayFr: Int, var daySa: Int, var daySu: Int) {
}
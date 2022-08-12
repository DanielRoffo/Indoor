package com.example.indoor

import java.sql.Timestamp

data class UserParameters(var temperature: Double? = 0.0, var stateTemp: Int? = 0,
                          var humidity: Double? = 0.0, var stateHum: Int? = 0,
                          var waterTimer: Timestamp? = Timestamp(0), var stateWaterTimer: Int? = 0,
                          var water: Double? = 0.0, var stateWater: Int? = 0,
                          ){

}

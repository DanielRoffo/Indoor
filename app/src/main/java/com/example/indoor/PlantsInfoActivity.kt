package com.example.indoor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.indoor.databinding.ActivityPlantsInfoBinding
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase


class PlantsInfoActivity : AppCompatActivity() {
    private lateinit var addsBtn:FloatingActionButton
    private lateinit var recv:RecyclerView
    private lateinit var plantsList:ArrayList<PlantsData>
    private lateinit var plantsAdapter:PlantsInfoAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityPlantsInfoBinding
    private lateinit var arrayPlant: UserActualParameters


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlantsInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        auth = Firebase.auth
        val currentUserId = auth.currentUser!!.uid

        var actName: String?
        var actTemp :Double?
        var actHum :Double?
        var actWaterTime :String?
        var actWater :Double?

        var arrayPlantsList = ArrayList<UserActualParameters>()

        PlantsInfoDao().plantsCollection.document(currentUserId).collection("Parameters").get().addOnSuccessListener { result ->
            for (document in result){


                actName = document.data.getValue("actualPlantName") as String
                actTemp = document.data.getValue("actualTemperature") as Double
                actHum = document.data.getValue("actualHumidity") as Double
                actWaterTime = document.data.getValue("actualWaterTimer") as String
                actWater = document.data.getValue("actualWater") as Double

                //arrayPlant = UserActualParameters(actName, actTemp, actHum, actWaterTime, actWater)

                arrayPlantsList.add(arrayPlant)


            }

            /**set List*/
            //plantsList =
            /**set find Id*/
            addsBtn = findViewById(R.id.addingBtn)
            recv = findViewById(R.id.mRecycler)
            /**set Adapter*/
            plantsAdapter = PlantsInfoAdapter(this as Fragment,arrayPlantsList)
            /**setRecycler view Adapter*/
            recv.layoutManager = LinearLayoutManager(this)
            recv.adapter = plantsAdapter
            /**set Dialog*/
            addsBtn.setOnClickListener { addInfo(arrayPlantsList) }
        }




    }

    private fun addInfo(listPlants: ArrayList<UserActualParameters>) {
        val inflter = LayoutInflater.from(this)
        val v = inflter.inflate(R.layout.add_plant_info_item,null)
        /**set view*/
        /**set view*/
        val plantName = v.findViewById<EditText>(R.id.titleET)
        val tempVal = v.findViewById<EditText>(R.id.TempET)
        val humVal = v.findViewById<EditText>(R.id.HumET)
        val waterTimeVal = v.findViewById<EditText>(R.id.WaterTimeET)
        val waterVal = v.findViewById<EditText>(R.id.WaterET)
        var plantsInfoDao = PlantsInfoDao()

        val addDialog = AlertDialog.Builder(this)

        addDialog.setView(v)
        addDialog.setPositiveButton("Add"){
                dialog,_->
            val title: String? = plantName.text.toString()
            val temp: Double? = tempVal.text.toString().toDouble()
            val hum: Double? = humVal.text.toString().toDouble()
            val waterTime: String? = waterTimeVal.text.toString()
            val water: Double? = waterVal.text.toString().toDouble()

            //plantsInfoDao.addPlantInfo(title, temp, hum, waterTime, water)

            //listPlants.add(UserActualParameters(title, temp, hum, waterTime, water))
            plantsAdapter.notifyDataSetChanged()
            Toast.makeText(this,"Plant parameters successfully added",Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        addDialog.setNegativeButton("Cancel"){
                dialog,_->
            dialog.dismiss()
            Toast.makeText(this,"Cancel",Toast.LENGTH_SHORT).show()

        }
        addDialog.create()
        addDialog.show()
    }
            /**ok now run this */
    /**ok now run this */

}
package com.example.indoor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.indoor.databinding.ActivityPlantsInfoBinding
import com.example.indoor.databinding.FragmentPage1Binding
import com.example.indoor.databinding.FragmentPage2Binding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Page2Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Page2Fragment : Fragment() {

    private lateinit var addsBtn: FloatingActionButton
    private lateinit var recv: RecyclerView
    private lateinit var plantsList:ArrayList<PlantsData>
    private lateinit var plantsAdapter:PlantsInfoAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentPage2Binding
    private lateinit var arrayPlant: UserActualParameters

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPage2Binding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        val currentUserId = auth.currentUser!!.uid

        var plantName: String; var substrate: String; var fertilizers: String; var pesticides: String; var description: String;
        var harvestTimeDays: Int? = null; var harvestTimeMonths: Int? = null; var harvestTimeYears: Int? = null; var minTempDay: Double;
        var maxTempDay: Double; var minTempNight: Double; var maxTempNight: Double; var minHumDay: Double;
        var maxHumDay: Double; var minHumNight: Double; var maxHumNight: Double; var dayStartTimeHour: Int;
        var dayStartTimeMin: Int; var nightStartTimeHour: Int; var nightStartTimeMin: Int; var ventTime: Int? = null;
        var ventIntervals: Int? = null; var irriStartTimeHour: Int? = null; var irriStartTimeMin: Int? = null; var irriTime: Int? = null
        var dayMo: Int; var dayTu: Int; var dayWe: Int; var dayTh: Int; var dayFr: Int; var daySa: Int; var daySu: Int


        var arrayPlantsList = ArrayList<UserActualParameters>()

        PlantsInfoDao().plantsCollection.document(currentUserId).collection("Parameters").get().addOnSuccessListener { result ->
            for (document in result){

                plantName = document.data.getValue("plantName").toString()
                substrate = document.data.getValue("substrate").toString()
                fertilizers = document.data.getValue("fertilizers").toString()
                pesticides = document.data.getValue("pesticides").toString()
                description = document.data.getValue("description").toString()

                if (document.data.getValue("harvestTimeDays") != null){
                    harvestTimeDays = document.data.getValue("harvestTimeDays").toString().toIntOrNull()
                }

                if (document.data.getValue("harvestTimeMonths") != null){
                    harvestTimeMonths = document.data.getValue("harvestTimeMonths").toString().toIntOrNull()
                }

                if (document.data.getValue("harvestTimeYears") != null){
                    harvestTimeYears = document.data.getValue("harvestTimeYears").toString().toIntOrNull()
                }

                minTempDay = document.data.getValue("minTempDay").toString().toDouble()
                maxTempDay = document.data.getValue("maxTempDay").toString().toDouble()
                minTempNight = document.data.getValue("minTempNight").toString().toDouble()
                maxTempNight = document.data.getValue("maxTempNight").toString().toDouble()

                minHumDay = document.data.getValue("minHumDay").toString().toDouble()
                maxHumDay = document.data.getValue("maxHumDay").toString().toDouble()
                minHumNight = document.data.getValue("minHumNight").toString().toDouble()
                maxHumNight = document.data.getValue("maxHumNight").toString().toDouble()

                dayStartTimeHour = document.data.getValue("dayStartTimeHour").toString().toInt()
                dayStartTimeMin = document.data.getValue("dayStartTimeMin").toString().toInt()
                nightStartTimeHour = document.data.getValue("nightStartTimeHour").toString().toInt()
                nightStartTimeMin = document.data.getValue("nightStartTimeMin").toString().toInt()

                if (document.data.getValue("ventTime") != null){
                    ventTime = document.data.getValue("ventTime").toString().toIntOrNull()
                }

                if (document.data.getValue("ventIntervals") != null){
                    ventIntervals = document.data.getValue("ventIntervals").toString().toIntOrNull()
                }

                if (document.data.getValue("irriStartTimeHour") != null){
                    irriStartTimeHour = document.data.getValue("irriStartTimeHour").toString().toIntOrNull()
                }
                if (document.data.getValue("irriStartTimeMin") != null){
                    irriStartTimeMin = document.data.getValue("irriStartTimeMin").toString().toIntOrNull()
                }
                if (document.data.getValue("irriTime") != null){
                    irriTime = document.data.getValue("irriTime").toString().toIntOrNull()
                }

                dayMo = document.data.getValue("dayMo").toString().toInt()
                dayTu = document.data.getValue("dayTu").toString().toInt()
                dayWe = document.data.getValue("dayWe").toString().toInt()
                dayTh = document.data.getValue("dayTh").toString().toInt()
                dayFr = document.data.getValue("dayFr").toString().toInt()
                daySa = document.data.getValue("daySa").toString().toInt()
                daySu = document.data.getValue("daySu").toString().toInt()

                arrayPlant =    UserActualParameters(plantName, substrate, fertilizers, pesticides, description,
                                                    harvestTimeDays, harvestTimeMonths, harvestTimeYears, minTempDay,
                                                    maxTempDay, minTempNight, maxTempNight, minHumDay,
                                                    maxHumDay, minHumNight, maxHumNight, dayStartTimeHour,
                                                    dayStartTimeMin, nightStartTimeHour, nightStartTimeMin, ventTime,
                                                    ventIntervals, irriStartTimeHour, irriStartTimeMin, irriTime,
                                                    dayMo, dayTu, dayWe, dayTh, dayFr, daySa, daySu)

                arrayPlantsList.add(arrayPlant)


            }

            /**set List*/
            //plantsList =
            /**set find Id*/
            addsBtn = binding.addingBtn
            recv = binding.mRecycler
            /**set Adapter*/
            plantsAdapter = PlantsInfoAdapter(this,arrayPlantsList)
            /**setRecycler view Adapter*/
            recv.layoutManager = LinearLayoutManager(context)
            recv.adapter = plantsAdapter
            /**set Dialog*/
            addsBtn.setOnClickListener {
                //addInfo(arrayPlantsList)
                val intent = Intent(context, SetNewParametersActivity::class.java)
                this.startActivity(intent)
            }
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Page2Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Page2Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
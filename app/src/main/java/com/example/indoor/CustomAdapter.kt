package com.example.indoor

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CustomAdapter: RecyclerView.Adapter<CustomAdapter.ViewHolder>(){

    private lateinit var auth: FirebaseAuth

    val titles = arrayOf("House Of Plants",
        "Temperature / Humidity",
        "Watering",
        "Calendar",
        "Notes",
        "Camera",
        "Configurations",
        "Exit")
    val details = arrayOf("Get and set predefined parameters for your plants.",
        "Check Temperature / Humidity in real time",
        "Adjusts automatic watering settings",
        "Set notifications in your calendar",
        "Don't forget anything with your notes!",
        "Check your plants in real time",
        "Set your thermometer and humidity controller",
        "See you soon!")
    val images = intArrayOf(R.drawable.icons8_oak_tree_100,
        R.drawable.icons8_temperature_100,
        R.drawable.icons8water100,
        R.drawable.icons8_autumn_100,
        R.drawable.icons8_tree_planting_100,
        R.drawable.icons8bulletcamera100,
        R.drawable.icons8_spade_100,
        R.drawable.icons8_exit_sign_100)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_layout, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemTitle.text = titles[i]
        viewHolder.itemDetail.text = details[i]
        viewHolder.itemImage.setImageResource(images[i])
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemImage: ImageView
        var itemTitle: TextView
        var itemDetail: TextView

        init {
            auth = Firebase.auth
            itemImage = itemView.findViewById(R.id.item_image)
            itemTitle = itemView.findViewById(R.id.item_title)
            itemDetail = itemView.findViewById(R.id.item_detail)

            itemView.setOnClickListener{
                if(itemTitle.text == titles[0]){
                    val intent = Intent(itemView.context, PlantsInfoActivity::class.java)

                    itemView.context.startActivity(intent)
                }else if(itemTitle.text == titles[1]){
                    val intent = Intent(itemView.context, TempHumActivity::class.java)

                    itemView.context.startActivity(intent)
                }else if(itemTitle.text == titles[2]){
                    val intent = Intent(itemView.context, MainActivity::class.java)

                    itemView.context.startActivity(intent)
                }else if(itemTitle.text == titles[3]){
                    val intent = Intent(itemView.context, CalenderActivity::class.java)

                    itemView.context.startActivity(intent)
                }else if(itemTitle.text == titles[4]){
                    val intent = Intent(itemView.context, NotesActivity::class.java)

                    itemView.context.startActivity(intent)
                }else if(itemTitle.text == titles[5]){
                    val intent = Intent(itemView.context, MainActivity::class.java)

                    itemView.context.startActivity(intent)
                }else if(itemTitle.text == titles[6]){
                    val intent = Intent(itemView.context, ConfigActivity::class.java)

                    itemView.context.startActivity(intent)
                }else{
                    auth.signOut()
                    val intent = Intent(itemView.context, SignInActivity::class.java)
                    itemView.context.startActivity(intent)
                }

            }
        }
    }

}
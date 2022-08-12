package com.example.indoor

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Color.parseColor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.AppCompatButton
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.example.indoor.databinding.ActivityMain2Binding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.navigation.NavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var viewPager: ViewPager2
    private val db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        //sacar titulo del menu
        supportActionBar?.setDisplayShowTitleEnabled(false)


        //
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        firebaseAnalytics = Firebase.analytics

        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        //Para configurar el View Pager

        viewPager = binding.viewPager
        val fragments: ArrayList<Fragment> = arrayListOf(
            Page1Fragment(),
            Page2Fragment(),
            Page3Fragment(),
            Page4Fragment()
        )

        val adapter = ViewPagerAdapter(fragments, this)
        viewPager.adapter = adapter

        //Cambiar el color del menu principal dependiendo la pos de los fragments

        onClickMenuItem()
        onChangeMenu()

        //Precarga de los siguientes fragmentos
        
        viewPager.offscreenPageLimit = 2;

        //Para configurar los menus laterales

        supportActionBar?.elevation = 0f

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        toggle = ActionBarDrawerToggle(this,drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {

            when(it.itemId){

                R.id.nav_home -> {
                    val intent = Intent(this, MainActivity2::class.java)
                    this.startActivity(intent)
                }
                R.id.nav_profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    this.startActivity(intent)
                }
                R.id.nav_calendar -> {
                    val intent = Intent(this, CalenderActivity::class.java)
                    this.startActivity(intent)
                }
                R.id.nav_notes -> {
                    val intent = Intent(this, NotesActivity::class.java)
                    this.startActivity(intent)
                }
                R.id.nav_logout -> {
                    auth.signOut()
                    val intent = Intent(this, SignInActivity::class.java)
                    this.startActivity(intent)
                }
                R.id.nav_share -> Toast.makeText(applicationContext, "Clicked Share", Toast.LENGTH_SHORT).show()
                R.id.nav_rate_us -> Toast.makeText(applicationContext, "Clicked Share", Toast.LENGTH_SHORT).show()

            }

            true
        }

    }

    private fun addDevice(device: String){
        val code = DeviceCodeData(device)
        val currentUserId = auth.currentUser!!.uid
        val deviceCode = db.collection("Users")
        deviceCode.document(currentUserId).collection("DeviceCode").document("Code").set(code)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId){
            R.id.config -> {
                // Creacion de un alertDialog --> Ventana q se abre dentro de un mismo activity.
                // Se pasan los valores
                val builder = AlertDialog.Builder(this@MainActivity2)
                val view = layoutInflater.inflate(R.layout.config_device, null)
                // Se pasa la vista al builder
                builder.setView(view)
                //Se crea el Dialog
                val dialog = builder.create()
                dialog.show()

                //Carga de codigo de activacion del dispositivo
                dialog.findViewById<AppCompatButton>(R.id.btnAcceptDialog).setOnClickListener {
                    //Se recuperan los valores del dialog
                    val deviceCode = dialog.findViewById<EditText>(R.id.device_code).text.toString()
                    addDevice(deviceCode)
                    Toast.makeText(this,"New device correctly set", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                dialog.findViewById<AppCompatButton>(R.id.btnCancelDialog).setOnClickListener{ dialog.dismiss() }
            }

        }
        if(toggle.onOptionsItemSelected(item)){

            return true

        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }

    override fun onBackPressed() {
        if(viewPager.currentItem == 0){
            super.onBackPressed()
        }else{
            viewPager.currentItem = viewPager.currentItem - 1
        }

    }
    private fun onClickMenuItem() {

        binding.homeMenu.setOnClickListener {
            viewPager.currentItem = 0
        }
        binding.parametersMenu.setOnClickListener {
            viewPager.currentItem = 1
        }
        binding.forumMenu.setOnClickListener {
            viewPager.currentItem = 2
        }
        binding.submissionsMenu.setOnClickListener {
            viewPager.currentItem = 3
        }
    }
    private fun onChangeMenu(){

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    // you are on the first page
                    binding.homeMenu.setCardBackgroundColor(Color.parseColor("#66BAFD"))
                    binding.parametersMenu.setCardBackgroundColor(Color.parseColor("#faedcd"))
                    binding.forumMenu.setCardBackgroundColor(Color.parseColor("#faedcd"))
                    binding.submissionsMenu.setCardBackgroundColor(Color.parseColor("#faedcd"))
                }
                else if (position == 1) {
                    // you are on the second page
                    binding.homeMenu.setCardBackgroundColor(Color.parseColor("#faedcd"))
                    binding.parametersMenu.setCardBackgroundColor(Color.parseColor("#66BAFD"))
                    binding.forumMenu.setCardBackgroundColor(Color.parseColor("#faedcd"))
                    binding.submissionsMenu.setCardBackgroundColor(Color.parseColor("#faedcd"))
                }
                else if (position == 2){
                    // you are on the third page
                    binding.homeMenu.setCardBackgroundColor(Color.parseColor("#faedcd"))
                    binding.parametersMenu.setCardBackgroundColor(Color.parseColor("#faedcd"))
                    binding.forumMenu.setCardBackgroundColor(Color.parseColor("#66BAFD"))
                    binding.submissionsMenu.setCardBackgroundColor(Color.parseColor("#faedcd"))
                }else if (position == 3){
                    // you are on the fourth page
                    binding.homeMenu.setCardBackgroundColor(Color.parseColor("#faedcd"))
                    binding.parametersMenu.setCardBackgroundColor(Color.parseColor("#faedcd"))
                    binding.forumMenu.setCardBackgroundColor(Color.parseColor("#faedcd"))
                    binding.submissionsMenu.setCardBackgroundColor(Color.parseColor("#66BAFD"))
                }
                super.onPageSelected(position)
            }
        })
    }

}

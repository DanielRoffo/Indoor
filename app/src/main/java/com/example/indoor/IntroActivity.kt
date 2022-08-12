package com.example.indoor

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.indoor.databinding.ActivityIntroBinding


class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val splashanimation = AnimationUtils.loadAnimation(this,R.anim.asset_fade_in)
        binding.imageView3.startAnimation(splashanimation)
        binding.textView11.startAnimation(splashanimation)
        binding.textView2.startAnimation(splashanimation)
        binding.textView12.startAnimation(splashanimation)
        binding.textView13.startAnimation(splashanimation)
        binding.textView14.startAnimation(splashanimation)


        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        }else{
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        @Suppress("DEPRECATION")
        Handler().postDelayed(
            {
                startActivity(
                    Intent(this@IntroActivity, SignInActivity::class.java)
                )
                finish()
            },3000
        )
    }
}
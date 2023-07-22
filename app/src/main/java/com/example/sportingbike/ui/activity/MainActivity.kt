package com.example.sportingbike.ui.activity

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.sportingbike.R
import com.example.sportingbike.databinding.ActivityMainBinding
import com.example.sportingbike.ui.fragment.menu.MainFragment
import com.example.sportingbike.ui.fragment.onboarding.OnBoardingFragment
import com.example.sportingbike.utils.Constants
import com.google.android.gms.tasks.Task
import com.google.firebase.remoteconfig.FirebaseRemoteConfig


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var prefs = getSharedPreferences(Constants.PREF, Context.MODE_PRIVATE)

        if (prefs.getBoolean("firstrun", true)) {

            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout, OnBoardingFragment())
            fragmentTransaction.commit()
            overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_bottom)

            prefs.edit().putBoolean("firstrun", false).apply();
        } else{
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout, MainFragment())
            fragmentTransaction.commit()
        }

    }
}
package com.example.kotlin.historia.framework.views.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.kotlin.historia.databinding.ActivitySplashsceenBinding
import com.example.kotlin.mypokedexapp.framework.viewmodel.SplashscreenViewModel

class SplashscreenActivity:AppCompatActivity() {

    private lateinit var binding: ActivitySplashsceenBinding
    private val viewModel: SplashscreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeBinding()

        viewModel.onCreate()

        initializeObservers()
    }

    private fun initializeBinding(){
        binding = ActivitySplashsceenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initializeObservers(){
        viewModel.finishedLoading.observe(this, Observer {finishedLoading->
            if(finishedLoading){
                passViewGoToMain()
            }
        })
    }
    private fun passViewGoToMain() {
        var intent: Intent = Intent(this, HistoriaActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
        finish()
    }
}
package com.example.uniforeventos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class OnboardingTwoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_two)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val btnNext = findViewById<Button>(R.id.btnNext)
        val btnSkip = findViewById<Button>(R.id.btnSkip)

        btnBack.setOnClickListener {
            finish()
        }

        btnNext.setOnClickListener {
            startActivity(Intent(this, OnboardingThreeActivity::class.java))
        }

        btnSkip.setOnClickListener {
            startActivity(Intent(this, LivrosReservadosActivity::class.java))
            finishAffinity()
        }
    }
}
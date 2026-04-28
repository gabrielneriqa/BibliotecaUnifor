package com.example.uniforeventos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class OnboardingThreeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_three)

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        val btnStart = findViewById<Button>(R.id.btnStart)
        val btnSkip = findViewById<Button>(R.id.btnSkip)

        btnBack.setOnClickListener {
            finish()
        }

        val irParaLivros = {
            startActivity(Intent(this, HomeActivity::class.java))
            finishAffinity()
        }

        btnStart.setOnClickListener { irParaLivros() }
        btnSkip.setOnClickListener { irParaLivros() }
    }
}
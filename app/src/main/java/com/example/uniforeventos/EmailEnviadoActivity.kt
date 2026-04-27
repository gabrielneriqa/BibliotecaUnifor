package com.example.uniforeventos

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EmailEnviadoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_enviado)

        val btnVoltar = findViewById<ImageView>(R.id.btnVoltar)
        val btnAbrirEmail = findViewById<TextView>(R.id.btnAbrirEmail)

        btnVoltar.setOnClickListener {
            finish()
        }

        btnAbrirEmail.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
    }
}
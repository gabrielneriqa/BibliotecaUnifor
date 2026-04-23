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

        val ivVoltar = findViewById<ImageView>(R.id.ivVoltar)
        val btnAbrirEmail = findViewById<TextView>(R.id.btnAbrirEmail)

        ivVoltar.setOnClickListener {
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
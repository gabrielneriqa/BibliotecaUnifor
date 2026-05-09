package com.example.uniforeventos

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReservaConfirmada: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserva_confirmada)

        voltar()
    }

    private fun voltar(){
        findViewById<TextView>(R.id.btnVoltar).setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}
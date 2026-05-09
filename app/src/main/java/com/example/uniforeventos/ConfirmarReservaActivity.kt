package com.example.uniforeventos

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ConfirmarReservaActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmar_reserva)

        reservaConfirmada()
        reservaCancelada()
    }

    private fun reservaConfirmada(){
        findViewById<TextView>(R.id.btnSim).setOnClickListener {
            startActivity(Intent(this, ReservaConfirmada::class.java))
        }
    }

    private fun reservaCancelada(){
        findViewById<TextView>(R.id.btnCancelar).setOnClickListener {
            finish()
        }
    }
}
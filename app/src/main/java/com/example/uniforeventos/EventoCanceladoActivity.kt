package com.example.uniforeventos

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class EventoCanceladoActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evento_cancelado)
        sair()
    }

    private fun sair(){
        findViewById<MaterialButton>(R.id.btnVoltar).setOnClickListener {
            finish()
        }
    }
}
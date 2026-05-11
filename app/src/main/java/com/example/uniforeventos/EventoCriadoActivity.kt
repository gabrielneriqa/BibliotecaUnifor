package com.example.uniforeventos

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class EventoCriadoActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evento_confirmado)

        voltar()
    }

    private fun voltar(){
        findViewById<MaterialButton>(R.id.btnVoltar).setOnClickListener {
            finish()
        }
    }
}
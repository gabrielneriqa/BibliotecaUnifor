package com.example.uniforeventos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView

class PresencaCanceladaActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_presenca_cancelada)
        sair()
    }

    private fun sair(){
        findViewById<MaterialCardView>(R.id.btnVoltar).setOnClickListener {
            finish()
        }
    }
}
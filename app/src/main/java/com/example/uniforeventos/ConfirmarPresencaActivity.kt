package com.example.uniforeventos

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView

class ConfirmarPresencaActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmar_presenca)
        confirmar()
        cancelar()
    }

    private fun confirmar(){
        findViewById<MaterialCardView>(R.id.btnConfirmarPresenca).setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }

    private fun cancelar(){
        findViewById<MaterialCardView>(R.id.btnCancelarPresenca).setOnClickListener {
            finish()
        }
    }

}
package com.example.uniforeventos

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView

class CancelarPresencaActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cancelar_presenca)

        findViewById<MaterialCardView>(R.id.btnCancelar).setOnClickListener {
            finish()
        }

        findViewById<MaterialCardView>(R.id.btnConfirmar).setOnClickListener {
            startActivity(Intent(this, EventoCanceladoActivity::class.java))
            finish()
        }
    }
}
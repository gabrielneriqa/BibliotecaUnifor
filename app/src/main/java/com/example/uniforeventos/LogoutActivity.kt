package com.example.uniforeventos

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LogoutActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conta)
        sair()
        cancelar()
    }

    private fun sair(){
        findViewById<TextView>(R.id.btnSair).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun cancelar(){
        findViewById<TextView>(R.id.btnCancelar).setOnClickListener {
            startActivity(Intent(this, ContaUsuarioActivity::class.java))
        }
    }

}
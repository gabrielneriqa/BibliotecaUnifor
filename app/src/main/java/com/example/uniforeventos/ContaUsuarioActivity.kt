package com.example.uniforeventos

import android.content.Intent
import android.os.Bundle
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity

class ContaUsuarioActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conta_usuario)

        suporte()
        redefinirSenha()
        idioma()
        logout()
    }

    private fun suporte(){
        findViewById<RelativeLayout>(R.id.rlSuporte).setOnClickListener {
            startActivity(Intent(this, SuporteActivity::class.java))
        }
    }

    private fun redefinirSenha(){
        findViewById<RelativeLayout>(R.id.rlSenha).setOnClickListener {
            startActivity(Intent(this, RedefinirSenhaActivity::class.java))
        }
    }

    private fun idioma(){
        findViewById<RelativeLayout>(R.id.rlIdiomas).setOnClickListener {
            startActivity(Intent(this, IdiomasActivity::class.java))
        }
    }

    private fun logout(){
        findViewById<RelativeLayout>(R.id.rlExit).setOnClickListener {
            startActivity(Intent(this, LogoutActivity::class.java))
        }
    }
}
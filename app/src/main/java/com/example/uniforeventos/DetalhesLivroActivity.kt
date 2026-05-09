package com.example.uniforeventos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class DetalhesLivroActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_livro)
        reservarLivro()
        voltar()
    }

    private fun reservarLivro(){
        var botaoReservar = findViewById<LinearLayout>(R.id.btnReservar)
        botaoReservar.setOnClickListener {
            startActivity(Intent(this, ConfirmarReservaActivity::class.java))
        }
    }

    private fun voltar(){
        findViewById<ImageView>(R.id.btnVoltar).setOnClickListener { finish() }
    }

    fun abrir(contexto: Context){
        val intent = Intent(contexto, DetalhesLivroActivity::class.java)
        contexto.startActivity(intent)
    }

}
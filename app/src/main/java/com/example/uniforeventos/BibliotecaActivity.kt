package com.example.uniforeventos

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BibliotecaActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_biblioteca)

        verDetalhes()
        backButton()
        verFiltro()
    }

    private fun verDetalhe(){
        startActivity(Intent(this, DetalhesLivroActivity::class.java))
    }

    private fun verDetalhes(){
        findViewById<TextView>(R.id.btnDetalhes1).setOnClickListener {verDetalhe()}
        findViewById<TextView>(R.id.btnDetalhes2).setOnClickListener {verDetalhe()}
        findViewById<TextView>(R.id.btnDetalhes3).setOnClickListener {verDetalhe()}
        findViewById<TextView>(R.id.btnDetalhes4).setOnClickListener {verDetalhe()}
    }

    private fun verFiltro(){
        findViewById<LinearLayout>(R.id.llFilter).setOnClickListener {
            startActivity(Intent(this, FilterActivity::class.java))
        }
    }

    private fun backButton(){
        val botaoVoltar = findViewById<TextView>(R.id.tvVoltar)
        botaoVoltar.setOnClickListener {
            finish()
        }
    }
}
package com.example.uniforeventos

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class EdicaoEventoActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_evento)
        botaoConfirmar()
    }
    private fun botaoConfirmar(){
        findViewById<MaterialButton>(R.id.btnSalvarAlteracoes).setOnClickListener { salvar() }
    }

    private fun salvar(){
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_confirmar_edicao, null)
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)

        val alertDialog = builder.create()
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialogView.findViewById<MaterialCardView>(R.id.btnDialogVoltar).setOnClickListener {
            alertDialog.dismiss()
        }

        dialogView.findViewById<MaterialCardView>(R.id.btnDialogConfirmarEdicao).setOnClickListener {
            startActivity(Intent(this, EventoCriadoActivity::class.java))
            alertDialog.dismiss()
            finish()
        }

        alertDialog.show()
    }
}
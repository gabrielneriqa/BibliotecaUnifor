package com.example.uniforeventos

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView

class EditarEventoActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cancelar_evento)
        editarEvento()
        cancelarEvento()
    }

    private fun editarEvento(){
        findViewById<MaterialCardView>(R.id.btnEditarEvento).setOnClickListener {
            startActivity(Intent(this, EdicaoEventoActivity::class.java))
            finish()
        }
    }

    private fun cancelarEvento(){
        findViewById<MaterialCardView>(R.id.btnCancelarEvento).setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_confirmar_cancelamento, null)
            val builder = AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(true)

            val alertDialog = builder.create()
            alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

            dialogView.findViewById<MaterialCardView>(R.id.btnDialogVoltar).setOnClickListener {
                alertDialog.dismiss()
            }

            dialogView.findViewById<MaterialCardView>(R.id.btnDialogConfirmarCancelamento).setOnClickListener {
                startActivity(Intent(this, EventoCanceladoActivity::class.java))
                alertDialog.dismiss()
                finish()
            }

            alertDialog.show()
        }
    }
}

package com.example.uniforeventos

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView

class CancelarEventoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cancelar_evento)

        configurarBotaoCancelar()
    }

    private fun configurarBotaoCancelar() {
        val botaoCancelarEvento = findViewById<MaterialCardView>(R.id.btnCancelarEvento)

        botaoCancelarEvento.setOnClickListener {
            exibirDialogoConfirmacaoCancelamento()
        }
    }

    private fun exibirDialogoConfirmacaoCancelamento() {
        val dialogView = LayoutInflater.from(this)
            .inflate(R.layout.dialog_confirmar_cancelamento, null)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)
            .create()

        dialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

            attributes = attributes.apply {
                dimAmount = 0.70f
            }
        }

        val botaoVoltar = dialogView.findViewById<MaterialCardView>(R.id.btnDialogVoltar)
        val botaoConfirmarCancelamento =
            dialogView.findViewById<MaterialCardView>(R.id.btnDialogConfirmarCancelamento)

        botaoVoltar.setOnClickListener {
            dialog.dismiss()
        }

        botaoConfirmarCancelamento.setOnClickListener {
            dialog.dismiss()

            // Aqui entra a ação real de cancelamento do evento.
            // Exemplo:
            // cancelarEvento()
            // finish()

            finish()
        }

        dialog.show()
    }

}
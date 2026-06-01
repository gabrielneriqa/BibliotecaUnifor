package com.example.uniforeventos

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView
import com.google.android.material.bottomnavigation.BottomNavigationView

class EditarEventoActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cancelar_evento)
        editarEvento()
        cancelarEvento()
        configurarBottomNav()
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

    private fun configurarBottomNav() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.selectedItemId = R.id.nav_home

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }
                R.id.nav_notifications -> {
                    startActivity(Intent(this, NotificationActivity::class.java))
                    true
                }
                R.id.nav_books -> {
                    startActivity(Intent(this, LivrosReservadosActivity::class.java))
                    true
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, ContaUsuarioActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

}
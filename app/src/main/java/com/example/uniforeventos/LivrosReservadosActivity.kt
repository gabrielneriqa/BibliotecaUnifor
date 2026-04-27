package com.example.uniforeventos

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class LivrosReservadosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_livros_reservados)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.selectedItemId = R.id.nav_books

        val btnEstender = findViewById<TextView>(R.id.btnEstenderEmprestimo)
        btnEstender.setOnClickListener {
            startActivity(Intent(this, EstenderEmprestimoActivity::class.java))
        }
    }
}
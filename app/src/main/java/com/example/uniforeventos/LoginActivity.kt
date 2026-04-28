package com.example.uniforeventos

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private var senhaVisivel = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etMatricula = findViewById<EditText>(R.id.etMatricula)
        val etSenha = findViewById<EditText>(R.id.etSenha)
        val ivOlho = findViewById<ImageView>(R.id.ivOlho)
        val btnEntrar = findViewById<TextView>(R.id.btnEntrar)
        val btnLoginFake = findViewById<TextView>(R.id.btnLoginFake)
        val tvEsqueceuSenha = findViewById<TextView>(R.id.tvEsqueceuSenha)

        ivOlho.setOnClickListener {
            senhaVisivel = !senhaVisivel
            etSenha.inputType = if (senhaVisivel) {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            etSenha.setSelection(etSenha.text.length)
        }

        val acaoLogin = {
            val matricula = etMatricula.text.toString().trim()
            val senha = etSenha.text.toString().trim()

            if (matricula == "1" && senha == "123") {
                startActivity(Intent(this, OnboardingOneActivity::class.java))
                finish()
            } else {
                Toast.makeText(
                    this,
                    "Matrícula ou senha inválidas.", // Use 20240001 / 123456
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        btnEntrar.setOnClickListener { acaoLogin() }
        btnLoginFake.setOnClickListener { acaoLogin() }

        tvEsqueceuSenha.setOnClickListener {
            startActivity(Intent(this, RedefinirSenhaActivity::class.java))
        }
    }
}
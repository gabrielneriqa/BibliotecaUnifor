package com.example.uniforeventos

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.uniforeventos.network.RetrofitClient
import com.example.uniforeventos.network.dto.LoginRequestDTO
import com.example.uniforeventos.network.dto.TokenUsuarioDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private var senhaVisivel = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etMatricula = findViewById<EditText>(R.id.etMatricula)
        val etSenha     = findViewById<EditText>(R.id.etSenha)
        val ivOlho      = findViewById<ImageView>(R.id.ivOlho)
        val btnEntrar   = findViewById<TextView>(R.id.btnEntrar)
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

        btnEntrar.setOnClickListener {
            val matricula = etMatricula.text.toString().trim()
            val senha = etSenha.text.toString().trim()

            if (matricula.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            btnEntrar.isEnabled = false
            btnEntrar.text = "Entrando..."

            val request = LoginRequestDTO(matricula = matricula, senha = senha)

            RetrofitClient.usuarioApiService.loginPorMatricula(request)
                .enqueue(object : Callback<TokenUsuarioDTO> {
                    override fun onResponse(
                        call: Call<TokenUsuarioDTO>,
                        response: Response<TokenUsuarioDTO>
                    ) {
                        if (response.isSuccessful) {
                            val token = response.body()!!
                            SessionManager.salvarSessao(
                                this@LoginActivity,
                                usuarioId = token.usuarioId,
                                nome = token.usuarioNome,
                                usuarioToken =  token.token
                            )
                            startActivity(Intent(this@LoginActivity, OnboardingOneActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                "Matrícula ou senha inválidos.",
                                Toast.LENGTH_SHORT
                            ).show()
                            btnEntrar.isEnabled = true
                            btnEntrar.text = "Entrar"
                        }
                    }

                    override fun onFailure(call: Call<TokenUsuarioDTO>, t: Throwable) {
                        Toast.makeText(
                            this@LoginActivity,
                            "Falha de conexão. Verifique sua internet.",
                            Toast.LENGTH_LONG
                        ).show()
                        btnEntrar.isEnabled = true
                        btnEntrar.text = "Entrar"
                    }
                })
        }

        btnLoginFake.setOnClickListener {
            SessionManager.salvarSessao(this, usuarioId = 1L, nome = "Usuário", usuarioToken = "token-1")
            startActivity(Intent(this, HomeActivity::class.java))
        }

        tvEsqueceuSenha.setOnClickListener {
            startActivity(Intent(this, RedefinirSenhaActivity::class.java))
        }
    }
}
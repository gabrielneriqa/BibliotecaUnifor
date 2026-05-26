package com.example.uniforeventos

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.uniforeventos.network.RetrofitClient
import com.example.uniforeventos.network.dto.EmprestimoRequestDTO
import com.example.uniforeventos.network.dto.EmprestimoResponseDTO
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConfirmarReservaActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_LIVRO_ID = "livro_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmar_reserva)

        val livroId = intent.getLongExtra(EXTRA_LIVRO_ID, -1L)

        configurarBotaoConfirmar(livroId)
        configurarBotaoCancelar()
        configurarBottomNav()
    }

    private fun configurarBotaoConfirmar(livroId: Long) {
        val btnSim = findViewById<TextView>(R.id.btnSim)

        btnSim.setOnClickListener {
            if (livroId == -1L) {
                Toast.makeText(this, "Livro inválido.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val usuarioId = SessionManager.getUsuarioId(this)
            if (usuarioId == -1L) {
                Toast.makeText(this, "Sessão expirada. Faça login novamente.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            btnSim.isEnabled = false
            btnSim.text = "Reservando..."

            val request = EmprestimoRequestDTO(
                usuarioId = usuarioId,
                livroId = livroId
            )

            RetrofitClient.instance.criar(request).enqueue(object : Callback<EmprestimoResponseDTO> {
                override fun onResponse(
                    call: Call<EmprestimoResponseDTO>,
                    response: Response<EmprestimoResponseDTO>
                ) {
                    if (response.isSuccessful) {
                        startActivity(Intent(this@ConfirmarReservaActivity, ReservaConfirmada::class.java))
                        finish()
                    } else {
                        val erro = when (response.code()) {
                            400 -> "Livro sem exemplares disponíveis."
                            404 -> "Livro ou usuário não encontrado."
                            else -> "Erro ao reservar (${response.code()})."
                        }
                        Toast.makeText(this@ConfirmarReservaActivity, erro, Toast.LENGTH_LONG).show()
                        btnSim.isEnabled = true
                        btnSim.text = "Sim"
                    }
                }

                override fun onFailure(call: Call<EmprestimoResponseDTO>, t: Throwable) {
                    Toast.makeText(
                        this@ConfirmarReservaActivity,
                        "Falha de conexão. Verifique sua internet.",
                        Toast.LENGTH_LONG
                    ).show()
                    btnSim.isEnabled = true
                    btnSim.text = "Sim"
                }
            })
        }
    }

    private fun configurarBotaoCancelar() {
        findViewById<TextView>(R.id.btnCancelar).setOnClickListener { finish() }
    }

    private fun configurarBottomNav() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.selectedItemId = R.id.nav_books

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
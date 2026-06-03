package com.example.uniforeventos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.uniforeventos.model.LivroResponse
import com.example.uniforeventos.repository.LivroRepository
import kotlinx.coroutines.launch

class DetalhesLivroActivity : AppCompatActivity() {

    private val livroRepository = LivroRepository()

    companion object {
        const val EXTRA_LIVRO_ID = "livro_id"

        fun abrir(context: Context, livroId: Long) {
            val intent = Intent(context, DetalhesLivroActivity::class.java)
            intent.putExtra(EXTRA_LIVRO_ID, livroId)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_livro)

        val livroId = intent.getLongExtra(EXTRA_LIVRO_ID, -1L)

        if (livroId == -1L) {
            Toast.makeText(this, "Livro inválido.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        voltar()
        configurarBottomNav()
        carregarLivro(livroId)
    }

    private fun carregarLivro(livroId: Long) {
        lifecycleScope.launch {
            try {
                val response = livroRepository.buscarLivroPorId(livroId)

                if (response.isSuccessful) {
                    val livro = response.body()

                    if (livro == null) {
                        Toast.makeText(
                            this@DetalhesLivroActivity,
                            "Livro não encontrado.",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                        return@launch
                    }

                    preencherDadosLivro(livro)
                    configurarBotaoReservar(livro)
                } else {
                    Log.e("API_LIVRO_DETALHE", "Erro HTTP: ${response.code()}")
                    Log.e("API_LIVRO_DETALHE", "Erro body: ${response.errorBody()?.string()}")

                    Toast.makeText(
                        this@DetalhesLivroActivity,
                        "Não foi possível carregar o livro.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (exception: Exception) {
                Log.e("API_LIVRO_DETALHE", "Erro ao buscar livro", exception)

                Toast.makeText(
                    this@DetalhesLivroActivity,
                    "Erro de conexão ao carregar livro.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun preencherDadosLivro(livro: LivroResponse) {
        val titulo = findViewById<TextView>(R.id.tvTituloLivro)
        val subInfo = findViewById<TextView>(R.id.tvSubInfo)
        val autor = findViewById<TextView>(R.id.tvAutorLivro)
        val statusDisponibilidade = findViewById<TextView>(R.id.tvStatusDisponibilidade)
        val copiasDisponiveis = findViewById<TextView>(R.id.tvCopiasDisponiveis)
        val imagemLivro = findViewById<ImageView>(R.id.imgLivro)

        val quantidadeDisponivel = livro.quantidadeDisponivel ?: 0
        val quantidadeTotal = livro.quantidadeTotal ?: 0

        titulo.text = livro.titulo
        autor.text = livro.autor

        subInfo.text = "Código: ${livro.codigo} | Ano: ${livro.ano ?: "Não informado"}"

        statusDisponibilidade.text = if (quantidadeDisponivel > 0) {
            "DISPONÍVEL"
        } else {
            "INDISPONÍVEL"
        }

        copiasDisponiveis.text = if (quantidadeDisponivel == 1) {
            "1 CÓPIA"
        } else {
            "$quantidadeDisponivel CÓPIAS"
        }

        imagemLivro.setImageResource(R.drawable.livro_mock)

        Log.d(
            "API_LIVRO_DETALHE",
            "Livro carregado: ${livro.titulo} | Disponíveis: $quantidadeDisponivel/$quantidadeTotal"
        )
    }

    private fun configurarBotaoReservar(livro: LivroResponse) {
        val botaoReservar = findViewById<LinearLayout>(R.id.btnReservar)
        val quantidadeDisponivel = livro.quantidadeDisponivel ?: 0

        if (quantidadeDisponivel <= 0) {
            botaoReservar.alpha = 0.5f
            botaoReservar.isEnabled = false
            botaoReservar.setOnClickListener(null)
            return
        }

        botaoReservar.alpha = 1f
        botaoReservar.isEnabled = true

        botaoReservar.setOnClickListener {
            val intent = Intent(this, ConfirmarReservaActivity::class.java)
            intent.putExtra(ConfirmarReservaActivity.EXTRA_LIVRO_ID, livro.id)
            startActivity(intent)
        }
    }

    private fun reservarLivro(livroId: Long) {
        val botaoReservar = findViewById<LinearLayout>(R.id.btnReservar)
        botaoReservar.setOnClickListener {
            val intent = Intent(this, ConfirmarReservaActivity::class.java)
            intent.putExtra(ConfirmarReservaActivity.EXTRA_LIVRO_ID, livroId)
            startActivity(intent)
        }
    }

    private fun voltar() {
        findViewById<ImageView>(R.id.btnVoltar).setOnClickListener { finish() }
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
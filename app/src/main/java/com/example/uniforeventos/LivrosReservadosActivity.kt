package com.example.uniforeventos

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uniforeventos.network.RetrofitClient
import com.example.uniforeventos.network.dto.EmprestimoResponseDTO
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LivrosReservadosActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var adapter: EmprestimoAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvVazio: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_livros_reservados)

        drawerLayout = findViewById(R.id.drawerLayout)
        recyclerView = findViewById(R.id.recyclerLivrosReservados)
        tvVazio = findViewById(R.id.tvSemLivros)

        adapter = EmprestimoAdapter(emptyList()) { emprestimo ->
            val intent = Intent(this, EstenderEmprestimoActivity::class.java)
            intent.putExtra(EstenderEmprestimoActivity.EXTRA_EMPRESTIMO_ID, emprestimo.id)
            intent.putExtra(EstenderEmprestimoActivity.EXTRA_LIVRO_TITULO, emprestimo.livroTitulo)
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        configurarHamburger()
        configurarSidebar()
        configurarBottomNav()
        carregarEmprestimos()
    }

    override fun onResume() {
        super.onResume()
        // Recarrega ao voltar de EstenderEmprestimoActivity ou ConfirmarReservaActivity
        carregarEmprestimos()
    }

    private fun carregarEmprestimos() {
        val usuarioId = SessionManager.getUsuarioId(this)
        if (usuarioId == -1L) return

        RetrofitClient.emprestimoApiService.listarPorUsuario(usuarioId)
            .enqueue(object : Callback<List<EmprestimoResponseDTO>> {
                override fun onResponse(
                    call: Call<List<EmprestimoResponseDTO>>,
                    response: Response<List<EmprestimoResponseDTO>>
                ) {
                    if (response.isSuccessful) {
                        val lista = response.body() ?: emptyList()
                        adapter.atualizarLista(lista)
                        tvVazio.visibility = if (lista.isEmpty()) View.VISIBLE else View.GONE
                        recyclerView.visibility = if (lista.isEmpty()) View.GONE else View.VISIBLE
                    } else {
                        Toast.makeText(
                            this@LivrosReservadosActivity,
                            "Erro ao carregar empréstimos.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<List<EmprestimoResponseDTO>>, t: Throwable) {
                    Toast.makeText(
                        this@LivrosReservadosActivity,
                        "Falha de conexão.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun configurarHamburger() {
        findViewById<View>(R.id.btnMenu).setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    private fun configurarSidebar() {
        findViewById<View>(R.id.btnFecharSidebar).setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        findViewById<View>(R.id.menuHome).setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            startActivity(Intent(this, HomeActivity::class.java))
        }
        findViewById<View>(R.id.menuCriarEvento).setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            startActivity(Intent(this, CreateEventActivity::class.java))
        }
        findViewById<View>(R.id.menuNotificacoes).setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            Toast.makeText(this, "Em breve", Toast.LENGTH_SHORT).show()
        }
        findViewById<View>(R.id.menuLivrosReservados).setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        findViewById<View>(R.id.menuEventosReservados).setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            Toast.makeText(this, "Em breve", Toast.LENGTH_SHORT).show()
        }
        findViewById<View>(R.id.menuPerfil).setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            Toast.makeText(this, "Em breve", Toast.LENGTH_SHORT).show()
        }
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
                R.id.nav_books -> true
                R.id.nav_profile -> {
                    startActivity(Intent(this, ContaUsuarioActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
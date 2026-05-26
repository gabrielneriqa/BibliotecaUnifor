package com.example.uniforeventos

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uniforeventos.network.RetrofitClient
import com.example.uniforeventos.network.dto.EmprestimoResponseDTO
import com.example.uniforeventos.network.dto.EstenderEmprestimoDTO
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

class EstenderEmprestimoActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_EMPRESTIMO_ID = "emprestimo_id"
        const val EXTRA_LIVRO_TITULO = "livro_titulo"
    }

    private var startDate: LocalDate? = null
    private var endDate: LocalDate? = null

    private lateinit var adapter: CalendarAdapter
    private var currentDays: List<CalendarDay> = emptyList()
    private var currentYearMonth: YearMonth = YearMonth.now()

    // 🔧 Substituir por datas reais vindas da API (datas já reservadas por outros)
    private val reservedDates: Set<LocalDate> = setOf(
        LocalDate.of(2026, 4, 28),
        LocalDate.of(2026, 4, 29)
    )

    private var emprestimoId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estender_emprestimo)

        emprestimoId = intent.getLongExtra(EXTRA_EMPRESTIMO_ID, -1L)
        val livroTitulo = intent.getStringExtra(EXTRA_LIVRO_TITULO) ?: ""

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerCalendar)
        val btnConfirmar = findViewById<View>(R.id.btnConfirmar)

        findViewById<View>(R.id.ivVoltar).setOnClickListener { finish() }

        adapter = CalendarAdapter(emptyList()) { diaClicado ->
            handleDayClick(diaClicado)
        }

        recyclerView.layoutManager = GridLayoutManager(this, 7)
        recyclerView.adapter = adapter

        carregarMes(currentYearMonth)

        findViewById<View>(R.id.btnMesAnterior).setOnClickListener {
            currentYearMonth = currentYearMonth.minusMonths(1)
            carregarMes(currentYearMonth)
        }

        findViewById<View>(R.id.btnProximoMes).setOnClickListener {
            currentYearMonth = currentYearMonth.plusMonths(1)
            carregarMes(currentYearMonth)
        }

        btnConfirmar.setOnClickListener { confirmarExtensao(btnConfirmar) }

        configurarBottomNav()
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

    // ─── Lógica de seleção de período ───────────────────────────────────────

    private fun handleDayClick(day: CalendarDay) {
        val date = day.date ?: return
        val s = startDate
        val e = endDate

        when {
            e != null || date == s -> {
                startDate = date
                endDate = null
            }
            s == null -> {
                startDate = date
            }
            date.isBefore(s) -> {
                startDate = date
                endDate = null
            }
            else -> {
                val temReservadoNoMeio = reservedDates.any { reserved ->
                    reserved.isAfter(s) && reserved.isBefore(date)
                }
                if (temReservadoNoMeio) {
                    Toast.makeText(this, "O período contém datas reservadas", Toast.LENGTH_SHORT).show()
                } else {
                    endDate = date
                }
            }
        }

        refreshCalendar()
    }

    // ─── Calendário ─────────────────────────────────────────────────────────

    private fun carregarMes(yearMonth: YearMonth) {
        val nomeMes = yearMonth.month
            .getDisplayName(TextStyle.FULL, Locale("pt", "BR"))
            .replaceFirstChar { it.uppercase() }

        findViewById<TextView>(R.id.tvMesAno).text = "$nomeMes ${yearMonth.year}"
        refreshCalendar()
    }

    private fun refreshCalendar() {
        currentDays = buildCalendarDays(currentYearMonth)
        adapter.updateDays(currentDays)
    }

    private fun buildCalendarDays(yearMonth: YearMonth): List<CalendarDay> {
        val days = mutableListOf<CalendarDay>()

        val primeiroDia = yearMonth.atDay(1)
        val offset = primeiroDia.dayOfWeek.value % 7

        repeat(offset) {
            days.add(CalendarDay(date = null, dayNumber = "", isCurrentMonth = false))
        }

        for (num in 1..yearMonth.lengthOfMonth()) {
            val date = yearMonth.atDay(num)

            val isStart = date == startDate
            val isEnd = date == endDate
            val inRange = startDate != null && endDate != null
                    && date.isAfter(startDate) && date.isBefore(endDate)

            days.add(
                CalendarDay(
                    date = date,
                    dayNumber = num.toString(),
                    isCurrentMonth = true,
                    isReserved = date in reservedDates,
                    isSelected = isStart || isEnd,
                    isRangeStart = isStart,
                    isRangeEnd = isEnd,
                    isInRange = inRange
                )
            )
        }

        return days
    }

    // ─── Confirmação com API ─────────────────────────────────────────────────

    private fun confirmarExtensao(btnConfirmar: View) {
        val dataFim = endDate ?: startDate

        if (dataFim == null) {
            Toast.makeText(this, "Selecione a nova data de devolução", Toast.LENGTH_SHORT).show()
            return
        }

        if (emprestimoId == -1L) {
            Toast.makeText(this, "Empréstimo inválido.", Toast.LENGTH_SHORT).show()
            return
        }

        btnConfirmar.isEnabled = false

        val dto = EstenderEmprestimoDTO(fimEmprestimo = dataFim.toString())

        RetrofitClient.instance.estender(emprestimoId, dto)
            .enqueue(object : Callback<EmprestimoResponseDTO> {
                override fun onResponse(
                    call: Call<EmprestimoResponseDTO>,
                    response: Response<EmprestimoResponseDTO>
                ) {
                    if (response.isSuccessful) {
                        showSuccessOverlay()
                    } else {
                        Toast.makeText(
                            this@EstenderEmprestimoActivity,
                            "Erro ao estender (${response.code()}).",
                            Toast.LENGTH_LONG
                        ).show()
                        btnConfirmar.isEnabled = true
                    }
                }

                override fun onFailure(call: Call<EmprestimoResponseDTO>, t: Throwable) {
                    Toast.makeText(
                        this@EstenderEmprestimoActivity,
                        "Falha de conexão.",
                        Toast.LENGTH_LONG
                    ).show()
                    btnConfirmar.isEnabled = true
                }
            })
    }

    private fun showSuccessOverlay() {
        val overlay = findViewById<View>(R.id.successOverlay)
        val imagem = findViewById<ImageView>(R.id.imgSuccess)
        val texto = findViewById<TextView>(R.id.tvSuccessLabel)

        overlay.alpha = 0f
        imagem.scaleX = 0f
        imagem.scaleY = 0f
        imagem.alpha = 0f
        texto.alpha = 0f

        overlay.visibility = View.VISIBLE

        overlay.animate().alpha(1f).setDuration(220).start()

        imagem.animate()
            .scaleX(1f).scaleY(1f).alpha(1f)
            .setStartDelay(120)
            .setDuration(450)
            .setInterpolator(OvershootInterpolator(1.8f))
            .start()

        texto.animate()
            .alpha(1f)
            .setStartDelay(400)
            .setDuration(300)
            .start()

        overlay.postDelayed({ finish() }, 2000)
    }
}
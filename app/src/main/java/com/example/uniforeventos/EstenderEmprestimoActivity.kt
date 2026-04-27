package com.example.uniforeventos

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

class EstenderEmprestimoActivity : AppCompatActivity() {

    private var startDate: LocalDate? = null
    private var endDate: LocalDate? = null

    private lateinit var adapter: CalendarAdapter
    private var currentDays: List<CalendarDay> = emptyList()
    private var currentYearMonth: YearMonth = YearMonth.now()

    // 🔧 Substituir por datas reais vindas da API
    private val reservedDates: Set<LocalDate> = setOf(
        LocalDate.of(2026, 4, 28),
        LocalDate.of(2026, 4, 29)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_estender_emprestimo)

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

        btnConfirmar.setOnClickListener { confirmarExtensao() }
    }

    // ─── Lógica de seleção de período ───────────────────────────────────────

    private fun handleDayClick(day: CalendarDay) {
        val date = day.date ?: return
        val s = startDate
        val e = endDate

        when {
            // Período já completo ou clicou no início → reinicia seleção
            e != null || date == s -> {
                startDate = date
                endDate = null
            }

            // Nenhuma data selecionada ainda
            s == null -> {
                startDate = date
            }

            // Clicou antes do início → novo início
            date.isBefore(s) -> {
                startDate = date
                endDate = null
            }

            // Clicou após o início → tenta fechar o período
            else -> {
                val temReservadoNoMeio = reservedDates.any { reserved ->
                    reserved.isAfter(s) && reserved.isBefore(date)
                }

                if (temReservadoNoMeio) {
                    Toast.makeText(
                        this,
                        "O período contém datas reservadas",
                        Toast.LENGTH_SHORT
                    ).show()
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

            val isStart  = date == startDate
            val isEnd    = date == endDate
            val inRange  = startDate != null && endDate != null
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

    // ─── Confirmação ────────────────────────────────────────────────────────

    private fun confirmarExtensao() {
        if (startDate == null || endDate == null) {
            Toast.makeText(this, "Selecione o período de extensão", Toast.LENGTH_SHORT).show()
            return
        }

        // 🔧 Substituir pelo payload real da API:
        // val body = ExtensaoRequest(livroId, startDate.toString(), endDate.toString())
        // viewModel.estenderEmprestimo(body)

        Handler(Looper.getMainLooper()).postDelayed({
            showSuccessOverlay()
        }, 800)
    }

    private fun showSuccessOverlay() {
        val overlay = findViewById<View>(R.id.successOverlay)
        val imagem  = findViewById<ImageView>(R.id.imgSuccess)
        val texto   = findViewById<TextView>(R.id.tvSuccessLabel)

        overlay.alpha = 0f
        imagem.scaleX = 0f
        imagem.scaleY = 0f
        imagem.alpha  = 0f
        texto.alpha   = 0f

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

        Handler(Looper.getMainLooper()).postDelayed({ finish() }, 2000)
    }
}
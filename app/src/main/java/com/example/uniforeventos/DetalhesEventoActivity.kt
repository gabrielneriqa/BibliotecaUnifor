package com.example.uniforeventos

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.lifecycleScope
import com.example.uniforeventos.model.EventoResponse
import com.example.uniforeventos.repository.EventoRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class DetalhesEventoActivity : AppCompatActivity() {

    private lateinit var imagemEvento: ImageView
    private lateinit var botaoVoltar: AppCompatImageView
    private lateinit var textoTituloEvento: TextView
    private lateinit var textoDataEvento: TextView
    private lateinit var textoHorarioEvento: TextView
    private lateinit var textoLocalEvento: TextView
    private lateinit var textoEnderecoEvento: TextView
    private lateinit var imagemOrganizador: ImageView
    private lateinit var textoNomeOrganizador: TextView
    private lateinit var textoCargoOrganizador: TextView
    private lateinit var textoDescricaoEvento: TextView

    private val eventoRepository = EventoRepository()

    private var descricaoCompleta: String = ""
    private var eventoId: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_evento)

        inicializarViews()
        configurarCliqueDoBotaoVoltar()
        preencherDadosDoEventoRecebidosPorIntent()
        carregarEventoPorId()
        confirmarPresenca()
        configurarBottomNav()
    }

    private fun inicializarViews() {
        imagemEvento = findViewById(R.id.ivImagemEvento)
        botaoVoltar = findViewById(R.id.ivVoltar)
        textoTituloEvento = findViewById(R.id.tvTituloEvento)
        textoDataEvento = findViewById(R.id.tvDataEvento)
        textoHorarioEvento = findViewById(R.id.tvHorarioEvento)
        textoLocalEvento = findViewById(R.id.tvLocalEvento)
        textoEnderecoEvento = findViewById(R.id.tvEnderecoEvento)
        imagemOrganizador = findViewById(R.id.ivOrganizador)
        textoNomeOrganizador = findViewById(R.id.tvNomeOrganizador)
        textoCargoOrganizador = findViewById(R.id.tvCargoOrganizador)
        textoDescricaoEvento = findViewById(R.id.tvDescricaoEvento)
    }

    private fun configurarCliqueDoBotaoVoltar() {
        botaoVoltar.setOnClickListener {
            finish()
        }
    }

    private fun preencherDadosDoEventoRecebidosPorIntent() {
        eventoId = intent.getLongExtra(EXTRA_EVENTO_ID, -1L)

        val titulo = intent.getStringExtra(EXTRA_TITULO).orEmpty()
        val dataCompleta = intent.getStringExtra(EXTRA_DATA_COMPLETA).orEmpty()
        val horario = intent.getStringExtra(EXTRA_HORARIO).orEmpty()
        val local = intent.getStringExtra(EXTRA_LOCAL).orEmpty()
        val endereco = intent.getStringExtra(EXTRA_ENDERECO).orEmpty()
        val organizadorNome = intent.getStringExtra(EXTRA_ORGANIZADOR_NOME).orEmpty()
        val organizadorCargo = intent.getStringExtra(EXTRA_ORGANIZADOR_CARGO).orEmpty()
        val descricao = intent.getStringExtra(EXTRA_DESCRICAO).orEmpty()
        val imagemEventoResId = intent.getIntExtra(EXTRA_IMAGEM_EVENTO_RES_ID, 0)
        val imagemOrganizadorResId = intent.getIntExtra(EXTRA_IMAGEM_ORGANIZADOR_RES_ID, 0)

        textoTituloEvento.text = titulo
        textoDataEvento.text = dataCompleta
        textoHorarioEvento.text = horario
        textoLocalEvento.text = local
        textoEnderecoEvento.text = endereco
        textoNomeOrganizador.text = organizadorNome
        textoCargoOrganizador.text = organizadorCargo

        if (imagemEventoResId != 0) {
            imagemEvento.setImageResource(imagemEventoResId)
        } else {
            imagemEvento.setImageResource(R.drawable.img_evento_placeholder)
        }

        if (imagemOrganizadorResId != 0) {
            imagemOrganizador.setImageResource(imagemOrganizadorResId)
        } else {
            imagemOrganizador.setImageResource(R.drawable.img_avatar_1)
        }

        descricaoCompleta = descricao
        configurarDescricaoExpandivel()
    }

    private fun carregarEventoPorId() {
        if (eventoId <= 0L) {
            Toast.makeText(
                this,
                "Evento não encontrado.",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        lifecycleScope.launch {
            try {
                val eventoResponse = eventoRepository.buscarEventoPorId(eventoId)
                preencherDadosDoEventoDaApi(eventoResponse)
            } catch (e: Exception) {
                Toast.makeText(
                    this@DetalhesEventoActivity,
                    "Erro ao carregar detalhes do evento: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun preencherDadosDoEventoDaApi(evento: EventoResponse) {
        textoTituloEvento.text = evento.titulo
        textoDataEvento.text = formatarDataCompleta(evento.dataInicio)
        textoHorarioEvento.text = evento.horario
        textoLocalEvento.text = evento.local ?: "Local não informado"
        textoEnderecoEvento.text = evento.endereco ?: "Endereço não informado"

        textoNomeOrganizador.text = "UNIFOR"
        textoCargoOrganizador.text = "Organizador"

        imagemEvento.setImageResource(R.drawable.img_evento_placeholder)
        imagemOrganizador.setImageResource(R.drawable.img_avatar_1)

        descricaoCompleta = evento.descricao ?: ""
        configurarDescricaoExpandivel()
    }

    private fun formatarDataCompleta(dataInicio: String): String {
        val data = runCatching {
            LocalDate.parse(dataInicio)
        }.getOrNull()

        return data?.format(
            DateTimeFormatter.ofPattern("dd 'de' MMMM, yyyy", Locale("pt", "BR"))
        ) ?: dataInicio
    }

    private fun configurarDescricaoExpandivel() {
        textoDescricaoEvento.highlightColor = Color.TRANSPARENT
        textoDescricaoEvento.movementMethod = LinkMovementMethod.getInstance()

        if (descricaoCompleta.length <= LIMITE_DESCRICAO_COLAPSADA) {
            textoDescricaoEvento.text = descricaoCompleta
            return
        }

        aplicarDescricaoColapsada()
    }

    private fun aplicarDescricaoColapsada() {
        val descricaoResumida = descricaoCompleta
            .take(LIMITE_DESCRICAO_COLAPSADA)
            .trimEnd()

        val textoFinal = "$descricaoResumida... Ler mais..."
        val spannableString = SpannableString(textoFinal)

        val inicioClique = textoFinal.indexOf("Ler mais...")
        val fimClique = textoFinal.length

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                aplicarDescricaoExpandida()
            }

            override fun updateDrawState(textPaint: TextPaint) {
                textPaint.color = Color.parseColor("#FF8D8D")
                textPaint.isUnderlineText = false
            }
        }

        spannableString.setSpan(
            clickableSpan,
            inicioClique,
            fimClique,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        textoDescricaoEvento.text = spannableString
    }

    private fun aplicarDescricaoExpandida() {
        val textoFinal = "$descricaoCompleta Ler menos..."
        val spannableString = SpannableString(textoFinal)

        val inicioClique = textoFinal.indexOf("Ler menos...")
        val fimClique = textoFinal.length

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                aplicarDescricaoColapsada()
            }

            override fun updateDrawState(textPaint: TextPaint) {
                textPaint.color = Color.parseColor("#FF8D8D")
                textPaint.isUnderlineText = false
            }
        }

        spannableString.setSpan(
            clickableSpan,
            inicioClique,
            fimClique,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        textoDescricaoEvento.text = spannableString
    }

    private fun confirmarPresenca() {
        findViewById<MaterialCardView>(R.id.btnConfirmarPresencaDetalhes).setOnClickListener {
            val intent = Intent(this, ConfirmarPresencaActivity::class.java)
            intent.putExtra(EXTRA_EVENTO_ID, eventoId)
            startActivity(intent)
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

    companion object {
        private const val EXTRA_EVENTO_ID = "extra_evento_id"
        private const val EXTRA_TITULO = "extra_titulo"
        private const val EXTRA_DATA_COMPLETA = "extra_data_completa"
        private const val EXTRA_HORARIO = "extra_horario"
        private const val EXTRA_LOCAL = "extra_local"
        private const val EXTRA_ENDERECO = "extra_endereco"
        private const val EXTRA_ORGANIZADOR_NOME = "extra_organizador_nome"
        private const val EXTRA_ORGANIZADOR_CARGO = "extra_organizador_cargo"
        private const val EXTRA_DESCRICAO = "extra_descricao"
        private const val EXTRA_IMAGEM_EVENTO_RES_ID = "extra_imagem_evento_res_id"
        private const val EXTRA_IMAGEM_ORGANIZADOR_RES_ID = "extra_imagem_organizador_res_id"

        private const val LIMITE_DESCRICAO_COLAPSADA = 140

        fun abrir(contexto: Context, evento: Evento) {
            val intent = Intent(contexto, DetalhesEventoActivity::class.java).apply {
                putExtra(EXTRA_EVENTO_ID, evento.id)
                putExtra(EXTRA_TITULO, evento.titulo)
                putExtra(EXTRA_DATA_COMPLETA, evento.dataCompleta)
                putExtra(EXTRA_HORARIO, evento.horario)
                putExtra(EXTRA_LOCAL, evento.local)
                putExtra(EXTRA_ENDERECO, evento.endereco)
                putExtra(EXTRA_ORGANIZADOR_NOME, evento.organizadorNome)
                putExtra(EXTRA_ORGANIZADOR_CARGO, evento.organizadorCargo)
                putExtra(EXTRA_DESCRICAO, evento.descricao)
                putExtra(EXTRA_IMAGEM_EVENTO_RES_ID, evento.imagemResId)
                putExtra(EXTRA_IMAGEM_ORGANIZADOR_RES_ID, evento.organizadorImagemResId)
            }

            contexto.startActivity(intent)
        }
    }
}
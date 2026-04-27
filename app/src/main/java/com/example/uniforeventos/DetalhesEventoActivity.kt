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
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView


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

    private var descricaoCompleta: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_evento)

        inicializarViews()
        configurarCliqueDoBotaoVoltar()
        preencherDadosDoEvento()
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

    private fun preencherDadosDoEvento() {
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
        }

        if (imagemOrganizadorResId != 0) {
            imagemOrganizador.setImageResource(imagemOrganizadorResId)
        }

        descricaoCompleta = descricao
        configurarDescricaoExpandivel()
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

    companion object {
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
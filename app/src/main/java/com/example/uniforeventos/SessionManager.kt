package com.example.uniforeventos

import android.content.Context

object SessionManager {

    private const val PREF_NAME = "unifor_session"
    private const val KEY_USER_ID = "usuario_id"
    private const val KEY_USER_NOME = "usuario_nome"

    fun salvarSessao(context: Context, usuarioId: Long, nome: String) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit()
            .putLong(KEY_USER_ID, usuarioId)
            .putString(KEY_USER_NOME, nome)
            .apply()
    }

    fun getUsuarioId(context: Context): Long {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getLong(KEY_USER_ID, -1L)
    }

    fun getUsuarioNome(context: Context): String {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getString(KEY_USER_NOME, "Usuário") ?: "Usuário"
    }

    fun limparSessao(context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit()
            .clear()
            .apply()
    }
}
package com.example.uniforeventos

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class NotificationActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notificacao_check)
        limparNotificacaoo()
    }

    private fun limparNotificacaoo(){
        findViewById<ImageView>(R.id.checkStatus).setOnClickListener {
            setContentView(R.layout.activity_notificacao_vazia)
        }
    }
}
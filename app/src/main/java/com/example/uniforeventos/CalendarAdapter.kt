package com.example.uniforeventos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class CalendarAdapter(
    private var days: List<CalendarDay>,
    private val onDayClick: (CalendarDay) -> Unit
) : RecyclerView.Adapter<CalendarAdapter.CalendarDayViewHolder>() {

    inner class CalendarDayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDay: TextView = itemView.findViewById(R.id.tvDay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarDayViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_calendar_day, parent, false)
        return CalendarDayViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarDayViewHolder, position: Int) {
        val item = days[position]
        val context = holder.itemView.context

        holder.tvDay.text = item.dayNumber

        when {
            item.date == null -> {
                holder.tvDay.text = ""
                holder.tvDay.background = null
                holder.tvDay.visibility = View.INVISIBLE
                holder.itemView.isClickable = false
                holder.itemView.isEnabled = false
            }

            item.isReserved -> {
                holder.tvDay.visibility = View.VISIBLE
                holder.tvDay.setTextColor(ContextCompat.getColor(context, android.R.color.white))
                holder.tvDay.setBackgroundResource(R.drawable.bg_calendar_day_red)
                holder.itemView.isClickable = false
                holder.itemView.isEnabled = false
            }

            item.isSelected -> {
                // Início ou fim do período — círculo azul
                holder.tvDay.visibility = View.VISIBLE
                holder.tvDay.setTextColor(ContextCompat.getColor(context, android.R.color.white))
                holder.tvDay.setBackgroundResource(R.drawable.bg_calendar_day_blue)
                holder.itemView.isClickable = true
                holder.itemView.isEnabled = true
            }

            item.isInRange -> {
                // Dias dentro do período — fundo azul claro, texto escuro
                holder.tvDay.visibility = View.VISIBLE
                holder.tvDay.setTextColor(ContextCompat.getColor(context, R.color.calendar_day_normal))
                holder.tvDay.setBackgroundResource(R.drawable.bg_calendar_range)
                holder.itemView.isClickable = true
                holder.itemView.isEnabled = true
            }

            else -> {
                holder.tvDay.visibility = View.VISIBLE
                holder.tvDay.setTextColor(ContextCompat.getColor(context, R.color.calendar_day_normal))
                holder.tvDay.background = null
                holder.itemView.isClickable = true
                holder.itemView.isEnabled = true
            }
        }

        val clickAction = {
            if (item.date != null && !item.isReserved) {
                onDayClick(item)
            }
        }

        holder.itemView.setOnClickListener { clickAction() }
        holder.tvDay.setOnClickListener { clickAction() }
    }

    override fun getItemCount(): Int = days.size

    fun updateDays(newDays: List<CalendarDay>) {
        days = newDays
        notifyDataSetChanged()
    }
}
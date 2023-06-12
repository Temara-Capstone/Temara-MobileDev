package com.team.temara.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.team.temara.R

class RecommendedQuestionsAdapter(
    private val questions: List<String>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<RecommendedQuestionsAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(question: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recommend_chat_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val question = questions[position]
        holder.bind(question)
    }

    override fun getItemCount(): Int {
        return questions.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val questionTextView: TextView = itemView.findViewById(R.id.tvRecom)

        fun bind(question: String) {
            questionTextView.text = question

            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedQuestion = questions[position]
                    listener.onItemClick(clickedQuestion)
                }
            }
        }
    }
}

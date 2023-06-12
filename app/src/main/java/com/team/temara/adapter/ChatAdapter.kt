package com.team.temara.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.team.temara.R
import com.team.temara.utils.Message

class ChatAdapter(private val messageList: List<Message>) : RecyclerView.Adapter<ChatAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val chatView: View = LayoutInflater.from(parent.context).inflate(R.layout.chat_bubble_item, parent, false)
        return MyViewHolder(chatView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val message: Message = messageList[position]
        if (message.getSentBy() == Message.SEND_BY_ME) {
            holder.leftChatTextView.visibility = View.GONE
            holder.rightChatTextView.visibility = View.VISIBLE
            holder.rightChatTextView.text = message.getMessage()
        } else {
            holder.rightChatTextView.visibility = View.GONE
            holder.leftChatTextView.visibility = View.VISIBLE
            holder.leftChatTextView.text = message.getMessage()
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var leftChatTextView: TextView = itemView.findViewById(R.id.tvChat)
        var rightChatTextView: TextView = itemView.findViewById(R.id.tvChatResponse)
    }
}

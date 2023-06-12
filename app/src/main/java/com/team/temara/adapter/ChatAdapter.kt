package com.team.temara.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.team.temara.R
import com.team.temara.utils.Message
import de.hdodenhof.circleimageview.CircleImageView

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
            holder.leftUserImageView.visibility = View.GONE
            holder.rightUserImageView.visibility = View.VISIBLE
            holder.rightChatTextView.text = message.getMessage()
            holder.rightUserImageView.setImageResource(R.drawable.default_image)
        } else {
            holder.rightChatTextView.visibility = View.GONE
            holder.leftChatTextView.visibility = View.VISIBLE
            holder.rightUserImageView.visibility = View.GONE
            holder.leftUserImageView.visibility = View.VISIBLE
            holder.leftChatTextView.text = message.getMessage()
            // Tampilkan gambar pengguna (user)
            holder.leftUserImageView.setImageResource(R.drawable.ara) // Ganti dengan gambar pengguna yang sesuai
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var leftChatTextView: TextView = itemView.findViewById(R.id.tvChat)
        var rightChatTextView: TextView = itemView.findViewById(R.id.tvChatResponse)
        var leftUserImageView: CircleImageView = itemView.findViewById(R.id.ivUser)
        var rightUserImageView: CircleImageView = itemView.findViewById(R.id.ivUserResponse)
    }
}

package com.team.temara.utils

class Message(private var message: String, private var sendBy: String) {

    companion object {
        const val SEND_BY_ME = "me"
        const val SEND_BY_BOT = "bot"
    }

    fun getMessage(): String {
        return message
    }

    fun setMessage(message: String) {
        this.message = message
    }

    fun getSentBy(): String {
        return sendBy
    }

    fun setSentBy(sendBy: String) {
        this.sendBy = sendBy
    }
}
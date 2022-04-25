package com.sivva.daredevil

import android.os.Bundle
import com.kevin.devil.Devil
import com.kevin.devil.models.DevilMessage
import kotlinx.android.synthetic.main.activity_messaging.*
import timber.log.Timber
import java.lang.Exception

class MessagingActivity : BaseActivity() {

    lateinit var messageAdapter: MessageAdapter
    lateinit var messageList:List<DevilMessage>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messaging)

        messageList = intent.getSerializableExtra("list") as List<DevilMessage>

        messageAdapter = MessageAdapter(this, messageList)
        messages.adapter = messageAdapter
        Timber.e(" size " +messageList.size )
        messageAdapter.updateList(messageList)
        try {
            messages.smoothScrollToPosition(messageAdapter.itemCount)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        sendMessage.setOnClickListener {
            if (messageEt.text.isNotEmpty()) {
                Devil.getHermes().sendMessage(
                    DevilMessage(
                        "user",
                        messageEt.text.toString(),
                        System.currentTimeMillis(),
                        0L,
                        false,
                        false
                    )
                )
                messageEt.setText("")
            } else
                messageEt.error = "Please enter message to send"
        }
    }

    override fun updateLog(topic: String, payload: String) {
//
    }

    override fun onPause() {
        super.onPause()
        val locStr = gson.toJson(messageAdapter.list)
        val prefs = this.getSharedPreferences("list", MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("data", locStr)
        editor.apply()
    }

    override fun updateMessage(topic: String, payload: String) {
        messageAdapter.add(gson.fromJson(payload,DevilMessage::class.java))
        Timber.e(" size " +messageList.size )
        try {
            messages.smoothScrollToPosition(messageAdapter.itemCount)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}
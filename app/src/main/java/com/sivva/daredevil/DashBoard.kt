package com.sivva.daredevil

import adapters.KAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.reflect.TypeToken
import com.kevin.devil.models.DevilMessage
import kotlinx.android.synthetic.main.activity_dash_board.*
import models.LogMessage
import java.lang.reflect.Type

class DashBoard : BaseActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: KAdapter
    private val messages: ArrayList<LogMessage> = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash_board)
        recyclerView = findViewById(R.id.rv)
        message.setOnClickListener {
            val switcher = Intent(this, MessagingActivity::class.java)
            switcher.putExtra("list", app.userMessages)
            startActivity(switcher)
        }
        stats.setOnClickListener {
            val switcher = Intent(this, StatsActivity::class.java)
            startActivity(switcher)
        }
        adapter = KAdapter(messages, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        message.text = app.userMessages.count().toString()
    }

    override fun updateLog(topic: String, payload: String) {
        update(topic, payload)
    }

    override fun updateMessage(topic: String, payload: String) {
        app.userMessages.add(gson.fromJson((payload), DevilMessage::class.java))
        message.text = app.userMessages.size.toString()
        message.invalidate()
        message.requestLayout()
        if (!isShowing)
            showSubjectDialog()
    }
//pushing to tamucc

    private fun update(topic: String, message: String) {
        recyclerView.scrollToPosition(messages.size - 1)
        when {
            topic.contains(DEBUG) -> messages.add(LogMessage(DEBUG, message))
            topic.contains(
                ERROR
            ) -> messages.add(
                LogMessage(
                    ERROR,
                    message
                )
            )
            topic.contains(FAILED_REQUEST) -> messages.add(
                LogMessage(
                    FAILED_REQUEST,
                    message
                )
            )
            topic.contains(EXCEPTION) -> messages.add(
                LogMessage(
                    EXCEPTION,
                    message
                )
            )
            topic.contains(ALIVE) -> messages.add(
                LogMessage(
                    ALIVE,
                    message
                )
            )
            topic.contains(REQUEST) -> messages.add(
                LogMessage(
                    REQUEST,
                    message
                )
            )
            topic.contains(DEAD) -> messages.add(LogMessage(DEAD, message))
        }
        adapter.notifyDataSetChanged()
        recyclerView.smoothScrollToPosition(messages.size - 1)
    }


    override fun onResume() {
        super.onResume()
        isShowing = false
        app.userMessages = fetchPreviousMessages()
        message.text = app.userMessages.count().toString()
    }

    private fun fetchPreviousMessages(): ArrayList<DevilMessage> {
        return try {
            val prefs = this.getSharedPreferences("list", MODE_PRIVATE)
            val json = prefs.getString("data", "")
            val type: Type = object : TypeToken<ArrayList<DevilMessage>>() {}.type
            gson.fromJson(json, type)
        } catch (e: java.lang.Exception) {
            arrayListOf()
        }
    }

    override fun onPause() {
        super.onPause()
        val locStr = gson.toJson(app.userMessages)
        val prefs = this.getSharedPreferences("list", MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("data", locStr)
        editor.apply()
    }

    private var isShowing = false

    private fun showSubjectDialog() {
        isShowing = true
        val dialog = BottomSheetDialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val view: View = LayoutInflater.from(this).inflate(R.layout.new_message_dialog, null)

        val primabutton = view.findViewById<Button>(R.id.primaryButton)
        primabutton?.setOnClickListener {
            dialog.dismiss()
            val switcher = Intent(this, MessagingActivity::class.java)
            switcher.putExtra("list", app.userMessages)
            startActivity(switcher)
        }
        val secondary = view.findViewById<Button>(R.id.secondaryButton)
        secondary?.setOnClickListener {
            dialog.dismiss()
            isShowing = false
        }
        dialog.setContentView(view)
        dialog.setCancelable(false)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.show()
    }

}



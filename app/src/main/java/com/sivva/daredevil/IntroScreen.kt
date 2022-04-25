package com.sivva.daredevil

import adapters.IntroRecyclerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.kevin.devil.DevilShouter
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import models.IntroModel

class IntroScreen : AppCompatActivity() {

    private lateinit var viewPager2: ViewPager2
    private lateinit var dotsIndicator: WormDotsIndicator
    private lateinit var adapter: IntroRecyclerAdapter
    private val messages: ArrayList<IntroModel> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_screen)
        DevilShouter.scream(this, "This is a scream from library..")
        val introModel =
            IntroModel(R.drawable.daredevil, "I cannot see you, but I can Sense you.", "DareDevil")
        val introModel1 =
            IntroModel(R.drawable.flash, "Live logs faster than The Flash.", "Speed")
        val introModel2 =
            IntroModel(R.drawable.jenkins, "A Supervisor to your Jenkins", "Jenkins (Ci-Cd)")

        messages.add(introModel)
        messages.add(introModel1)
        messages.add(introModel2)
        messages.add(IntroModel(R.drawable.kube, "A Compass to your Kubernetes.", "Kubernetes"))
        messages.add(
            IntroModel(
                R.drawable.dock,
                "A orchestrator to your Docker Swarm.",
                "Docker Swarm"
            )
        )
        messages.add(IntroModel(R.drawable.hermes, "A fast and reliable MailMan.", "Hermes"))
        messages.add(
            IntroModel(
                R.drawable.wiki,
                "Your private Log, Bug, Error Wikipedia.",
                "Log Wikipedia"
            )
        )
        messages.add(
            IntroModel(
                R.drawable.console,
                "Your private remote Log Console.",
                "Remote Log Console"
            )
        )
//        messages.add(IntroModel(R.drawable.console, "\"I am a very good SDK\"", "But how?"))
//        messages.add(IntroModel(R.drawable.console, "How do you do that? \n \"I am a very good SDK\"", "Win the game by a mile."))
//        messages.add(IntroModel(R.drawable.money, "An answer to the prodigal server bills.", "Server bill"))

        viewPager2 = findViewById(R.id.pager)
        adapter = IntroRecyclerAdapter()
        viewPager2.adapter = adapter
        dotsIndicator = findViewById(R.id.worm_dots_indicator)
        dotsIndicator.setViewPager2(viewPager2)
        adapter.updateList(messages)


    }

    fun continueToDashBoard(view: View) {
        startActivity(Intent(this, DashBoard::class.java))
        finish()
    }
}
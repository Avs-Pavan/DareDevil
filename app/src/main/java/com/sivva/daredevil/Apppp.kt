package com.sivva.daredevil

import android.app.Application
import com.kevin.devil.Devil
import com.kevin.devil.models.DevilConfig
import com.kevin.devil.models.DevilMessage
import timber.log.Timber

class Apppp : Application() {
    private val serverUri = "tcp://134.209.144.25:1883"
    public var userMessages:  ArrayList<DevilMessage> = arrayListOf()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Devil.breath(
            DevilConfig(
                true,
                true,
                serverUri,
                applicationContext,
                "Tag",
                "Will_topic",
                "123456"
            )
        )
    }
}
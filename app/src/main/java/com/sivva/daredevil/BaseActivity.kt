package com.sivva.daredevil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.kevin.devil.models.DevilMessage
import models.LogMessage
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import java.lang.Exception

abstract class BaseActivity : AppCompatActivity() {
    lateinit var mqttAndroidClient: MqttAndroidClient
    val serverUri = "tcp://134.209.144.25:1883"
    var clientId = "daredevil"
    private val subscriptionTopic = "D/+"
    val gson: Gson = Gson()
    val app :Apppp = Apppp()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        initDevl()
    }

    private fun initDevl() {
        clientId += System.currentTimeMillis()
        mqttAndroidClient = MqttAndroidClient(applicationContext, serverUri, clientId)
        mqttAndroidClient.setCallback(object : MqttCallbackExtended {
            override fun connectComplete(reconnect: Boolean, serverURI: String) {
                if (reconnect) {
                    log("Reconnected to : $serverURI")
                    // Because Clean Session is true, we need to re-subscribe
                    subscribeToTopic()
                } else {
                    // publishMessage();
                    log("Connected to: $serverURI")

                }
            }

            override fun connectionLost(cause: Throwable) {
                log("The Connection was lost.")
            }

            override fun messageArrived(topic: String, message: MqttMessage) {
                log("Incoming message: " + String(message.payload))
            }

            override fun deliveryComplete(token: IMqttDeliveryToken) {}
        })
        val mqttConnectOptions = MqttConnectOptions()
        mqttConnectOptions.isAutomaticReconnect = true
        mqttConnectOptions.isCleanSession = false
        try {
            mqttAndroidClient.connect(mqttConnectOptions, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    val disconnectedBufferOptions = DisconnectedBufferOptions()
                    disconnectedBufferOptions.isBufferEnabled = true
                    disconnectedBufferOptions.bufferSize = 100
                    disconnectedBufferOptions.isPersistBuffer = false
                    disconnectedBufferOptions.isDeleteOldestMessages = false
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions)
                    subscribeToTopic()
                }

                override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                    log("Failed to connect to: $serverUri")
                }
            })
        } catch (ex: MqttException) {
            ex.printStackTrace()
        }
    }


    val DEBUG = "DEBUG"
    val ERROR = "ERROR"
    val REQUEST = "REQUEST"
    val FAILED_REQUEST = "FAILED_REQUEST"
    val EXCEPTION = "EXCEPTION"
    val ALIVE = "ALIVE"
    val DEAD = "DEAD"

    private fun subscribeToTopic() {
        try {
            mqttAndroidClient.subscribe(
                subscriptionTopic, 1
            ) { topic: String, message: MqttMessage ->
                // message Arrived!
                runOnUiThread {
                    updateLog(topic, String(message.payload))
                }
            }
            mqttAndroidClient.subscribe(
                "M/+", 1
            ) { topic: String, message: MqttMessage ->
                // message Arrived!
                runOnUiThread {
                    updateMessage(topic, String(message.payload))
                }
            }
        } catch (ex: Exception) {

        } catch (ex: MqttException) {
            System.err.println("Exception whilst subscribing")
            ex.printStackTrace()
        }
    }

    fun log(message: String) {
        Log.e("Mqtt client ", message)
    }

    fun shout(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    abstract fun updateLog(topic: String, payload: String)

    abstract fun updateMessage(topic: String, payload: String)

}
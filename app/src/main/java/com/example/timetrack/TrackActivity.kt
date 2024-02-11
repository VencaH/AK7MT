package com.example.timetrack

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TrackActivity: AppCompatActivity() {
    private lateinit var history: Button
    private lateinit var timer: TextView
    private lateinit var start: Button
    private lateinit var reset: Button
    private var timerRunning: Boolean = false
    private lateinit var serviceIntent: Intent
    private var time: Int = 0
//    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track)
        val title = intent.getExtras()?.getString("task_name")
        val titleText: TextView = findViewById(R.id.taskName)
        titleText.text = title

        timer = findViewById(R.id.timeViewer)
        timer.text = timeFormatter(0)

        start = findViewById(R.id.startButton)
        reset = findViewById(R.id.resetButton)

        start.setOnClickListener {
            if (timerRunning) {
                stopService(serviceIntent)
                start.text = "Start"
                timerRunning = false
            } else {
                serviceIntent.putExtra(StopwatchService.TIME_EXTRA, time)
                startService(serviceIntent)
                start.text = "Stop"
                timerRunning = true
            }
        }
        reset.setOnClickListener {
            time = 0
            timer.text = timeFormatter(0)
        }

        serviceIntent = Intent(applicationContext, StopwatchService::class.java)
        registerReceiver(updateTime, IntentFilter(StopwatchService.TIMER_UPDATED),
            RECEIVER_EXPORTED)


        history = findViewById(R.id.historyBotton)
        history.setOnClickListener {
            val intent = Intent(this@TrackActivity, HistoryActivity::class.java)
            val bundle = Bundle()
            bundle.putString("task_name", title)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }


    private val updateTime: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
           time = intent.getIntExtra(StopwatchService.TIME_EXTRA, 0)
            timer.text = timeFormatter(time)
        }

    }


    private fun timeFormatter(time: Int): String {
        val hours: Int =  (time / 3600).toInt()
        val minutes: Int = ((time % 3600) / 60).toInt()
        val seconds: Int = (time % 60).toInt()
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}
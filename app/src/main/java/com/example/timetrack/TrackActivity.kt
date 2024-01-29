package com.example.timetrack

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TrackActivity: AppCompatActivity() {
    private lateinit var history: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track)
        val title = intent.getExtras()?.getString("task_name")
        val titleText: TextView = findViewById(R.id.taskName)
        titleText.text = title

        history = findViewById(R.id.historyBotton)
        history.setOnClickListener {
            val intent = Intent(this@TrackActivity, HistoryActivity::class.java)
            val bundle = Bundle()
            bundle.putString("task_name", title)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }
}
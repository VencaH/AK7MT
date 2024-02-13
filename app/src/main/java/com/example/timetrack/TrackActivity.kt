package com.example.timetrack

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.timetrack.databinding.ActivityTrackBinding

class TrackActivity: AppCompatActivity() {
   private lateinit var serviceIntent: Intent
    private lateinit var binding: ActivityTrackBinding
    private lateinit var viewModel: TrackActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrackBinding.inflate(layoutInflater)
        val app = application as ApiHandler
        viewModel = ViewModelProvider(this, TrackActivityViewModelFactory(app.repository)).get(TrackActivityViewModel::class.java)
        binding.trackViewModel = viewModel
        binding.lifecycleOwner = this
        val view = binding.root
        setContentView(view)
        val title = intent.extras?.getString("task_name")
        val id = intent.extras?.getString("id")
        viewModel.title.value = title
       viewModel.idCard.value = id.orEmpty()
        viewModel.response.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
        viewModel.stopwatch.observe(this
        ) {
            if (it == "start") {
                serviceIntent.putExtra(StopwatchService.TIME_EXTRA, viewModel.time.value)
                startService(serviceIntent)
                viewModel.stopwatch.value = null
            } else if (it == "stop") {
                stopService(serviceIntent)
                viewModel.stopwatch.value = null
            }
        }


        serviceIntent = Intent(applicationContext, StopwatchService::class.java)
        registerReceiver(updateTime, IntentFilter(StopwatchService.TIMER_UPDATED),
            RECEIVER_EXPORTED)

        binding.trackHome.setOnClickListener {
            val intent = Intent(this@TrackActivity, MainActivity::class.java)
            startActivity(intent)
        }

        binding.historyBotton.setOnClickListener {
            val intent = Intent(this@TrackActivity, HistoryActivity::class.java)
            val bundle = Bundle()
            bundle.putString("task_name", title)
            bundle.putString("id", id)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }


    private val updateTime: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
           val time = intent.getIntExtra(StopwatchService.TIME_EXTRA, 0)
            binding.trackViewModel!!.time.postValue(timeFormatter(time))

        }

    }


    private fun timeFormatter(time: Int): String {
        val hours: Int = (time / 3600)
        val minutes: Int = ((time % 3600) / 60)
        val seconds: Int = (time % 60)
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}
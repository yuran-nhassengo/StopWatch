package com.lamarck.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import com.lamarck.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

   // lateinit var stopwatch: Chronometer
    var running =false
    var offset: Long = 0

    val OFFSET_KEY ="offset"
    val RUNNING_KEY ="running"
    val BASE_KEY ="base"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

       // stopwatch = findViewById(R.id.stopwatch)
      //  val startButton = findViewById<Button>(R.id.start_button)

        if(savedInstanceState !=null){
            offset = savedInstanceState.getLong(OFFSET_KEY)
            running = savedInstanceState.getBoolean(RUNNING_KEY)

            if(running){

                binding.stopwatch.base = savedInstanceState.getLong(BASE_KEY)
                binding.stopwatch.start()
            }else setBaseTime()

        }

        binding.startButton.setOnClickListener {

            if(!running){
                setBaseTime()
                binding.stopwatch.start()
                running =true
            }
        }

        val pauseButton = findViewById<Button>(R.id.pause_button)
        pauseButton.setOnClickListener {

            if(running){
                saveoffset()
                binding.stopwatch.stop()
                running = false
            }
        }

        val resetButton = findViewById<Button>(R.id.reset_button)
        resetButton.setOnClickListener {
            offset =0
            setBaseTime()
        }


    }

    private fun saveoffset() {

        offset = SystemClock.elapsedRealtime()-binding.stopwatch.base

    }

    private fun setBaseTime() {
        binding.stopwatch.base =SystemClock.elapsedRealtime()-0
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putLong(OFFSET_KEY,offset)
        outState.putBoolean(RUNNING_KEY,running)
        outState.putLong(BASE_KEY,binding.stopwatch.base)
        super.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()
        if (running){
            saveoffset()
            binding.stopwatch.stop()
        }
    }

    override fun onRestart() {
        super.onRestart()
        setBaseTime()
        binding.stopwatch.start()
        offset=0
    }

    override fun onPause() {
        super.onPause()
        if(running){
            saveoffset()
            binding.stopwatch.stop()
        }
    }

    override fun onResume() {
        super.onResume()
        setBaseTime()
        binding.stopwatch.start()
        offset=0
    }
}
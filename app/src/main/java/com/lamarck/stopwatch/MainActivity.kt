package com.lamarck.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer

class MainActivity : AppCompatActivity() {

    lateinit var stopwatch: Chronometer
    var running =false
    var offset: Long = 0

    val OFFSET_KEY ="offset"
    val RUNNING_KEY ="running"
    val BASE_KEY ="base"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        stopwatch = findViewById(R.id.stopwatch)
        val startButton = findViewById<Button>(R.id.start_button)

        if(savedInstanceState !=null){
            offset = savedInstanceState.getLong(OFFSET_KEY)
            running = savedInstanceState.getBoolean(RUNNING_KEY)

            if(running){

                stopwatch.base = savedInstanceState.getLong(BASE_KEY)
                stopwatch.start()
            }else setBaseTime()

        }

        startButton.setOnClickListener {

            if(!running){
                setBaseTime()
                stopwatch.start()
                running =true
            }
        }

        val pauseButton = findViewById<Button>(R.id.pause_button)
        pauseButton.setOnClickListener {

            if(running){
                saveoffset()
                stopwatch.stop()
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

        offset = SystemClock.elapsedRealtime()-stopwatch.base

    }

    private fun setBaseTime() {
        stopwatch.base =SystemClock.elapsedRealtime()-0
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putLong(OFFSET_KEY,offset)
        outState.putBoolean(RUNNING_KEY,running)
        outState.putLong(BASE_KEY,stopwatch.base)
        super.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()
        if (running){
            saveoffset()
            stopwatch.stop()
        }
    }

    override fun onRestart() {
        super.onRestart()
        setBaseTime()
        stopwatch.start()
        offset=0
    }

    override fun onPause() {
        super.onPause()
        if(running){
            saveoffset()
            stopwatch.stop()
        }
    }

    override fun onResume() {
        super.onResume()
        setBaseTime()
        stopwatch.start()
        offset=0
    }
}
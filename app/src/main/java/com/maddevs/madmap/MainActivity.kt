package com.maddevs.madmap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import com.maddevs.madmap.map.module.camera.CameraZoom
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        map.initMap(this)

        plus.setOnClickListener {
            map.onChangeCameraPosition(2.0, CameraZoom.Type.PLUS)
        }
        minus.setOnClickListener {
            map.onChangeCameraPosition(2.0, CameraZoom.Type.MINUS)
        }


        map.onChangeCameraPosition(42.878767474194284, 74.61443737149239)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                map.onChangeCameraPosition(progress.toDouble())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }
}
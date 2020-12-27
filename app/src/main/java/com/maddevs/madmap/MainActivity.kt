package com.maddevs.madmap

import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.maddevs.madmap.map.module.camera.CameraZoom
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        plus.setOnClickListener {
            map.onChangeCameraPosition(2.0, CameraZoom.Type.PLUS)
        }
        minus.setOnClickListener {
            map.onChangeCameraPosition(2.0, CameraZoom.Type.MINUS)
        }
    }
}
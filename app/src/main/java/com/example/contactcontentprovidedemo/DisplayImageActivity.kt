package com.example.contactcontentprovidedemo

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_display_image.*

class DisplayImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_image)

        val path = intent.getStringExtra("path")
        val bitmap = BitmapFactory.decodeFile(path)
        imageView.setImageBitmap(bitmap)
    }
}
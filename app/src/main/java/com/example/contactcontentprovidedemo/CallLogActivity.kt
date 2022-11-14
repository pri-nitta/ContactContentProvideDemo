package com.example.contactcontentprovidedemo

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.CallLog
import android.widget.SimpleCursorAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_call_log.*

class CallLogActivity : AppCompatActivity() {

    private var cols =
        listOf(
            CallLog.Calls._ID,
            CallLog.Calls.NUMBER,
            CallLog.Calls.TYPE,
            CallLog.Calls.DURATION
        ).toTypedArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_log)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CALL_LOG
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                Array(1) { Manifest.permission.READ_CALL_LOG },
                101
            )
        } else {
            displayLog()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            displayLog()
    }

    private fun displayLog() {

        val from =
            listOf(
                CallLog.Calls.NUMBER,
                CallLog.Calls.DURATION,
                CallLog.Calls.TYPE
            ).toTypedArray()

        val to = intArrayOf(R.id.textView1, R.id.textView2, R.id.textView3)


        val rs = contentResolver.query(
            CallLog.Calls.CONTENT_URI,
            cols,
            null,
            null,
            "${CallLog.Calls.LAST_MODIFIED} DESC"
        )

        listViewCallLog.adapter =
            SimpleCursorAdapter(applicationContext, R.layout.mylayout, rs, from, to, 0)

    }
}
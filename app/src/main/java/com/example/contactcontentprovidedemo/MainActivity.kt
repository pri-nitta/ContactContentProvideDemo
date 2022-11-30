package com.example.contactcontentprovidedemo

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.SearchView
import android.widget.SimpleCursorAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var cols = listOf(
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER,
        ContactsContract.CommonDataKinds.Phone._ID
        //always include the id colum
    ).toTypedArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                Array(1) { android.Manifest.permission.READ_CONTACTS },
                111
            )
        } else {
            readContact()
        }

        button.setOnClickListener {
            startActivity(Intent(this, CallLogActivity::class.java))
        }

        button2.setOnClickListener {
            startActivity(Intent(this, ReadImagesActivity::class.java))
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            readContact()
    }

    private fun readContact() {
        val from = listOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        ).toTypedArray()

        val to = intArrayOf(android.R.id.text1, android.R.id.text2)

        var rs = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            cols, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        )

        val adapter =
            SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, rs, from, to, 0)
        listview1.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                rs = contentResolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    cols,
                    "${ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} LIKE ?",
                    Array(1){"%$p0%"},
                    //%% in both sides will say to match exactly the content
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                )
                adapter.changeCursor(rs)
                return false
            }
        })

    }
}
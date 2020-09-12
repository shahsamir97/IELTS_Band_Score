package com.test.ieltsmarkstoband

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SpeakingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speaking)
        title = "Speaking"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}
package com.test.ieltsmarkstoband

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

class SpeakingActivity : AppCompatActivity() {

    //AdView adView;
    private lateinit var mAdView: AdView
    var adcount:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speaking)
        title = "Speaking"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mAdView = findViewById(R.id.adView)


        val sharedPreferences = getSharedPreferences("adcount", Context.MODE_PRIVATE)
        adcount = sharedPreferences?.getInt("count", 0)!!;

        if (adcount < 5){
            val adRequest = AdRequest.Builder().build()
            mAdView.loadAd(adRequest)
            mAdView.adListener = object: AdListener() {
                override fun onAdOpened() {
                    super.onAdOpened()
                    adcount++;
                }
            }
        }else{
            mAdView.visibility = View.GONE
        }
    }

    override fun onStop() {
        super.onStop()
        val sharedPreferences = getSharedPreferences("adcount", Context.MODE_PRIVATE)
        val myEdidts = sharedPreferences?.edit()
        myEdidts?.putInt("count", adcount)
        myEdidts?.apply()
        Log.i("Add click", "data saved")
    }
}
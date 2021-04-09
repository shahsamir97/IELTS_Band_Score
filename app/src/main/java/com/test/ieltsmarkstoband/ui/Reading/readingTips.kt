package com.test.ieltsmarkstoband.ui.Reading

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.test.ieltsmarkstoband.R

class readingTips : Fragment() {
    //AdView adView;
    lateinit var mAdView: AdView
    var adcount: Int = 0
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_reading_tips, container, false)
        mAdView = root.findViewById(R.id.adView)

        val sharedPreferences = activity?.getSharedPreferences("adcount", Context.MODE_PRIVATE)
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
        return root
    }

    override fun onStop() {
        super.onStop()
        val sharedPreferences = activity?.getSharedPreferences("adcount", Context.MODE_PRIVATE)
        val myEdidts = sharedPreferences?.edit()
        myEdidts?.putInt("count", adcount)
        myEdidts?.apply()
        Log.i("Add click", "data saved")
    }
}
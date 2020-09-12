package com.test.ieltsmarkstoband.ui.Reading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.test.ieltsmarkstoband.R

class readingTips : Fragment() {
    //AdView adView;
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //adView = root.findViewById(R.id.adView);
        //  AdRequest adRequest = new AdRequest.Builder().build();
        //  adView.loadAd(adRequest);
        return inflater.inflate(R.layout.fragment_reading_tips, container, false)
    }
}
package com.test.ieltsmarkstoband.ui.Listening

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.test.ieltsmarkstoband.R

class ListeningTipsFragment : Fragment() {
    //AdView adView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // adView = root.findViewById(R.id.adView);
        // AdRequest adRequest = new AdRequest.Builder().build();
        //adView.loadAd(adRequest);
        return inflater.inflate(R.layout.fragment_listening_tips, container, false)
    }
}
package com.test.ieltsmarkstoband.ui.Writing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.test.ieltsmarkstoband.R

class WritingTipsFragment : Fragment() {
    // AdView adView;
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // adView = root.findViewById(R.id.adView);
        //   AdRequest adRequest = new AdRequest.Builder().build();
        // adView.loadAd(adRequest);
        return inflater.inflate(R.layout.fragment_writing_tips, container, false)
    }
}
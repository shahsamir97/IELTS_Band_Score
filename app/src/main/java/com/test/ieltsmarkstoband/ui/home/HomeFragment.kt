package com.test.ieltsmarkstoband.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Switch
import androidx.fragment.app.Fragment
import com.test.ieltsmarkstoband.MainActivity
import com.test.ieltsmarkstoband.R

class HomeFragment : Fragment() {
    lateinit var moduleSwitch: Switch
    override fun onStart() {
        super.onStart()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val mainActivity = activity as MainActivity?
        moduleSwitch = root.findViewById(R.id.moduleSwitch)
        moduleSwitch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                moduleSwitch.setText("Academic")
                MainActivity.switchMode = 1
            } else {
                moduleSwitch.setText("General Training")
                MainActivity.switchMode = 0
            }
        })
        return root
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = activity!!.getSharedPreferences("ModulePreference", Context.MODE_PRIVATE)
        val tempMode = sharedPreferences.getInt("switchMode", 0)
        moduleSwitch!!.isChecked = tempMode == 1
    }

    override fun onPause() {
        super.onPause()
    }
}
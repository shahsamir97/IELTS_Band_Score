package com.test.ieltsmarkstoband.ui.share

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.test.ieltsmarkstoband.R

class ShareFragment : Fragment() {
    var shareButton: Button? = null
    var root: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shareApp()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 8000) {
            if (resultCode == Activity.RESULT_OK) {
                showDialog()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_share, container, false)
        shareButton = root?.findViewById<View>(R.id.shareButton) as Button
        shareButton!!.setOnClickListener { shareApp() }
        return root
    }

    private fun showDialog() {
        val alert = AlertDialog.Builder(context)
        val view = layoutInflater.inflate(R.layout.thanks_fragment, null)
        alert.setView(view)
        val alertDialog = alert.create()
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()
        object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                alertDialog.dismiss()
            }
        }.start()
    }

    fun shareApp() {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=com.samir.ieltsmarkstoband")
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Try This Amazing App")
        sendIntent.type = "text/plain"
        val shareIntent = Intent.createChooser(sendIntent, "Share With friends")
        startActivityForResult(shareIntent, 8000)
    }
}
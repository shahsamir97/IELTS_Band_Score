package com.test.ieltsmarkstoband

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.textfield.TextInputLayout

class TotalBandScoreActivity : AppCompatActivity() {

    private lateinit var viewModel: TotalBandScoreViewModel

    var bandScore: Double? = null
    var readingMark: EditText? = null
    var writingMark: EditText? = null
    var listeningMark: EditText? = null
    var speakingMark: EditText? = null
    var scoreTextView: TextView? = null
    var readingMarkLayout: TextInputLayout? = null
    var writingMarkLayout: TextInputLayout? = null
    var listeningMarkLayout: TextInputLayout? = null
    var speakingMarkLayout: TextInputLayout? = null
    private var userEmail: String? = null
    private var userName: String? = null

    //Database instance
    var appDatabase = Database()
    override fun onStart() {
        super.onStart()
        val sharedPreferences2 = getSharedPreferences("ModulePreference", Context.MODE_PRIVATE)
        userEmail = sharedPreferences2.getString("useremail", "")
        userName = sharedPreferences2.getString("username", "")
        val temp = userEmail!!.split("@".toRegex()).toTypedArray()
        val temp2 = temp[1].split("\\.".toRegex()).toTypedArray()
        userEmail = temp[0] + temp2[0]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_total_band_score)
        title = "Total Band Score"

        viewModel = ViewModelProviders.of(this).get(TotalBandScoreViewModel::class.java)

        /*//Admob Section
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-8179519608335020/1542360731");
        interstitialAd.loadAd(new AdRequest.Builder().build());
        //ends*/

        //Back Button setup
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        readingMark = findViewById(R.id.readingMarkEditText)
        readingMarkLayout = findViewById(R.id.section1MarkLayout)
        writingMark = findViewById(R.id.writingMark)
        writingMarkLayout = findViewById(R.id.section3MarkLayout)
        speakingMark = findViewById(R.id.speakingMark)
        speakingMarkLayout = findViewById(R.id.section4MarkLayout)
        listeningMark = findViewById(R.id.listeningMarkEditText)
        listeningMarkLayout = findViewById(R.id.section2MarkLayout)
        scoreTextView = findViewById(R.id.bandScoreText)
        scoreTextView?.setText(viewModel.score)
    }

    fun showMarkingGuide(view: View?) {
        val dialogView = layoutInflater.inflate(R.layout.totalbandscore_dialog_fragment, null)
        val textView = dialogView.findViewById<TextView>(R.id.markingGuide)
        val okButton = dialogView.findViewById<Button>(R.id.okButton)
        val alert = AlertDialog.Builder(this)
        alert.setView(dialogView)
        val alertDialog = alert.create()
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.show()
        okButton.setOnClickListener { alertDialog.dismiss() }
    }

    private fun getTotal(reading: Double, writing: Double, listening: Double, speaking: Double): Double {
        return reading + writing + listening + speaking
    }

    private fun calculateAverage(total: Double): Double {
        return total / 4
    }

    private fun calculateBandScore(total: Double): Double {
        bandScore = total
        var numberD = bandScore.toString()
        numberD = numberD.substring(numberD.indexOf("."))
        val floorD = Math.floor(bandScore!!)
        var temp = numberD.toDouble()
        temp = if (temp >= .25 && temp < .75) {
            floorD + .5
        } else if (temp < .25) {
            floorD
        } else {
            Math.ceil(bandScore!!)
        }
        bandScore = temp
        return bandScore!!
    }

    private val isReadingModuleValid: Boolean
        private get() {
            val temp = readingMarkStringToDouble()
            return if (temp > 9) {
                readingMarkLayout!!.error = "Band score cannot be more than 9 !"
                false
            } else {
                readingMarkLayout!!.error = null
                true
            }
        }

    private val isWritingModuleValid: Boolean
        private get() {
            val temp = writingMarkStringToDouble()
            return if (temp > 9) {
                writingMarkLayout!!.error = "Band score cannot be more than 9 !"
                false
            } else {
                writingMarkLayout!!.error = null
                true
            }
        }

    private val isSpeakingModuleValid: Boolean
        private get() {
            val temp = speakingMarkStringToDouble()
            return if (temp > 9) {
                speakingMarkLayout!!.error = "Band score cannot be more than 9 !"
                false
            } else {
                speakingMarkLayout!!.error = null
                true
            }
        }

    private val isListeningModuleValid: Boolean
        private get() {
            val temp = listeningMarkStringToDouble()
            return if (temp > 9) {
                listeningMarkLayout!!.error = "Band score cannot be more than 9 !"
                false
            } else {
                listeningMarkLayout!!.error = null
                true
            }
        }

    private fun readingMarkStringToDouble(): Double {
        return readingMark!!.text.toString().toDouble()
    }

    private fun writingMarkStringToDouble(): Double {
        return writingMark!!.text.toString().toDouble()
    }

    private fun listeningMarkStringToDouble(): Double {
        return listeningMark!!.text.toString().toDouble()
    }

    private fun speakingMarkStringToDouble(): Double {
        return speakingMark!!.text.toString().toDouble()
    }

    fun calculate(view: View?) {
        val mark1 = readingMark!!.text.toString().trim { it <= ' ' }
        val mark2 = listeningMark!!.text.toString().trim { it <= ' ' }
        val mark3 = writingMark!!.text.toString().trim { it <= ' ' }
        val mark4 = speakingMark!!.text.toString().trim { it <= ' ' }
        if (!TextUtils.isEmpty(readingMark!!.text.toString())
                && !TextUtils.isEmpty(writingMark!!.text.toString())
                && !TextUtils.isEmpty(speakingMark!!.text.toString())
                && !TextUtils.isEmpty(listeningMark!!.text.toString())) {
            val b1 = isListeningModuleValid
            val b2 = isReadingModuleValid
            val b3 = isWritingModuleValid
            val b4 = isSpeakingModuleValid
            if (b1 && b2 && b3 && b4) {
                val totalMark = getTotal(readingMarkStringToDouble(), writingMarkStringToDouble(),
                        speakingMarkStringToDouble(),
                        listeningMarkStringToDouble())
                if (totalMark <= 36) {
                    val average = calculateAverage(totalMark)
                    val bandResult = calculateBandScore(average)
                    val result = java.lang.Double.toString(bandResult)
                    scoreTextView!!.textSize = 36f
                    scoreTextView!!.text = "Band Score : $result"
                    viewModel.score = "Band Score : $result"

                    //Storing User Data in database
                    appDatabase.insertData(result, mark1, mark2, mark3, mark4, "", userEmail, userName, "Total Band Scores")

                    /* //Admob section
                    if (interstitialAd.isLoaded()) {
                        interstitialAd.show();
                    } else {
                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                    }
                    //ends here*/
                } else {
                    scoreTextView!!.textSize = 24f
                    scoreTextView!!.text = "Wrong! No Band Score can be greater than 9!"
                }
            } else {
                scoreTextView!!.textSize = 24f
                scoreTextView!!.text = "Wrong! Check Band Scores."
            }
        } else {
            scoreTextView!!.textSize = 24f
            scoreTextView!!.text = "Wrong Input! Enter all Band Scores."
        }
    }
}
package com.test.ieltsmarkstoband

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.textfield.TextInputLayout

class ReadingActivity : AppCompatActivity() {

    private lateinit var viewmodel: ReadingViewModel

    //Database instancce
    var appDatabase = Database()
    var score = 0.0
    lateinit var section1: EditText
    lateinit var section2: EditText
    lateinit var section3: EditText
    lateinit var totalMarkEditText: EditText
    lateinit var section1Layout: TextInputLayout
    lateinit var section2Layout: TextInputLayout
    lateinit var section3Layout: TextInputLayout
    lateinit var totalMarkLayout: TextInputLayout
   lateinit var bandScoreTextView: TextView
   lateinit var tipsTextView: TextView
    var tipsAndTricks: String? = null
    var moduleMode = 0
    private var userEmail: String? = null
    private var userName: String? = null
    var markingGuideImageView: ImageView? = null
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
        setContentView(R.layout.activity_reading)
        title = "Reading"
        viewmodel = ViewModelProviders.of(this).get(ReadingViewModel::class.java)

        //Admob Section
        /*
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-8179519608335020/2768453125");
        interstitialAd.loadAd(new AdRequest.Builder().build());

         */
        //ends
        val intent = intent
        val bundle = intent.extras
        moduleMode = bundle!!.getInt("module")
        section1 = findViewById(R.id.section1Mark)
        section2 = findViewById(R.id.section2Mark)
        section3 = findViewById(R.id.writingMark)
        totalMarkEditText = findViewById(R.id.totalMarkEditText)
        section1Layout = findViewById(R.id.section1MarkLayout)
        section2Layout = findViewById(R.id.section2MarkLayout)
        section3Layout = findViewById(R.id.section3MarkLayout)
        totalMarkLayout = findViewById(R.id.totalMarkLayout)
        bandScoreTextView = findViewById(R.id.bandScoreText)
        bandScoreTextView.setText(viewmodel.score)


        //Marks Text Field key Listeners
        section1.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (TextUtils.isEmpty(section1.getText().toString())
                    && TextUtils.isEmpty(section2.getText().toString())
                    && TextUtils.isEmpty(section2.getText().toString())) {
                totalMarkEditText.setEnabled(true)
            } else if (!TextUtils.isEmpty(section1.getText().toString())) {
                totalMarkEditText.setEnabled(false)
            } else {
                totalMarkEditText.setEnabled(true)
            }
            setTotalMarkErrorTONULL()
            false
        })
        section2.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (TextUtils.isEmpty(section1.getText().toString())
                    && TextUtils.isEmpty(section2.getText().toString())
                    && TextUtils.isEmpty(section2.getText().toString())) {
                totalMarkEditText.setEnabled(true)
            } else if (!TextUtils.isEmpty(section2.getText().toString())) {
                totalMarkEditText.setEnabled(false)
            } else {
                totalMarkEditText.setEnabled(true)
            }
            setTotalMarkErrorTONULL()
            false
        })
        section3.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (TextUtils.isEmpty(section1.getText().toString())
                    && TextUtils.isEmpty(section2.getText().toString())
                    && TextUtils.isEmpty(section2.getText().toString())) {
                totalMarkEditText.setEnabled(true)
            } else if (!TextUtils.isEmpty(section3.getText().toString())) {
                totalMarkEditText.setEnabled(false)
            } else {
                totalMarkEditText.setEnabled(true)
            }
            setTotalMarkErrorTONULL()
            false
        })
        totalMarkEditText.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (!TextUtils.isEmpty(totalMarkEditText.getText().toString())) {
                section1.setEnabled(false)
                section2.setEnabled(false)
                section3.setEnabled(false)
                setAllSectionErrorToNULL()
            } else {
                section1.setEnabled(true)
                section2.setEnabled(true)
                section3.setEnabled(true)
            }
            false
        })
    }

    fun convertMarksToBandScoreAcademic(mark: Int): String {
        val tempBandScore: String
        var score = 0.0
        if (mark < 4) {
            score = 0.0
        } else if (mark == 4 || mark == 5) {
            score = 2.5
        } else if (mark == 6 || mark == 7) {
            score = 3.0
        } else if (mark == 8 || mark == 9) {
            score = 3.5
        } else if (mark >= 10 && mark <= 12) {
            score = 4.0
        } else if (mark == 14 || mark == 13) {
            score = 4.5
        } else if (mark >= 15 && mark <= 18) {
            score = 5.0
        } else if (mark >= 19 && mark <= 22) {
            score = 5.5
        } else if (mark >= 23 && mark <= 26) {
            score = 6.0
        } else if (mark >= 27 && mark <= 29) {
            score = 6.5
        } else if (mark >= 30 && mark <= 32) {
            score = 7.0
        } else if (mark == 33 || mark == 34) {
            score = 7.5
        } else if (mark == 35 || mark == 36) {
            score = 8.0
        } else if (mark == 37 || mark == 38) {
            score = 8.5
        } else if (mark == 39 || mark == 40) {
            score = 9.0
        }
        tempBandScore = java.lang.Double.toString(score)
        return tempBandScore
    }

    fun convertMarksToBandScoreGeneral(mark: Int): String {
        val tempBandScore: String
        var score = 0.0
        if (mark < 6) {
            score = 0.0
        } else if (mark >= 6 && mark <= 8) {
            score = 2.5
        } else if (mark >= 9 && mark <= 11) {
            score = 3.0
        } else if (mark >= 12 && mark <= 14) {
            score = 3.5
        } else if (mark >= 15 && mark <= 18) {
            score = 4.0
        } else if (mark >= 19 && mark <= 22) {
            score = 4.5
        } else if (mark >= 23 && mark <= 26) {
            score = 5.0
        } else if (mark >= 27 && mark <= 29) {
            score = 5.5
        } else if (mark == 30 || mark == 31) {
            score = 6.0
        } else if (mark == 32 || mark == 33) {
            score = 6.5
        } else if (mark == 34 || mark == 35) {
            score = 7.0
        } else if (mark == 36) {
            score = 7.5
        } else if (mark == 37 || mark == 38) {
            score = 8.0
        } else if (mark == 39) {
            score = 8.5
        } else if (mark == 40) {
            score = 9.0
        }
        tempBandScore = if (mark < 6) {
            "0"
        } else {
            java.lang.Double.toString(score)
        }
        return tempBandScore
    }

    //set error message null
    private fun setAllSectionErrorToNULL() {
        section1Layout!!.error = null
        section2Layout!!.error = null
        section3Layout!!.error = null
    }

    private fun setTotalMarkErrorTONULL() {
        totalMarkLayout!!.error = null
    }

    private fun section1MarkStringToInt(): Int {
        return section1!!.text.toString().toInt()
    }

    private fun section2MarkStringToInt(): Int {
        return section2!!.text.toString().toInt()
    }

    private fun section3MarkStringToInt(): Int {
        return section3!!.text.toString().toInt()
    }

    private fun totalMarkStringToInt(): Int {
        return totalMarkEditText!!.text.toString().toInt()
    }

    //Text Fields Verification
    private val isSection1Valid: Boolean
        private get() {
            val temp = section1MarkStringToInt()
            return if (temp > 20) {
                section1Layout!!.error = "Mark cannot be greater than 20 !"
                false
            } else {
                section1Layout!!.error = null
                true
            }
        }

    private val isSection2Valid: Boolean
        private get() {
            val temp = section2MarkStringToInt()
            return if (temp > 20) {
                section2Layout!!.error = "Mark cannot be greater than 20 !"
                false
            } else {
                section2Layout!!.error = null
                true
            }
        }

    private val isSection3Valid: Boolean
        private get() {
            val temp = section3MarkStringToInt()
            return if (temp > 20) {
                section3Layout!!.error = "Mark cannot be greater than 20 !"
                false
            } else {
                section3Layout!!.error = null
                true
            }
        }

    private val isTotalMarkValid: Boolean
        private get() = if (totalMarkStringToInt() > 40) {
            totalMarkLayout!!.error = "Mark cannot be greater than 40 !"
            false
        } else {
            totalMarkLayout!!.error = null
            true
        }

    private fun getTotal(mark1: Int, mark2: Int, mark3: Int): Int {
        return mark1 + mark2 + mark3
    }

    fun showMarkingGuide(view: View?) {
        val dialogView = layoutInflater.inflate(R.layout.marking_guide_dialog, null)
        val imageView = dialogView.findViewById<ImageView>(R.id.guideImageView)
        val okButton = dialogView.findViewById<Button>(R.id.okButton)
        val alert = AlertDialog.Builder(this)
        if (moduleMode == 0) {
            imageView.setImageResource(R.mipmap.readinggereralscores)
        } else {
            imageView.setImageResource(R.mipmap.readingacademicscores)
        }
        alert.setView(dialogView)
        val alertDialog = alert.create()
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.show()
        okButton.setOnClickListener { alertDialog.dismiss() }
    }

    fun calculate(view: View?) {
        val mark1 = section1!!.text.toString().trim { it <= ' ' }
        val mark2 = section2!!.text.toString().trim { it <= ' ' }
        val mark3 = section3!!.text.toString().trim { it <= ' ' }
        if (!TextUtils.isEmpty(totalMarkEditText!!.text.toString())) {
            if (isTotalMarkValid) {
                val total = totalMarkEditText!!.text.toString().toInt()
                if (total <= 40) {
                    if (moduleMode == 0) {
                        val score = convertMarksToBandScoreGeneral(total)
                        bandScoreTextView!!.textSize = 30f
                        bandScoreTextView!!.text = "Your Band Score is $score"
                        viewmodel.score = score
                        //Storing user data in database
                        appDatabase.insertData(score, mark1, mark2, mark3, "", Integer.toString(total), userEmail, userName, "Reading")
                        //ends

                        //Admob section
                        /*
                        if (interstitialAd.isLoaded()) {
                            interstitialAd.show();
                        } else {
                            Log.i("Admob :", "Failed to load Add");
                        }

                         */
                        //ends
                    } else {
                        val score = convertMarksToBandScoreAcademic(total)
                        bandScoreTextView!!.textSize = 30f
                        bandScoreTextView!!.text = "Your Band Score is $score"
                        viewmodel.score = score
                        //Storing user data in database
                        appDatabase.insertData(score, mark1, mark2, mark3, "", Integer.toString(total), userEmail, userName, "Reading")
                        //ends
                        /*
                        if (interstitialAd.isLoaded()) {
                            interstitialAd.show();
                        } else {
                            Log.d("TAG", "The interstitial wasn't loaded yet.");
                        }

                         */
                        //ends here
                    }
                } else {
                    bandScoreTextView!!.textSize = 24f
                    bandScoreTextView!!.text = "Mark sum cannot be more than 40!"
                    //tipsTextView.setText("Marks sum cannot be more than 40. \n" +
                    //      "Check your inputs and try again.");
                }
            } else {
                bandScoreTextView!!.text = "Wrong Input!"
            }
        } else if (!mark1.equals("") && !mark2.equals("") && !mark3.equals("")) {
            val b1 = isSection1Valid
            val b2 = isSection2Valid
            val b3 = isSection3Valid
            if (b1 && b2 && b3) {
                val total = getTotal(section1MarkStringToInt(), section2MarkStringToInt(), section3MarkStringToInt())
                if (total <= 40) {
                    if (moduleMode == 0) {
                        val score = convertMarksToBandScoreGeneral(total)
                        bandScoreTextView!!.textSize = 30f
                        bandScoreTextView!!.text = "Your Band Score is $score"
                        viewmodel.score = score
                        //Storing user data in database
                        appDatabase.insertData(score, mark1, mark2, mark3, "", Integer.toString(total), userEmail, userName, "Reading")
                        //ends

                        //Admob section
                        /*
                        if (interstitialAd.isLoaded()) {
                            interstitialAd.show();
                        } else {
                            Log.d("TAG", "The interstitial wasn't loaded yet.");
                        }

                         */
                        //ends here
                    } else {
                        val score = convertMarksToBandScoreAcademic(total)
                        bandScoreTextView!!.textSize = 30f
                        bandScoreTextView!!.text = "Your Band Score is $score"
                        viewmodel.score = score
                        //Storing user data in database
                        appDatabase.insertData(score, mark1, mark2, mark3, "", Integer.toString(total), userEmail, userName, "Reading")
                        //ends
                    }

                    // tipsTextView.setText(tipsAndTricks);
                } else {
                    bandScoreTextView!!.textSize = 24f
                    bandScoreTextView!!.text = "Mark sum cannot be more than 40!"
                    //tipsTextView.setText("Marks sum cannot be more than 40. \n" +
                    //  "Check your inputs and try again.");
                }
            } else {
                bandScoreTextView!!.text = "Wrong Input!"
            }
        } else {
            bandScoreTextView!!.text = "Fields are empty!"
            //tipsTextView.setText("Check the marks inputs.Do not leave any filed empty");
        }
    }
}
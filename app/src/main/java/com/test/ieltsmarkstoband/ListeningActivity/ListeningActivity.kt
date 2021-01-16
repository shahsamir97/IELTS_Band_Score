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
import com.google.firebase.auth.FirebaseAuth

class ListeningActivity : AppCompatActivity() {

    private lateinit var viewModel: ListeningViewModel

    var score = 0.0
    lateinit var section1: EditText
    lateinit var section2: EditText
    lateinit var section3: EditText
    lateinit var section4: EditText
    lateinit var totalMarkEditText: EditText
    lateinit var section1Layout: TextInputLayout
    lateinit var section2Layout: TextInputLayout
    lateinit var section3Layout: TextInputLayout
    lateinit var section4Layout: TextInputLayout
    lateinit var totalMarkLayout: TextInputLayout
    lateinit var bandScoreTextView: TextView
    lateinit var tipsTextView: TextView
    var tipsAndTricks: String? = null

    //Database
    var appDatabase = Database()
    private var userName: String? = null
    private var userEmail: String? = null
    override fun onStart() {
        super.onStart()
        val sharedPreferences2 = getSharedPreferences("ModulePreference", Context.MODE_PRIVATE)
        val currentUser = FirebaseAuth.getInstance().currentUser
        userEmail = currentUser?.email //sharedPreferences2.getString("useremail", "")
        userName = currentUser?.displayName
        val temp = userEmail!!.split("@".toRegex()).toTypedArray()
        val temp2 = temp[1].split("\\.".toRegex()).toTypedArray()
        userEmail = temp[0] + temp2[0]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listening)
        title = "Listening"

        viewModel = ViewModelProviders.of(this).get(ListeningViewModel::class.java)
        //Admob Section
        /*
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-8179519608335020/9525433166");
        interstitialAd.loadAd(new AdRequest.Builder().build());

         */
        //ends


        //Back button setup
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //View initializations
        section1 = findViewById(R.id.section1Mark)
        section2 = findViewById(R.id.section2Mark)
        section3 = findViewById(R.id.writingMark)
        section4 = findViewById(R.id.speakingMark)
        section1Layout = findViewById(R.id.section1MarkLayout)
        section2Layout = findViewById(R.id.section2MarkLayout)
        section3Layout = findViewById(R.id.section3MarkLayout)
        section4Layout = findViewById(R.id.section4MarkLayout)
        totalMarkLayout = findViewById(R.id.totalMarkLayout)
        totalMarkEditText = findViewById(R.id.totalMarkEditText)
        bandScoreTextView = findViewById(R.id.bandScoreText)
        bandScoreTextView.setText(viewModel.score)

        //Marks Text Field key Listeners
        section1.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (TextUtils.isEmpty(section1.getText().toString()) && TextUtils.isEmpty(section2.getText().toString())
                    && TextUtils.isEmpty(section2.getText().toString())
                    && TextUtils.isEmpty(section4.getText().toString())) {
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
            if (TextUtils.isEmpty(section1.getText().toString()) && TextUtils.isEmpty(section2.getText().toString())
                    && TextUtils.isEmpty(section2.getText().toString())
                    && TextUtils.isEmpty(section4.getText().toString())) {
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
            if (TextUtils.isEmpty(section1.getText().toString()) && TextUtils.isEmpty(section2.getText().toString())
                    && TextUtils.isEmpty(section2.getText().toString())
                    && TextUtils.isEmpty(section4.getText().toString())) {
                totalMarkEditText.setEnabled(true)
            } else if (!TextUtils.isEmpty(section3.getText().toString())) {
                totalMarkEditText.setEnabled(false)
            } else {
                totalMarkEditText.setEnabled(true)
            }
            setTotalMarkErrorTONULL()
            false
        })
        section4.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (TextUtils.isEmpty(section1.getText().toString()) && TextUtils.isEmpty(section2.getText().toString())
                    && TextUtils.isEmpty(section2.getText().toString())
                    && TextUtils.isEmpty(section4.getText().toString())) {
                totalMarkEditText.setEnabled(true)
            } else if (!TextUtils.isEmpty(section4.getText().toString())) {
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
                section4.setEnabled(false)
                setAllSectionErrorToNULL()
            } else {
                section1.setEnabled(true)
                section2.setEnabled(true)
                section3.setEnabled(true)
                section4.setEnabled(true)
            }
            false
        })


        //tipsTextView = findViewById(R.id.tipsText);
    }

    private fun convertMarksToBand(mark: Int): String {
        val tempbandscore: String
        if (mark < 11) {
            score = 0.0
        } else if (mark == 11 || mark == 12) {
            score = 4.0
            tipsAndTricks = """
                LOW SCORE!
                Better Luck Next Time. Don't worry we have some tricks for you to improve your score. Checkout our Listening Tips section for more info.
                """.trimIndent()
        } else if (mark >= 13 && mark <= 15) {
            score = 4.5
            tipsAndTricks = """
                LOW SCORE!
                Better Luck Next Time. Don't worry we have some tricks for you to improve your score. Checkout our  Listening Tips section for more info.
                """.trimIndent()
        } else if (mark == 16 || mark == 17) {
            score = 5.0
            tipsAndTricks = """On The Way to Success
 Following listening Trips and Tricks and score more than 8 band. Checkout our  Listening Tips section for more info"""
        } else if (mark >= 18 && mark <= 22) {
            score = 5.5
            tipsAndTricks = """
                On The Way to Success
                Following our listening Trips and Tricks and score more than 8 band. Checkout our  Listening Tips section for more info.
                """.trimIndent()
        } else if (mark >= 23 && mark <= 25) {
            score = 6.0
            tipsAndTricks = """
                You're Nearly To Success
                Following some cool trips and tricks will help you to get a good band score. Checkout our  Listening Tips section for more info.
                """.trimIndent()
        } else if (mark >= 26 && mark <= 29) {
            score = 6.5
            tipsAndTricks = """You're Nearly To Success
 Following some cool trips and tricks will help you to get a good band score. Checkout our  Listening Tips section for more info."""
        } else if (mark == 30 || mark == 31) {
            score = 7.0
            tipsAndTricks = """WELL DONE!
 You're almost scoring good. Follow our tips to get a band more than 8. Checkout our  Listening Tips section for more info"""
        } else if (mark >= 32 && mark <= 34) {
            score = 7.5
            tipsAndTricks = """
                SCORING GOOD!
                You're almost scoring goodFollow our tips to get a band more than 8. Checkout our Listening Tips section for more info
                """.trimIndent()
        } else if (mark == 35 || mark == 36) {
            score = 8.0
            tipsAndTricks = """
                VERY GOOD!
                You're almost scoring goodFollowing some trick may help you to score more than 8Checkout our Listening Tips section for more info
                """.trimIndent()
        } else if (mark == 37 || mark == 38) {
            score = 8.5
            tipsAndTricks = """
                CONGRATULATIONS!
                Your score is high.Keep practicing to be confident with this scoreCheckout our practice section.
                """.trimIndent()
        } else if (mark == 39 || mark == 40) {
            score = 9.0
            tipsAndTricks = """
                AMAZING!
                You have scored the highest score. Keep practicing to be confident with this scoreCheckout our practice section.
                """.trimIndent()
        }
        tempbandscore = java.lang.Double.toString(score)
        return tempbandscore
    }

    private fun getTotal(mark1: Int, mark2: Int, mark3: Int, mark4: Int): Int {
        return mark1 + mark2 + mark3 + mark4
    }

    //set error message null
    private fun setAllSectionErrorToNULL() {
        section1Layout!!.error = null
        section2Layout!!.error = null
        section3Layout!!.error = null
        section4Layout!!.error = null
    }

    private fun setTotalMarkErrorTONULL() {
        totalMarkLayout!!.error = null
    }

    //Marks string to int convertion
    private fun section1MarkStringToInt(): Int {
        return section1!!.text.toString().toInt()
    }

    private fun section2MarkStringToInt(): Int {
        return section2!!.text.toString().toInt()
    }

    private fun section3MarkStringToInt(): Int {
        return section3!!.text.toString().toInt()
    }

    private fun section4MarkStringToInt(): Int {
        return section4!!.text.toString().toInt()
    }

    private fun totalMarkStringToInt(): Int {
        return totalMarkEditText!!.text.toString().toInt()
    }

    //Text Fields Verification
    private val isSection1Valid: Boolean
        private get() {
            val temp = section1MarkStringToInt()
            return if (temp > 10) {
                section1Layout!!.error = "Mark cannot be greater than 10 !"
                false
            } else {
                section1Layout!!.error = null
                true
            }
        }

    private val isSection2Valid: Boolean
        private get() {
            val temp = section2MarkStringToInt()
            return if (temp > 10) {
                section2Layout!!.error = "Mark cannot be greater than 10 !"
                false
            } else {
                section2Layout!!.error = null
                true
            }
        }

    private val isSection3Valid: Boolean
        private get() {
            val temp = section3MarkStringToInt()
            return if (temp > 10) {
                section3Layout!!.error = "Mark cannot be greater than 10 !"
                false
            } else {
                section3Layout!!.error = null
                true
            }
        }

    private val isSection4Valid: Boolean
        private get() {
            val temp = section4MarkStringToInt()
            return if (temp > 10) {
                section4Layout!!.error = "Mark cannot be greater than 10 !"
                false
            } else {
                section4Layout!!.error = null
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

    fun calculate(view: View?) {
        val mark1 = section1!!.text.toString().trim { it <= ' ' }
        val mark2 = section2!!.text.toString().trim { it <= ' ' }
        val mark3 = section3!!.text.toString().trim { it <= ' ' }
        val mark4 = section4!!.text.toString().trim { it <= ' ' }
        if (!TextUtils.isEmpty(totalMarkEditText!!.text.toString())) {
            if (isTotalMarkValid) {
                val total = totalMarkEditText!!.text.toString().toInt()
                if (total <= 40) {
                    val score = convertMarksToBand(total)
                    bandScoreTextView!!.textSize = 30f
                    bandScoreTextView!!.text = "Your Band Score is $score"

                    //Storing UserData in Database
                    appDatabase.insertData(score, mark1, mark2, mark3, mark4, Integer.toString(total), userEmail, userName, "Listening")

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
                    bandScoreTextView!!.textSize = 24f
                    bandScoreTextView!!.text = "Mark sum cannot be more than 40 !"
                    //tipsTextView.setText("Marks sum cannot be more than 40. \n" +
                    //      "Check your inputs and try again.");
                }
            } else {
                bandScoreTextView!!.text = "Wrong Input!"
            }
        } else if (!mark1.equals("") && !mark2.equals("") && !mark3.equals("")
                && !mark4.equals("")) {
            val b1 = isSection1Valid
            val b2 = isSection2Valid
            val b3 = isSection3Valid
            val b4 = isSection4Valid
            if (b1 && b2 && b3 && b4) {
                val total = getTotal(section1MarkStringToInt(), section2MarkStringToInt(), section3MarkStringToInt(), section4MarkStringToInt())
                if (total <= 40) {
                    val score = convertMarksToBand(total)
                    bandScoreTextView!!.textSize = 30f
                    bandScoreTextView!!.text = "Your Band Score is $score"
                    viewModel.score = score
                    //Storing UserData in Database
                    appDatabase.insertData(score, mark1, mark2, mark3, mark4, Integer.toString(total), userEmail, userName, "Listening")
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
                    bandScoreTextView!!.textSize = 24f
                    bandScoreTextView!!.text = "Mark sum cannot be more than 40!"
                    //tipsTextView.setText("Marks sum cannot be more than 40. \n" +
                    //  "Check your inputs and try again.");
                }
            } else {
                bandScoreTextView!!.text = "Wrong Input!"
            }
        } else {
            bandScoreTextView!!.textSize = 22f
            bandScoreTextView!!.text = "Fields are empty! Fill up all fields."
            //tipsTextView.setText("Check the marks inputs.Do not leave any filed empty");
        }
    }

    fun showMarkingGuide(view: View?) {
        //Inflating Dialog View
        val mView = layoutInflater.inflate(R.layout.marking_guide_dialog, null)
        val okButton = mView.findViewById<Button>(R.id.okButton)
        val imageView = mView.findViewById<ImageView>(R.id.guideImageView)
        imageView.setImageResource(R.mipmap.listenongscores)

        //creating dialog
        val alert = AlertDialog.Builder(this)
        alert.setView(mView)
        val alertDialog = alert.create()
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.show()
        okButton.setOnClickListener { alertDialog.dismiss() }
    }
}
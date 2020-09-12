package com.test.ieltsmarkstoband

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.messaging.FirebaseMessaging
import com.test.ieltsmarkstoband.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {
    private var mAppBarConfiguration: AppBarConfiguration? = null

    var userName: String? = ""
        private set
        get() {
            return field
        }
    var userEmail: String? = ""
        private set
    var navigationView: NavigationView? = null


    companion object{
        var switchMode = 0

    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //cloud Messaging Channel Setup
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("MyNotifications",
                    "MyNotifications", NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener { task ->
                    var msg = "Successful"
                    if (!task.isSuccessful) {
                        msg = "Failed"
                    }
                }

        //Admob initialization
        /*
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
         */

        //Navigation Drawer
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        //Dialog setup ends here

        //Toolbar setup
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_speaking_tips, R.id.nav_listening_tips,
                R.id.nav_writing_tips, R.id.nav_reading_tips, R.id.nav_share, R.id.nav_feedback)
                .setDrawerLayout(drawer)
                .build()
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration!!)
        navigationView?.let { NavigationUI.setupWithNavController(it, navController) }

        //Home fragment switch button state
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val homeFragment = HomeFragment()
        fragmentTransaction.add(0, homeFragment)
        fragmentTransaction.commit()

        //setting header info
    }

    //verify User name
    private fun isUserNameValid(text: EditText): Boolean {
        return !TextUtils.isEmpty(text.text.toString())
    }

    //Verify User email address
    private fun isEmailValid(text: EditText): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(text.text.toString()).matches()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        return (NavigationUI.navigateUp(navController, mAppBarConfiguration!!)
                || super.onSupportNavigateUp())
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = getSharedPreferences("ModulePreference", Context.MODE_PRIVATE)
        userName = sharedPreferences.getString("username", "")
        userEmail = sharedPreferences.getString("useremail", "")
        val headerView = navigationView!!.getHeaderView(0)
        val nameTextView = headerView.findViewById<TextView>(R.id.header_name)
        val emailTextView = headerView.findViewById<TextView>(R.id.header_email)

        //Input Dialog for user to get the User name and email
        val mView = layoutInflater.inflate(R.layout.dialog_fragment, null)
        val nameTextInput = mView.findViewById<EditText>(R.id.nameEditText)
        val emailTextInput = mView.findViewById<EditText>(R.id.emailEditText)
        val nameInputLayout: TextInputLayout = mView.findViewById(R.id.nameInputLayout)
        val emailInputLayout: TextInputLayout = mView.findViewById(R.id.emailInputLayout)
        val okButton = mView.findViewById<Button>(R.id.ok_button)
        if (userName!!.equals("") && userEmail!!.equals("")) {

            //Setting up Username and email
            val alert = AlertDialog.Builder(this)
            alert.setView(mView)
            val alertDialog = alert.create()
            alertDialog.setCanceledOnTouchOutside(false)
            alertDialog.show()
            okButton.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View) {
                    var nameValid = false
                    var emailValid = false
                    if (isUserNameValid(nameTextInput)) {
                        nameTextView.text = nameTextInput.text.toString()
                        nameValid = true
                        nameInputLayout.error = null
                    } else {
                        nameInputLayout.error = resources.getString(R.string.usernameError)
                        nameValid = false
                    }
                    if (!TextUtils.isEmpty(emailTextInput.text.toString())) {
                        if (isEmailValid(emailTextInput)) {
                            emailTextView.text = emailTextInput.text.toString()
                            emailInputLayout.error = null
                            emailValid = true
                        } else {
                            emailInputLayout.error = resources.getString(R.string.emailValidityError)
                            emailValid = false
                        }
                    } else {
                        emailInputLayout.error = resources.getString(R.string.emailEmptyError)
                        emailValid = false
                    }
                    if (nameValid && emailValid) {
                        userName = nameTextInput.text.toString()
                        userEmail = emailTextInput.text.toString()
                        alertDialog.dismiss()
                    }
                }
            })
        } else {
            nameTextView.text = userName
            emailTextView.text = userEmail
        }
    }

    override fun onPause() {
        super.onPause()
        //saving User Mode
        val sharedPreferences = getSharedPreferences("ModulePreference", Context.MODE_PRIVATE)
        val myEdidts = sharedPreferences.edit()
        myEdidts.putString("username", userName)
        myEdidts.putString("useremail", userEmail)
        myEdidts.putInt("switchMode", switchMode)
        myEdidts.commit()
    }

    fun readingModule(view: View?) {
        val intent = Intent(this, ReadingActivity::class.java)
        intent.putExtra("module", switchMode)
        startActivity(intent)
    }

    fun listeningModule(view: View?) {
        val intent = Intent(this, ListeningActivity::class.java)
        startActivity(intent)
    }

    fun writingModule(view: View?) {
        val intent = Intent(this, WritingActivity::class.java)
        startActivity(intent)
    }

    fun totalBandScore(view: View?) {
        val intent = Intent(this, TotalBandScoreActivity::class.java)
        startActivity(intent)
    }

    fun resultsActivity(view: View?) {
        val intent = Intent(this, ResultsActivity::class.java)
        startActivity(intent)
    }

    fun speakingModule(view: View?) {
        val intent = Intent(this, SpeakingActivity::class.java)
        startActivity(intent)
    }
}
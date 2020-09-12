package com.test.ieltsmarkstoband

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*
import java.util.*

class ResultsActivity : AppCompatActivity() {
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var marksRecyclerView: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private var adapter: RecyclerView.Adapter<*>? = null
    var progressBar: ProgressBar? = null
    var userMarkList: MutableList<UserMarksDataModel>? = null
    var databaseReference: DatabaseReference? = null
    lateinit var linearLayout: LinearLayout
    private var userEmail: String? = null
    private var userName: String? = null
    override fun onStart() {
        super.onStart()

        //Back button setup
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val sharedPreferences2 = getSharedPreferences("ModulePreference", Context.MODE_PRIVATE)
        userEmail = sharedPreferences2.getString("useremail", "")
        userName = sharedPreferences2.getString("username", "")
        val temp = userEmail!!.split("@".toRegex()).toTypedArray()
        val temp2 = temp[1].split("\\.".toRegex()).toTypedArray()
        userEmail = temp[0] + temp2[0]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)
        title = "Results"
        bottomNavigationView = findViewById(R.id.bottom_navigationView)
        marksRecyclerView = findViewById(R.id.dataRecyclerView)
        marksRecyclerView.setVisibility(View.GONE)
        linearLayout = findViewById(R.id.noDataTextViews)
        linearLayout.setVisibility(View.GONE)
        progressBar = findViewById(R.id.progressBar)
        userMarkList = ArrayList()
        marksRecyclerView.setHasFixedSize(true)
        marksRecyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        layoutManager = LinearLayoutManager(this)
        marksRecyclerView.setLayoutManager(layoutManager)
        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_reading -> {
                    menuItem.isChecked = true
                    showReadingMarks()
                }
                R.id.nav_listening -> {
                    menuItem.isChecked = true
                    showListeningMarks()
                }
                R.id.nav_speaking -> {
                    menuItem.isChecked = true
                    showSpeakingMarks()
                }
                R.id.nav_writing -> {
                    menuItem.isChecked = true
                    showWritingMarks()
                }
                R.id.nav_totalBand -> {
                    menuItem.isChecked = true
                    showTotalBandScores()
                }
            }
            false
        })
    }

    private fun loadData(id: Int) {
        when (id) {
            R.id.nav_reading -> {
                progressBar!!.visibility = View.VISIBLE
                showReadingMarks()
            }
            R.id.nav_listening -> {
                progressBar!!.visibility = View.VISIBLE
                showListeningMarks()
            }
            R.id.nav_speaking -> {
                progressBar!!.visibility = View.VISIBLE
                showSpeakingMarks()
            }
            R.id.nav_writing -> {
                progressBar!!.visibility = View.VISIBLE
                showWritingMarks()
            }
            R.id.nav_totalBand -> {
                progressBar!!.visibility = View.VISIBLE
                showTotalBandScores()
            }
        }
    }

    private fun showReadingMarks() {
        val getMarksInBackground = GetMarksInBackground()
        getMarksInBackground.execute("Reading")
    }

    private fun showSpeakingMarks() {
        val getMarksInBackground = GetMarksInBackground()
        getMarksInBackground.execute("Speaking")
    }

    private fun showWritingMarks() {
        val getMarksInBackground = GetMarksInBackground()
        getMarksInBackground.execute("Writing")
    }

    private fun showListeningMarks() {
        val getMarksInBackground = GetMarksInBackground()
        getMarksInBackground.execute("Listening")
    }

    private fun showTotalBandScores() {
        val getMarksInBackground = GetMarksInBackground()
        getMarksInBackground.execute("Total Band Scores")
    }

    override fun onPause() {
        super.onPause()
        val id = bottomNavigationView!!.selectedItemId
        val sharedPreferences = getSharedPreferences("bottom_navigation_preference", Context.MODE_PRIVATE)
        val myEdits = sharedPreferences.edit()
        myEdits.putInt("nav_ID", id)
        myEdits.commit()
    }

    override fun onResume() {
        super.onResume()
        val sharedPreferences = getSharedPreferences("bottom_navigation_preference", Context.MODE_PRIVATE)
        val id = sharedPreferences.getInt("nav_ID", 0)
        if (id != 0) {
            loadData(id)
            bottomNavigationView!!.selectedItemId = id
        } else {
            bottomNavigationView!!.selectedItemId = R.id.nav_reading
        }
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    inner class GetMarksInBackground : AsyncTask<String?, Void?, Void?>() {
         override fun doInBackground(vararg p0: String?): Void? {
            Log.i("Module Info", p0[0])
            databaseReference = p0[0]?.let { FirebaseDatabase.getInstance().getReference(userEmail!!).child(it) }
            databaseReference!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    userMarkList!!.clear()
                    for (markDataSnapshot in dataSnapshot.children) {
                        val dataModel = markDataSnapshot.getValue(UserMarksDataModel::class.java)!!
                        userMarkList!!.add(dataModel)
                    }
                    if (userMarkList!!.size == 0) {
                        progressBar!!.visibility = View.GONE
                        marksRecyclerView!!.visibility = View.GONE
                        linearLayout!!.visibility = View.VISIBLE
                    } else {
                        marksRecyclerView!!.visibility = View.VISIBLE
                        linearLayout!!.visibility = View.GONE
                        progressBar!!.visibility = View.GONE
                    }
                    adapter = UserDataRecyclerView(this@ResultsActivity, userMarkList!!)
                    marksRecyclerView!!.adapter = adapter
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
            return null
        }

    }
}
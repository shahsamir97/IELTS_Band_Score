package com.test.ieltsmarkstoband

import android.text.TextUtils
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class Database {
    var database: DatabaseReference? = null
    val date: String
        get() {
            val sdf = SimpleDateFormat("dd.MM.yyyy")
            return sdf.format(Date())
        }

    val day: String
        get() {
            val sdf = SimpleDateFormat("EEEE")
            val d = Date()
            return sdf.format(d)
        }

    fun insertData(score: String?, section1: String?, section2: String?, section3: String?, section4: String?, total: String?, userEmail: String?, userName: String?, module: String) {
        database = FirebaseDatabase.getInstance().getReference(userEmail!!).child(module)
        val id = database!!.push().key
        val userMarksDataModel: UserMarksDataModel
        userMarksDataModel = if (TextUtils.isEmpty(section1)) {
            val temp = Character.toString(module[0])
            UserMarksDataModel(score, total, date, day, temp)
        } else {
            val temp = Character.toString(module[0])
            UserMarksDataModel(score, section1, section2, section3, section4, total, date, day, temp)
        }
        database!!.child(id!!).setValue(userMarksDataModel)
    }
}
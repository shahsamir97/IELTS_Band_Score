package com.test.ieltsmarkstoband

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.test.ieltsmarkstoband.UserDataRecyclerView.UserDataViewHolder

class UserDataRecyclerView(var context: Context, var userMarksModel: List<UserMarksDataModel>) : RecyclerView.Adapter<UserDataViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDataViewHolder {
        val s1: TextView
        val s2: TextView
        val s3: TextView
        val s4: TextView
        val total: TextView
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.user_data_list, parent, false)
        s1 = view.findViewById(R.id.section1)
        s2 = view.findViewById(R.id.section2)
        s3 = view.findViewById(R.id.section3)
        s4 = view.findViewById(R.id.section4)
        total = view.findViewById(R.id.totalMark)
        s1.visibility = View.GONE
        s2.visibility = View.GONE
        s3.visibility = View.GONE
        s4.visibility = View.GONE
        total.visibility = View.GONE
        view.setOnClickListener { }
        return UserDataViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserDataViewHolder, position: Int) {
        holder.scoreTextView.text = userMarksModel[position].score
        if (userMarksModel[position].totalMark != null) {
            if (userMarksModel[position].module.equals("R")
                    || userMarksModel[position].module.equals("L")) {
                holder.total.visibility = View.VISIBLE
                holder.total.text = "Total Mark : " + userMarksModel[position].totalMark
            }
        }
        if (userMarksModel[position].section1 != null) {
            holder.section1.visibility = View.VISIBLE
            if (userMarksModel[position].module.equals("R")
                    || userMarksModel[position].module.equals("L")) {
                holder.section1.text = "Section 1 : " + userMarksModel[position].section1
            } else {
                holder.section1.text = "Reading : " + userMarksModel[position].section1
            }
        }
        if (userMarksModel[position].section2 != null) {
            holder.section2.visibility = View.VISIBLE
            if (userMarksModel[position].module.equals("R")
                    || userMarksModel[position].module.equals("L")) {
                holder.section2.text = "Section 2 : " + userMarksModel[position].section2
            } else {
                holder.section2.text = "Listening : " + userMarksModel[position].section2
            }
        }
        if (userMarksModel[position].section3 != null) {
            holder.section3.visibility = View.VISIBLE
            if (userMarksModel[position].module.equals("R")
                    || userMarksModel[position].module.equals("L")) {
                holder.section3.text = "Section 3 : " + userMarksModel[position].section3
            } else {
                holder.section3.text = "Writing : " + userMarksModel[position].section3
            }
        }
        if (userMarksModel[position].section4 != null) {
            holder.section4.visibility = View.VISIBLE
            if (userMarksModel[position].module.equals("R")
                    || userMarksModel[position].module.equals("L")) {
                holder.section4.text = "Section 4 : " + userMarksModel[position].section4
            } else {
                holder.section4.text = "Speaking : " + userMarksModel[position].section4
            }
        }
        holder.dayTextView.text = userMarksModel[position].day
        holder.dateTextView.text = userMarksModel[position].date
    }

    override fun getItemCount(): Int {
        return userMarksModel.size
    }

    inner class UserDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var section1: TextView
        var section2: TextView
        var section3: TextView
        var section4: TextView
        var total: TextView
        var dayTextView: TextView
        var dateTextView: TextView
        var scoreTextView: TextView

        init {
            scoreTextView = itemView.findViewById<View>(R.id.markTextView) as TextView
            total = itemView.findViewById<View>(R.id.totalMark) as TextView
            section1 = itemView.findViewById(R.id.section1)
            section2 = itemView.findViewById(R.id.section2)
            section3 = itemView.findViewById(R.id.section3)
            section4 = itemView.findViewById(R.id.section4)
            dayTextView = itemView.findViewById<View>(R.id.dayTextView) as TextView
            dateTextView = itemView.findViewById<View>(R.id.dateTextView) as TextView
        }
    }

}
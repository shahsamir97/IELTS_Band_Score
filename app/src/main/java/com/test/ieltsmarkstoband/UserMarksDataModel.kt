package com.test.ieltsmarkstoband

import android.text.TextUtils

class UserMarksDataModel {

    var score: String? = null
    var section1: String? = null
    var section2: String? = null
    var section3: String? = null
    var section4: String? = null
    var totalMark: String? = null
    var date: String? = null
    var day: String? = null
    var module: String? = null


    constructor() {}
    constructor(score: String?, totalMark: String?, date: String?, day: String?, module: String?) {
        this.score = score
        this.totalMark = totalMark
        this.date = date
        this.day = day
        this.module = module
    }

    constructor(score: String?, section1: String?, section2: String?, section3: String?,
                section4: String?, totalMark: String?, date: String?, day: String?,
                module: String?) {
        this.score = score
        this.section1 = section1
        this.section2 = section2
        this.section3 = section3
        this.section4 = section4
        this.totalMark = totalMark
        this.date = date
        this.day = day
        this.module = module
    }







}
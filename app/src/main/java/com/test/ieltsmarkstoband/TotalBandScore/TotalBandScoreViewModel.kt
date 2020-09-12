package com.test.ieltsmarkstoband

import android.util.Log
import androidx.lifecycle.ViewModel

class TotalBandScoreViewModel : ViewModel() {

    var score = ""

    init {
        Log.i("GameViewModel", "GameViewModel created!")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel cleared!")
    }


}
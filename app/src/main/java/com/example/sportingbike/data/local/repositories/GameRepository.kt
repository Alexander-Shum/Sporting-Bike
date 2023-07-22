package com.example.sportingbike.data.local.repositories

import android.content.SharedPreferences
import com.example.sportingbike.utils.Constants

class GameRepository(private val sharedPreferences: SharedPreferences) {

    fun saveScoreRecord(record: Int?){
        val rec = record as Int
        if(!sharedPreferences.contains(Constants.SCORE_BIKE))
            sharedPreferences.edit().putInt(Constants.SCORE_BIKE, rec).apply()
        else{
            if (rec >= loadScoreRecord()) {
                sharedPreferences.edit().putInt(Constants.SCORE_BIKE, rec).apply()
            }
        }
    }

    fun loadScoreRecord() : Int{
        return sharedPreferences.getInt(Constants.SCORE_BIKE, 0)
    }
}
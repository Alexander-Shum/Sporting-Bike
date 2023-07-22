package com.example.sportingbike.ui.fragment.game

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GameViewModelFactory(
    private val application: Application,
    private val screenHeight: Int,
    private val screenWidth: Int,
    private val playerHeight: Int,
    private val playerWidth: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(application, screenHeight, screenWidth, playerHeight, playerWidth) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

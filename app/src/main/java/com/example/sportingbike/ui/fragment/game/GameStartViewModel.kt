package com.example.sportingbike.ui.fragment.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sportingbike.R

class GameStartViewModel : ViewModel() {
    private val _images = arrayOf(R.drawable.player1, R.drawable.player2, R.drawable.player3)
    private val _speeds = ArrayList<Int>()

    private val _currentImage = MutableLiveData<Int>(0)
    val currentImage: LiveData<Int>
        get() = _currentImage

    private val _currentSpeed = MutableLiveData<Int>(0)
    val currentSpeed: LiveData<Int>
        get() = _currentSpeed

    private val _selectedImage = MutableLiveData<Int>(0)
    val selectedImage: LiveData<Int>
        get() = _selectedImage

    private val _selectedSpeed = MutableLiveData<Int>(0)
    val selectedSpeed: LiveData<Int>
        get() = _selectedSpeed

    fun rightArrowSkin(): Int {
        _currentImage.value = _currentImage.value!! + 1
        if (_currentImage.value!! >= _images.size) {
            _currentImage.value = 0
        }
        _selectedImage.value = _images[_currentImage.value!!]
        return _images[_currentImage.value!!]
    }

    fun leftArrowSkin(): Int {
        _currentImage.value = _currentImage.value!! - 1
        if (_currentImage.value!! < 0) {
            _currentImage.value = _images.size - 1
        }
        _selectedImage.value = _images[_currentImage.value!!]
        return _images[_currentImage.value!!]
    }

    fun rightArrowSpeed(screenHigh: Int): Int {
        if(_speeds.isEmpty()){
            _speeds.add(screenHigh/10)
            _speeds.add(screenHigh/15)
        }
        _currentSpeed.value = _currentSpeed.value!! + 1
        if (_currentSpeed.value!! >= _speeds.size) {
            _currentSpeed.value = 0
        }
        _selectedSpeed.value = _speeds[_currentSpeed.value!!]
        return _speeds[_currentSpeed.value!!]
    }

    fun leftArrowSpeed(screenHigh: Int): Int {
        if(_speeds.isEmpty()){
            _speeds.add(screenHigh/20)
            _speeds.add(screenHigh/25)
        }
        _currentSpeed.value = _currentSpeed.value!! - 1
        if (_currentSpeed.value!! < 0) {
            _currentSpeed.value = _speeds.size - 1
        }
        _selectedSpeed.value = _speeds[_currentSpeed.value!!]
        return _speeds[_currentSpeed.value!!]
    }
}
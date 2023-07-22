package com.example.sportingbike.ui.fragment.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ResultViewModel : ViewModel() {
    private val _selectedFragment = MutableLiveData<Int>()
    val selectedFragment: LiveData<Int> = _selectedFragment

    fun selectFragment(fragment: Int){
        _selectedFragment.value = fragment
    }


}
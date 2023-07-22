package com.example.sportingbike.ui.fragment.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _selectedFragment = MutableLiveData<Int>()
    val selectedFragment: LiveData<Int> = _selectedFragment

    fun selectFragment(fragment: Int){
        _selectedFragment.value = fragment
    }
}
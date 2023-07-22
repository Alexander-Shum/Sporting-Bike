package com.example.sportingbike.ui.fragment.game

import android.app.Application
import android.graphics.Rect
import android.preference.PreferenceManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sportingbike.data.local.repositories.GameRepository
import kotlin.random.Random

class GameViewModel(application: Application) : AndroidViewModel(application) {
    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application)
    private val gameRepository = GameRepository(sharedPreferences)
    private var _screenHigh = 0
    private var _screenWidth = 0

    constructor(
        application: Application,
        screenHeight: Int,
        screenWidth: Int,
        playerHeight: Int,
        playerWidth: Int
    ) : this(application) {
        _playerX.value = screenWidth / 2 - playerWidth / 2 - 200
        _playerY.value = screenHeight - playerHeight - 350
        _screenHigh = screenHeight
        _screenWidth = screenWidth
        _coinX.value = 0
        _coinY.value = 0
        _rockX.value = 0
        _rockY.value = 0
        _score.value = 0
        _health.value = 3
        _gameOver.value = false
    }

    private val _playerX = MutableLiveData<Int>()
    val playerX: LiveData<Int>
        get() = _playerX

    private val _playerY = MutableLiveData<Int>()
    val playerY: LiveData<Int>
        get() = _playerY

    private val _coinX = MutableLiveData<Int>()
    val coinX: LiveData<Int>
        get() = _coinX

    private val _coinY = MutableLiveData<Int>()
    val coinY: LiveData<Int>
        get() = _coinY

    private val _rockX = MutableLiveData<Int>()
    val rockX: LiveData<Int>
        get() = _rockX

    private val _rockY = MutableLiveData<Int>()
    val rockY: LiveData<Int>
        get() = _rockY

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    private val _highScore = MutableLiveData<Int>()
    val highScore: LiveData<Int>
        get() = _highScore

    private val _gameOver = MutableLiveData<Boolean>()
    val gameOver: LiveData<Boolean>
        get() = _gameOver

    private val _health = MutableLiveData<Int>()
    val health: LiveData<Int>
        get() = _health

    private val _speed = MutableLiveData<Int>()
    val speed: LiveData<Int>
        get() = _speed

    fun movePlayerLeft(screenHeight: Int) {
        _playerY.value = screenHeight - screenHeight/4
        if (_playerX.value!! - 100 >= 0) {
            _playerX.value = _playerX.value!! - 100
        }
    }

    fun movePlayerRight(screenHeight: Int, screenWidth: Int, playerWidth: Int) {
        _playerY.value = screenHeight - screenHeight/4
        if (_playerX.value!! + 100 + playerWidth <= screenWidth) {
            _playerX.value = _playerX.value!! + 100
        }
    }

    fun updatePositions(
        coinHeight: Int,
        coinWidth: Int,
        rockHeight: Int,
        rockWidth: Int,
        screenHeight: Int,
        screenWidht: Int
    ) {
        _coinY.value = _coinY.value!! + _speed.value!!
        if (_coinY.value!! + coinHeight >= screenHeight) {
            _coinX.value = Random.nextInt(screenWidht - coinWidth)
            _coinY.value = 0
        }

        _rockY.value = _rockY.value!! + _speed.value!!
        if (_rockY.value!! + rockHeight >= screenHeight) {
            _rockX.value = Random.nextInt(screenWidht - rockWidth)
            _rockY.value = 0
        }

        if (checkCollisionCoin()) {
            _score.value = _score.value!! + 1
            _coinX.value = (0..screenHeight - coinHeight).random()
            _coinY.value = 0
        }

        if (_health.value == 0)
            _gameOver.value = true

        if (checkCollisionRock()) {
            _health.value = _health.value!! - 1
            _rockX.value = (0..screenHeight - coinHeight).random()
            _rockY.value = 0
        }
    }

    private fun checkCollisionCoin(): Boolean {
        val playerRect = Rect(
            _playerX.value!!,
            _playerY.value!!,
            _playerX.value!! + _screenHigh/5,
            _playerY.value!! + _screenHigh/5
        )

        val coinRect = Rect(
            _coinX.value!!,
            _coinY.value!!,
            _coinX.value!! + _screenHigh/10,
            _coinY.value!! + _screenHigh/10
        )

        return coinRect.intersect(playerRect)
    }

    private fun checkCollisionRock(): Boolean {
        val playerRect = Rect(
            _playerX.value!!,
            _playerY.value!!,
            _playerX.value!! + _screenHigh/5,
            _playerY.value!! + _screenWidth/5
        )

        val rockRect = Rect(
            _rockX.value!!,
            _rockY.value!!,
            _rockX.value!! + _screenHigh/10,
            _rockY.value!! + _screenHigh/10
        )

        return rockRect.intersect(playerRect)
    }

    fun initSpeed(speed: Int){
        _speed.value = speed
    }

    fun saveRecordScore() {
        gameRepository.saveScoreRecord(_score.value)
    }

    fun loadRecordScore() {
        _highScore.value = gameRepository.loadScoreRecord()
    }

    fun reset(){
        _coinX.value = 0
        _coinY.value = 0
        _rockX.value = 0
        _rockY.value = 0
        _score.value = 0
        _health.value = 3
        _gameOver.value = false
    }

    companion object {
        const val PLAYER_WIDTH = 400
        const val PLAYER_HEIGHT = 400
        const val COIN_WIDTH = 250
        const val COIN_HEIGHT = 250
        const val ROCK_WIDTH = 250
        const val ROCK_HEIGHT = 250
    }
}
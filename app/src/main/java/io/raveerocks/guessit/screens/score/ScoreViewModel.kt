package io.raveerocks.guessit.screens.score

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class ScoreViewModel(finalScore: Int) : ViewModel() {

    private var _score = MutableLiveData<String>()
    private var _eventPlayAgain = MutableLiveData<Boolean>()

    val score: LiveData<String>
        get() = _score
    val eventPlayAgain: LiveData<Boolean>
        get() = _eventPlayAgain

    init {
        _score.value = finalScore.toString()
    }

    fun onPlayAgain() {
        _eventPlayAgain.value = true
    }

    fun onPlayAgainComplete() {
        _eventPlayAgain.value = false
    }

}
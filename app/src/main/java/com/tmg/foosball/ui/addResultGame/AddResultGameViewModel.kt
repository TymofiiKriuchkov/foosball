package com.tmg.foosball.ui.addResultGame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tmg.foosball.R
import com.tmg.foosball.data.Repository
import com.tmg.foosball.model.GameResultModel
import com.tmg.foosball.utils.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class AddResultGameViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    val _gameModelLiveData = MutableLiveData<GameResultModel>()
    val gameModelLiveData: LiveData<GameResultModel>
        get() = _gameModelLiveData

    var showMessage = SingleLiveEvent<Int>()
    var navigateEvent = SingleLiveEvent<Boolean>()

    private val disposable = CompositeDisposable()

    fun saveGameResult(
        player1Name: String,
        player1Score: String,
        player2Name: String,
        player2Score: String
    ) {
        if (player1Name.isEmpty() || player1Score.isEmpty()
            || player2Name.isEmpty() || player2Score.isEmpty()
        ) {
            showMessage.value = R.string.error_message_empty_fields
            return
        }

        if (_gameModelLiveData.value == null)
            repository.addGameResult(
                GameResultModel(
                    player1Name, player1Score.toInt(),
                    player2Name, player2Score.toInt(),
                    repository.getGameResults() + 1
                )
            )
        else
            repository.updateGameResult(
                GameResultModel(
                    player1Name, player1Score.toInt(),
                    player2Name, player2Score.toInt(),
                    _gameModelLiveData.value!!.id
                )
            )
        navigateEvent.value = true
    }

    fun initGameResult(gameId: Int) {
        if (gameId != GAME_NOT_FOUND) {
            _gameModelLiveData.value = repository.getGameById(gameId)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

}
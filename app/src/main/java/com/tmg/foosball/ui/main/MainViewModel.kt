package com.tmg.foosball.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tmg.foosball.R
import com.tmg.foosball.data.Repository
import com.tmg.foosball.model.GameResultModel
import com.tmg.foosball.model.WinnerModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class MainViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    private val _winnersLiveData = MutableLiveData<ArrayList<WinnerModel>>()
    val winnersLiveData: LiveData<ArrayList<WinnerModel>>
        get() = _winnersLiveData


    private val _sortedTypeTitle = MutableLiveData<Int>()
    val sortedTypeTitle: LiveData<Int>
        get() = _sortedTypeTitle

    private val disposable = CompositeDisposable()

    init {
        calculateWinners()
    }

    fun onSortByGamesClicked() {
        _sortedTypeTitle.value = R.string.winners_sorted_by_games_quantity
        calculateWinners()
    }

    fun onSortByWinsClicked() {
        _sortedTypeTitle.value = R.string.winners_sorted_by_wins
        calculateWinners(true)
    }

    fun calculateWinners(sortByWins: Boolean = false) {
        repository.subjectGameResultModels.subscribe { list ->
            disposable.add(
                Observable.fromArray(list)
                    .flatMapIterable { participants ->
                        participants
                    }
                    .collectInto(mutableMapOf<String, Int>()) { map, itemOfGame ->
                        if (sortByWins)
                            sortByWins(map, itemOfGame)
                        else
                            sortByGames(map, itemOfGame)
                    }
                    .toObservable()
                    .flatMapIterable { it.entries }
                    .map { WinnerModel(position = it.value, name = it.key) }
                    .toSortedList { p1, p2 ->
                        p2.position - p1.position
                    }
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ items ->
                        _winnersLiveData.value = (items as ArrayList<WinnerModel>?)
                    }, { throwable ->
                        //..
                    })
            )
        }.addTo(disposable)
    }

    private fun sortByGames(map: MutableMap<String, Int>, gameResultModel: GameResultModel) {
        val player1 = gameResultModel.player1Name
        val player2 = gameResultModel.player2Name

        //add both players
        if (map.contains(player1)) {
            val currentGames = map[player1]
            map[player1] = currentGames!! + 1
        } else map[player1] = 1

        if (map.contains(player2)) {
            val currentGames = map[player2]
            map[player2] = currentGames!! + 1
        } else map[player2] = 1
    }

    private fun sortByWins(map: MutableMap<String, Int>, gameResultModel: GameResultModel) {
        var keyOfWinner = gameResultModel.player1Name
        var keyOfLoser = gameResultModel.player2Name
        if (gameResultModel.player1Score < gameResultModel.player2Score) {
            keyOfWinner = gameResultModel.player2Name
            keyOfLoser = gameResultModel.player1Name
        }
        if (map.contains(keyOfWinner)) {
            val currentWins = map[keyOfWinner]
            map[keyOfWinner] = currentWins!! + 1
        } else map[keyOfWinner] = 1

        //need to add a loser if it's not exist
        if (!map.contains(keyOfLoser))
            map[keyOfLoser] = 0
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}
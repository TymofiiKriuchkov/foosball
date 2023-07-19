package com.tmg.foosball.ui.allGames

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tmg.foosball.data.Repository
import com.tmg.foosball.model.GameResultModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AllGamesViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {

    private val _gamesResultsLiveData = MutableLiveData<ArrayList<GameResultModel>>()
    val gamesResultsLiveData: LiveData<ArrayList<GameResultModel>>
        get() = _gamesResultsLiveData

    private val disposable = CompositeDisposable()

    init {
        initHistoricalGameList()
    }

    fun initHistoricalGameList() {
        repository.subjectGameResultModels.subscribe { list ->
            disposable.add(
                Observable.fromArray(list)
                    .flatMapIterable { participants ->
                        participants
                    }
                    .toSortedList { p1, p2 ->
                        p2.id.compareTo(p1.id)
                    }
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ items ->
                        _gamesResultsLiveData.value = items as ArrayList<GameResultModel>?
                    }, { throwable ->
                        //..
                    })
            )
        }.addTo(disposable)
    }

    fun onDeleteItemClicked(position: Int) {
        val gamesArray = _gamesResultsLiveData.value
        val elementToRemove = gamesArray!![position]
        repository.removeElement(elementToRemove)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}
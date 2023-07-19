package com.tmg.foosball.data

import com.tmg.foosball.model.GameResultModel
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class Repository @Inject constructor(
    private val localDataSource: LocalDataSource,

    //In real life we could have also data from remote
    private val remoteDataSource: RemoteDataSource
) {

    var subjectGameResultModels: BehaviorSubject<ArrayList<GameResultModel>> =
        BehaviorSubject.create()

    init {
        //init from local data source
        subjectGameResultModels.onNext(localDataSource.getElements())
    }

    fun addGameResult(model: GameResultModel) {
        subjectGameResultModels.value?.let { gameResultModels ->
            gameResultModels.add(model)
            subjectGameResultModels.onNext(gameResultModels)
        }
    }

    fun getGameResults(): Int {
        subjectGameResultModels.value?.let { gameResultModels ->
            return gameResultModels.size
        }
        return 0
    }

    fun updateGameResult(model: GameResultModel) {
        subjectGameResultModels.value?.let { gameResultModels ->
            for (i in 0 until gameResultModels.size) {
                if (model.id == gameResultModels[i].id) {
                    gameResultModels[i] = model
                    subjectGameResultModels.onNext(gameResultModels)
                    break
                }
            }
        }
    }

    fun removeElement(elementToRemove: GameResultModel) {
        subjectGameResultModels.value?.let { gameResultModels ->
            gameResultModels.remove(elementToRemove)
            subjectGameResultModels.onNext(gameResultModels)
        }
    }

    fun getGameById(gameId: Int): GameResultModel? {
        subjectGameResultModels.value?.let { gameResultModels ->
            for (i in 0 until gameResultModels.size) {
                if (gameId == gameResultModels[i].id)
                    return gameResultModels[i]
            }
        }
        return null
    }
}
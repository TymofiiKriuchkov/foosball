package com.tmg.foosball.data

import com.tmg.foosball.model.GameResultModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor() {

    //by games:
    // 1. Diego
    // 2. Amos
    // 3. Joel

    //by wins:
    // 1. Diego
    // 2. Amos
    // 3. Joel
    fun getElements(): ArrayList<GameResultModel> {
        val gameResults = ArrayList<GameResultModel>()

        gameResults.add(
            GameResultModel("Amos", 4, "Diego", 5, 0)
        )
        gameResults.add(
            GameResultModel("Amos", 1, "Diego", 5, 1)
        )
        gameResults.add(
            GameResultModel("Amos", 2, "Diego", 5, 2)
        )
        gameResults.add(
            GameResultModel("Amos", 0, "Diego", 5, 3)
        )
        gameResults.add(
            GameResultModel("Amos", 6, "Diego", 5, 4)
        )
        gameResults.add(
            GameResultModel("Amos", 1, "Diego", 2, 5)
        )
        gameResults.add(
            GameResultModel("Amos", 2, "Diego", 3, 6)
        )
        gameResults.add(
            GameResultModel("Joel", 4, "Diego", 5, 7)
        )
        gameResults.add(
            GameResultModel("Tim", 4, "Amos", 5, 8)
        )
        gameResults.add(
            GameResultModel("Tim", 5, "Amos", 2, 9)
        )
        gameResults.add(
            GameResultModel("Amos", 3, "Tim", 5, 10)
        )
        gameResults.add(
            GameResultModel("Amos", 5, "Tim", 3, 11)
        )
        gameResults.add(
            GameResultModel("Amos", 5, "Joel", 4, 12)
        )
        gameResults.add(
            GameResultModel("Joel", 5, "Tim", 2, 13)
        )

        return gameResults
    }
}
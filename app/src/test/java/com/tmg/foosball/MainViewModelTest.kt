package com.tmg.foosball

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tmg.foosball.data.Repository
import com.tmg.foosball.model.GameResultModel
import com.tmg.foosball.model.WinnerModel
import com.tmg.foosball.rule.RxImmediateSchedulerRule
import com.tmg.foosball.ui.main.MainViewModel
import io.reactivex.subjects.BehaviorSubject
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var viewModel: MainViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    private val gameModel = GameResultModel(
        "a", 1, "b",
        3, 1
    )
    private val gameModel2 = GameResultModel(
        "a", 1, "b",
        3, 2
    )
    private val gameModel3 = GameResultModel(
        "a", 1, "b",
        3, 3
    )
    private val gameModel4 = GameResultModel(
        "a", 1, "c",
        3, 4
    )


    @Before
    fun setUp() {
        val mockSubject: BehaviorSubject<ArrayList<GameResultModel>> =
            BehaviorSubject.create()
        Mockito.`when`(repository.subjectGameResultModels).thenReturn(mockSubject)

        viewModel = MainViewModel(repository = repository)
    }

    @Test
    fun calculate_winners_by_win() {
        val gameResultArray = ArrayList<GameResultModel>()
        gameResultArray.add(gameModel)
        gameResultArray.add(gameModel2)
        val mockSubject: BehaviorSubject<ArrayList<GameResultModel>> =
            BehaviorSubject.create()
        mockSubject.onNext(gameResultArray)
        Mockito.`when`(viewModel.repository.subjectGameResultModels).thenReturn(mockSubject)

        val winnerModel = ArrayList<WinnerModel>()
        winnerModel.add(WinnerModel(2, "b"))
        winnerModel.add(WinnerModel(0, "a"))

        viewModel.calculateWinners(true)
        Assert.assertEquals(viewModel.winnersLiveData.value, winnerModel)
    }

    @Test
    fun calculate_winners_by_game_number() {
        val gameResultArray = ArrayList<GameResultModel>()
        gameResultArray.add(gameModel)
        gameResultArray.add(gameModel2)
        gameResultArray.add(gameModel3)
        gameResultArray.add(gameModel4)

        val mockSubject: BehaviorSubject<ArrayList<GameResultModel>> =
            BehaviorSubject.create()
        mockSubject.onNext(gameResultArray)
        Mockito.`when`(viewModel.repository.subjectGameResultModels).thenReturn(mockSubject)

        val winnerModel = ArrayList<WinnerModel>()
        winnerModel.add(WinnerModel(4, "a"))
        winnerModel.add(WinnerModel(3, "b"))
        winnerModel.add(WinnerModel(1, "c"))

        viewModel.calculateWinners()
        Assert.assertEquals(viewModel.winnersLiveData.value, winnerModel)
    }
}
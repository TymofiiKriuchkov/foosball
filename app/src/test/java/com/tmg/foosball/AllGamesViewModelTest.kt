package com.tmg.foosball

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tmg.foosball.data.Repository
import com.tmg.foosball.model.GameResultModel
import com.tmg.foosball.rule.RxImmediateSchedulerRule
import com.tmg.foosball.ui.allGames.AllGamesViewModel
import io.reactivex.subjects.BehaviorSubject
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class AllGamesViewModelTest {

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var viewModel: AllGamesViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Before
    fun setUp() {
        val mockSubject: BehaviorSubject<ArrayList<GameResultModel>> =
            BehaviorSubject.create()
        `when`(repository.subjectGameResultModels).thenReturn(mockSubject)

        viewModel = AllGamesViewModel(repository = repository)
    }

    private val gameModel = GameResultModel(
        "a", 1,
        "b", 3, 1
    )
    private val gameModel2 = GameResultModel(
        "a", 1,
        "b", 3, 2
    )

    @Test
    fun init_historical_game_list() {
        val gameResultArray = ArrayList<GameResultModel>()
        gameResultArray.add(gameModel)
        val mockSubject: BehaviorSubject<ArrayList<GameResultModel>> =
            BehaviorSubject.create()

        mockSubject.onNext(gameResultArray)
        `when`(viewModel.repository.subjectGameResultModels).thenReturn(mockSubject)
        viewModel.initHistoricalGameList()
        assertEquals(viewModel.gamesResultsLiveData.value, gameResultArray)
    }

    @Test
    fun filter_according_position_historical_game_list() {
        val gameResultArray = ArrayList<GameResultModel>()
        gameResultArray.add(gameModel)
        gameResultArray.add(gameModel2)

        val mockSubject: BehaviorSubject<ArrayList<GameResultModel>> =
            BehaviorSubject.create()

        mockSubject.onNext(gameResultArray)
        `when`(viewModel.repository.subjectGameResultModels).thenReturn(mockSubject)
        viewModel.initHistoricalGameList()
        assertEquals(
            (viewModel.gamesResultsLiveData.value
                    as ArrayList<GameResultModel>)[1].id, 1
        )
    }

}
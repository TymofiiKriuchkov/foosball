package com.tmg.foosball


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tmg.foosball.data.Repository
import com.tmg.foosball.model.GameResultModel
import com.tmg.foosball.ui.addResultGame.AddResultGameViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AddResultGameViewModelTest : Assert() {

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var viewModel: AddResultGameViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        viewModel = AddResultGameViewModel(repository = repository)
    }

    private val gameModel = GameResultModel("a", 1, "b", 3, 1)

    @Test
    fun check_if_all_fields_are_empty() {
        viewModel.saveGameResult("", "", "", "")
        assertEquals(viewModel.showMessage.value, R.string.error_message_empty_fields)
    }

    @Test
    fun save_game_result_navigate_next() {
        viewModel.saveGameResult("a", "1", "b", "3")
        assertEquals(viewModel.navigateEvent.value, true)
    }

    @Test
    fun update_result_game() {
        viewModel._gameModelLiveData.value = gameModel
        viewModel.saveGameResult("a", "1", "b", "3")
        verify(repository, times(1)).updateGameResult(gameModel)
    }

    @Test
    fun save_new_result_game() {
        viewModel._gameModelLiveData.value = null
        viewModel.saveGameResult("a", "1", "b", "3")
        verify(viewModel.repository, times(1)).addGameResult(gameModel)
    }

    @Test
    fun negative_save_new_result_game() {
        viewModel._gameModelLiveData.value = gameModel
        viewModel.saveGameResult("a", "1", "b", "3")
        verify(viewModel.repository, times(0)).addGameResult(gameModel)
    }

    @Test
    fun update_game_result_game() {
        viewModel._gameModelLiveData.value = gameModel
        viewModel.saveGameResult("a", "1", "b", "3")
        verify(viewModel.repository, times(1)).updateGameResult(gameModel)
    }

}
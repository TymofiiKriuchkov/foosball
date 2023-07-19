package com.tmg.foosball.ui.addResultGame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.tmg.foosball.databinding.AddResultGameDialogFragmentBinding
import com.tmg.foosball.di.Injectable
import com.tmg.foosball.ui.core.BaseDialogFragment
import javax.inject.Inject


const val GAME_NOT_FOUND = -1

class AddResultGameDialogFragment : BaseDialogFragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: AddResultGameViewModel by viewModels { viewModelFactory }

    private var _binding: AddResultGameDialogFragmentBinding? = null
    private val binding get() = _binding!!

    private val args: AddResultGameDialogFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AddResultGameDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gameId = args.gameId
        viewModel.initGameResult(gameId)

        viewModel.gameModelLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.etPlayer1Name.setText(it.player1Name)
                binding.etPlayer1Score.setText(it.player1Score.toString())
                binding.etPlayer2Name.setText(it.player2Name)
                binding.etPlayer2Score.setText(it.player2Score.toString())
            }
        }

        viewModel.showMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it!!, Toast.LENGTH_SHORT).show()
        }

        viewModel.navigateEvent.observe(viewLifecycleOwner) {
            dismiss()
        }

        binding.saveBtn.setOnClickListener {
            viewModel.saveGameResult(
                binding.etPlayer1Name.text.toString(),
                binding.etPlayer1Score.text.toString(),
                binding.etPlayer2Name.text.toString(),
                binding.etPlayer2Score.text.toString()
            )

        }

        binding.cancelAction.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
package com.tmg.foosball.ui.allGames


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.tmg.foosball.databinding.FragmentAllGamesBinding
import com.tmg.foosball.di.Injectable
import com.tmg.foosball.ui.addResultGame.GAME_NOT_FOUND
import com.tmg.foosball.ui.core.BaseFragment
import javax.inject.Inject


class AllGamesFragment : BaseFragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: AllGamesViewModel by viewModels { viewModelFactory }

    private var _binding: FragmentAllGamesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllGamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.gamesResultsLiveData.observe(viewLifecycleOwner) {
            val adapter = GamesAdapter(it, object : OnItemClickListener {
                override fun onItemClicked(itemId: Int) {
                    val action = AllGamesFragmentDirections
                        .actionAllGamesFragmentToAddGameFragmentResult(itemId)
                    view.findNavController().navigate(action)
                }

            }, object : OnItemLongClickListener {
                override fun onItemLongClicked(position: Int) {
                    showRemoveItemDialog(position)
                }

            })
            binding.rvGames.adapter = adapter
        }

        binding.floatingActionButtonAddGameResult.setOnClickListener {
            val action = AllGamesFragmentDirections
                .actionAllGamesFragmentToAddGameFragmentResult(GAME_NOT_FOUND)
            view.findNavController().navigate(action)
        }

    }

    fun showRemoveItemDialog(position: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete entry")
            .setMessage("Are you sure you want to delete this entry?")
            .setPositiveButton(
                android.R.string.ok
            ) { _, _ ->
                viewModel.onDeleteItemClicked(position)
            }
            .setNegativeButton(android.R.string.cancel, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
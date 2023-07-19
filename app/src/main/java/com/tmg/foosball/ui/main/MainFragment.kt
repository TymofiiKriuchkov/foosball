package com.tmg.foosball.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.tabs.TabLayout
import com.tmg.foosball.databinding.FragmentMainBinding
import com.tmg.foosball.di.Injectable
import com.tmg.foosball.ui.core.BaseFragment
import javax.inject.Inject

class MainFragment : BaseFragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MainViewModel by viewModels {
        viewModelFactory
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.winnersLiveData.observe(viewLifecycleOwner) {
            val adapter = CustomAdapter(it)
            binding.rvResults.visibility = View.VISIBLE
            binding.rvResults.adapter = adapter
        }

        viewModel.sortedTypeTitle.observe(viewLifecycleOwner) {
            binding.message.text = getString(it)
        }

        binding.btnAllGames.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToAllGamesFragment()
            view.findNavController().navigate(action)
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.rvResults.visibility = View.GONE

                when (tab.position) {
                    0 -> {
                        viewModel.onSortByGamesClicked()
                    }
                    1 -> {
                        viewModel.onSortByWinsClicked()
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselect
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
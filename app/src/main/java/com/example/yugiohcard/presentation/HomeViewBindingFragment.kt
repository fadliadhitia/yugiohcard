package com.example.yugiohcard.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.yugiohcard.R
import com.example.yugiohcard.adapter.CardRecyclerViewAdapter
import com.example.yugiohcard.databinding.FragmentHomeBinding
import com.example.yugiohcard.module.ApiResult
import com.example.yugiohcard.module.ViewBindingFragment
import kotlinx.coroutines.launch

// 7/14/2022
// 10119907
// T DONI INDRAPRASTA PRADANA
// IF10K
class HomeViewBindingFragment : ViewBindingFragment<FragmentHomeBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding = FragmentHomeBinding::inflate

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerViewAdapter = CardRecyclerViewAdapter()
        initSwipeLayout()
        initSearchEvent(recyclerViewAdapter)
        initRecyclerView(recyclerViewAdapter)
        initShardFlowEvent(recyclerViewAdapter)
        homeViewModel.fetch()
    }

    private fun initSwipeLayout() {
        binding.swipeLayout.setColorSchemeResources(R.color.purple_200)
        binding.swipeLayout.setOnRefreshListener {
            homeViewModel.fetch()
            binding.swipeLayout.isRefreshing = false
        }
    }

    private fun initSearchEvent(adapter: CardRecyclerViewAdapter) {
        binding.searchBar.maxWidth = Int.MAX_VALUE

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun initRecyclerView(adapter: CardRecyclerViewAdapter) {
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                binding.swipeLayout.isEnabled =
                    layoutManager.findFirstCompletelyVisibleItemPosition() == 0
            }
        })
    }

    private fun initShardFlowEvent(adapter: CardRecyclerViewAdapter) =
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.cardSharedFlow.collect {
                    when (it) {
                        is ApiResult.Success -> {
                            LoadingDialogFragment.dismiss()
                            adapter.submitList(it.response.toList())
                            adapter.setFilteredItems()
                        }
                        is ApiResult.Loading -> {
                            LoadingDialogFragment.show(
                                parentFragmentManager,
                                String()
                            )
                        }
                        is ApiResult.Error -> {
                            LoadingDialogFragment.dismiss()
                            Toast.makeText(
                                context,
                                it.response.throwable.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
}
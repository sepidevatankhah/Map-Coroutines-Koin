package ir.nwise.app.ui.home

import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ir.nwise.app.R
import ir.nwise.app.common.NetworkManager.isOnline
import ir.nwise.app.databinding.FragmentHomeBinding
import ir.nwise.app.ui.base.BaseFragment
import ir.nwise.app.ui.map.MapViewModel
import ir.nwise.app.ui.map.MapViewState
import ir.nwise.app.ui.utils.hide
import ir.nwise.app.ui.utils.show
import ir.nwise.app.ui.utils.toastNoInternetConnection
import ir.nwise.app.ui.utils.toastOopsError
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<MapViewState, MapViewModel, FragmentHomeBinding>() {
    private val homeViewModel: MapViewModel by viewModel()

    private val carAdapter: CarAdapter =
        CarAdapter(
            onItemClicked = { model ->
                binding.root.findNavController().navigate(
                    HomeFragmentDirections.openDetail()
                )
            }
        )

    override fun getLayout(): Int = R.layout.fragment_home

    override fun onCreateViewCompleted() {
        setHasOptionsMenu(true)
        binding.lifecycleOwner = this@HomeFragment
        setupSwipeRefreshLayout()
        getAllCars()
        initRecyclerView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = homeViewModel
        super.onCreate(savedInstanceState)
    }

    override fun render(state: MapViewState) {
        binding.swipeRefresh.isRefreshing = false
        binding.spinner.hide()
        when (state) {
            is MapViewState.Loading -> {
                if (carAdapter.itemCount == 0) {
                    binding.spinner.show()
                    binding.recyclerView.hide()
                }
            }
            is MapViewState.Loaded -> {
                binding.spinner.hide()
                binding.recyclerView.show()
                carAdapter.submitItems(state.cars)
                carAdapter.notifyDataSetChanged()
            }
            is MapViewState.Error -> {
                binding.swipeRefresh.isRefreshing = false
                binding.spinner.hide()
                Log.e(
                    "HomeFragment",
                    state.throwable.message,
                    state.throwable
                )
                binding.root.toastOopsError()
            }
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = carAdapter
        }
    }

    private fun setupSwipeRefreshLayout() {
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = true
            getAllCars()
        }
    }

    private fun getAllCars() {
        context?.let {
            if (isOnline(it))
                viewModel.getCars()
            else {
                view?.toastNoInternetConnection()
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }
}
package ir.nwise.app.ui.home

import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ir.nwise.app.R
import ir.nwise.app.databinding.FragmentHomeBinding
import ir.nwise.app.ui.base.BaseFragment
import ir.nwise.app.ui.error.ErrorType
import ir.nwise.app.ui.map.MapViewModel
import ir.nwise.app.ui.map.MapViewState
import ir.nwise.app.ui.utils.hide
import ir.nwise.app.ui.utils.show
import ir.nwise.app.ui.widget.ErrorView
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<MapViewState, MapViewModel, FragmentHomeBinding>() {
    private val homeViewModel: MapViewModel by viewModel()
    private var errorView: ErrorView? = null

    private val carAdapter: CarAdapter =
        CarAdapter(
            onItemClicked = { model ->
                binding.root.findNavController().navigate(
                    HomeFragmentDirections.openDetail(model)
                )
            }
        )

    override fun getLayout(): Int = R.layout.fragment_home
    override fun onCreateViewCompleted(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        binding.lifecycleOwner = this@HomeFragment
        setupSwipeRefreshLayout()
        getAllCars()
        initRecyclerView()
        errorView = activity?.findViewById(R.id.error_view)
        errorView?.setButtonListener { getAllCars() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = homeViewModel
        super.onCreate(savedInstanceState)
    }

    override fun render(state: MapViewState) {
        binding.apply {
            swipeRefresh.isRefreshing = false
            spinner.hide()
            errorView?.hide()
            when (state) {
                is MapViewState.Loading -> {
                    if (carAdapter.itemCount == 0) {
                        spinner.show()
                        recyclerView.hide()
                    }
                }
                is MapViewState.Loaded -> {
                    recyclerView.show()
                    errorView?.hide()
                    spinner.hide()
                    recyclerView.show()
                    carAdapter.submitItems(state.cars)
                    carAdapter.notifyDataSetChanged()
                }
                is MapViewState.Error -> {
                    recyclerView.hide()
                    errorView?.show()
                    swipeRefresh.isRefreshing = false
                    spinner.hide()
                    Log.e(
                        "HomeFragment",
                        state.throwable.message,
                        state.throwable
                    )
                    errorView?.show(ErrorType.fromThrowable(state.throwable))
                }
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
        viewModel.getCars()
    }
}
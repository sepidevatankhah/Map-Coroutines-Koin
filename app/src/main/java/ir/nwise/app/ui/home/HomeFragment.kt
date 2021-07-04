package ir.nwise.app.ui.home

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import ir.nwise.app.R
import ir.nwise.app.common.NetworkManager.isOnline
import ir.nwise.app.databinding.FragmentHomeBinding
import ir.nwise.app.ui.base.BaseFragment
import ir.nwise.app.ui.utils.hide
import ir.nwise.app.ui.utils.show
import ir.nwise.app.ui.utils.toastNoInternetConnection
import ir.nwise.app.ui.utils.toastOopsError
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<HomeViewState, HomeViewModel, FragmentHomeBinding>() {
    private val homeViewModel: HomeViewModel by viewModel()

    private val carAdapter: CarAdapter =
        CarAdapter(
            onItemClicked = { model ->
//                binding.root.findNavController().navigate(
//                    HomeFragmentDirections.openDetail(model)
//                )
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

    override fun render(state: HomeViewState) {
        binding.swipeRefresh.isRefreshing = false
        binding.spinner.hide()
        when (state) {
            is HomeViewState.Loading -> {
                if (carAdapter.itemCount == 0) {
                    binding.spinner.show()
                    binding.recyclerView.hide()
                }
            }
            is HomeViewState.Loaded -> {
                binding.spinner.hide()
                binding.recyclerView.show()
                carAdapter.submitItems(state.cars)
                carAdapter.notifyDataSetChanged()
            }
            is HomeViewState.Error -> {
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
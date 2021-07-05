package ir.nwise.app.ui.map

import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ir.nwise.app.R
import ir.nwise.app.common.NetworkManager
import ir.nwise.app.databinding.FragmentMapBinding
import ir.nwise.app.domain.models.Car
import ir.nwise.app.ui.base.BaseFragment
import ir.nwise.app.ui.utils.DialogHelper
import ir.nwise.app.ui.utils.getBitmap
import ir.nwise.app.ui.utils.toastOopsError
import org.koin.android.viewmodel.ext.android.viewModel


class MapFragment : BaseFragment<MapViewState, MapViewModel, FragmentMapBinding>() {
    private val mapViewModel: MapViewModel by viewModel()
    private var googleMap: GoogleMap? = null

    override fun getLayout(): Int = R.layout.fragment_map

    override var callObserverFromOnViewCreated: Boolean = false

    override fun onCreateViewCompleted() {
        getAllCars()
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            this.googleMap = googleMap
            startObserving()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = mapViewModel
        super.onCreate(savedInstanceState)
    }

    override fun render(state: MapViewState) {
        when (state) {
            is MapViewState.Loading -> {
            }
            is MapViewState.Loaded -> {
                showMarkers(state.cars)
            }
            is MapViewState.Error -> {
                Log.e(
                    "MapFragment",
                    state.throwable.message,
                    state.throwable
                )
                binding.root.toastOopsError()
            }
        }
    }

    private fun getAllCars() {
        context?.let {
            if (NetworkManager.isOnline(it))
                viewModel.getCars()
            else {
                DialogHelper.showOfflineNoDataError(requireContext()) { _, _ -> viewModel.getCars() }
            }
        }
    }

    private fun showMarkers(vehicles: List<Car>) {
        vehicles.forEach { vehicle ->
            val location = LatLng(vehicle.latitude, vehicle.longitude)
            getBitmap(requireContext(), vehicle.carImageUrl) { bitmap ->
                googleMap?.addMarker(
                    MarkerOptions()
                        .position(location)
                        .title(vehicle.name)
                        .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                )
            }
        }
        zoomToLastVehicle(vehicles.last())

        googleMap?.setOnInfoWindowClickListener {
            binding.root.findNavController().navigate(MapFragmentDirections.openCarDetail())
        }
    }

    private fun zoomToLastVehicle(lastVehicle: Car) {
        googleMap?.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    lastVehicle.latitude,
                    lastVehicle.longitude
                ), 14F
            )
        )
    }

}
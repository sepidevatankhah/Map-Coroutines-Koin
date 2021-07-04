package ir.nwise.app.ui.main

import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ir.nwise.app.R
import ir.nwise.app.databinding.MainFragmentBinding
import ir.nwise.app.domain.models.Car
import ir.nwise.app.ui.base.BaseFragment
import ir.nwise.app.ui.utils.getBitmap
import ir.nwise.app.ui.utils.toastOopsError
import org.koin.android.viewmodel.ext.android.viewModel


class MainFragment : BaseFragment<MapViewState, MapViewModel, MainFragmentBinding>() {
    companion object {
        fun newInstance() = MainFragment()
    }

    private val mapViewModel: MapViewModel by viewModel()
    private var googleMap: GoogleMap? = null

    override fun getLayout(): Int = R.layout.main_fragment

    override var callObserverFromOnViewCreated: Boolean = false

    override fun onCreateViewCompleted() {
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
        viewModel.getCars()
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

    private fun showMarkers(vehicles: List<Car>) {
        vehicles.forEach { vehicle ->
            val location = LatLng(vehicle.latitude, vehicle.longitude)
            getBitmap(requireContext(), vehicle.carImageUrl) { bitmap ->
                googleMap?.let {
                    val mapMarker = it.addMarker(
                        MarkerOptions()
                            .position(location)
                            .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                            .title(vehicle.make)
                    )
                    mapMarker.showInfoWindow()

                }
            }

        }
        zoomToLastVehicle(vehicles.last())
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
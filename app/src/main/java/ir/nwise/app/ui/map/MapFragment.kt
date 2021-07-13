package ir.nwise.app.ui.map

import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ir.nwise.app.R
import ir.nwise.app.databinding.FragmentMapBinding
import ir.nwise.app.domain.models.Car
import ir.nwise.app.ui.base.BaseFragment
import ir.nwise.app.ui.error.ErrorType
import ir.nwise.app.ui.utils.getBitmap
import ir.nwise.app.ui.utils.hide
import ir.nwise.app.ui.utils.show
import ir.nwise.app.ui.widget.ErrorView
import org.koin.android.viewmodel.ext.android.viewModel


class MapFragment : BaseFragment<MapViewState, MapViewModel, FragmentMapBinding>() {
    private val mapViewModel: MapViewModel by viewModel()
    private var googleMap: GoogleMap? = null
    private var errorView: ErrorView? = null

    override fun getLayout(): Int = R.layout.fragment_map

    override var callObserverFromOnViewCreated: Boolean = false

    override fun onCreateViewCompleted(savedInstanceState: Bundle?) {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment

        errorView = activity?.findViewById(R.id.error_view)
        errorView?.setButtonListener { getAllCars() }

        mapFragment.getMapAsync { googleMap ->
            getAllCars()
            this.googleMap = googleMap
            startObserving()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = mapViewModel
        super.onCreate(savedInstanceState)
    }

    override fun render(state: MapViewState) {
        binding.apply {
            errorView?.hide()
            when (state) {
                is MapViewState.Loading -> {
                    spinner.show()
                    mapFragment.hide()
                }
                is MapViewState.Loaded -> {
                    errorView?.hide()
                    spinner.hide()
                    mapFragment.show()
                    showMarkers(state.cars)
                }
                is MapViewState.Error -> {
                    spinner.hide()
                    mapFragment.hide()
                    Log.e(
                        "MapFragment",
                        state.throwable.message,
                        state.throwable
                    )
                    errorView?.show(ErrorType.fromThrowable(state.throwable))
                }
            }
        }
    }

    private fun getAllCars() {
        viewModel.getCars()
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
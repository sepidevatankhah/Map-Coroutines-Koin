package ir.nwise.app.ui.map

import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import ir.nwise.app.R
import ir.nwise.app.databinding.FragmentMapBinding
import ir.nwise.app.domain.models.Car
import ir.nwise.app.ui.base.BaseFragment
import ir.nwise.app.ui.utils.getBitmap
import ir.nwise.app.ui.utils.toastOopsError
import org.koin.android.viewmodel.ext.android.viewModel


class MapFragment : BaseFragment<MapViewState, MapViewModel, FragmentMapBinding>() {
    private val mapViewModel: MapViewModel by viewModel()
    private var googleMap: GoogleMap? = null

    override fun getLayout(): Int = R.layout.fragment_map

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
        val markers: MutableMap<Marker, Car> = HashMap()
        vehicles.forEach { vehicle ->
            val location = LatLng(vehicle.latitude, vehicle.longitude)
            val mapMarker = googleMap?.addMarker(
                MarkerOptions()
                    .position(location)
                    .title(vehicle.name)
            )
            mapMarker?.let { marker -> markers.put(marker, vehicle) }
            getBitmap(requireContext(), vehicle.carImageUrl) { bitmap ->
                mapMarker?.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap))
            }
            mapMarker?.showInfoWindow()
        }
        zoomToLastVehicle(vehicles.last())

//        googleMap?.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
//            override fun getInfoContents(markerItem: Marker?): View {
//                // Getting view from the layout file
//                val rootViewBinding: ItemMapMarkerWindowBinding =
//                    DataBindingUtil.inflate(
//                        layoutInflater,
//                        R.layout.item_map_marker_window,
//                        null,
//                        false
//                    )
//                rootViewBinding.vehicleNameText.text = markers[markerItem]?.name
//                rootViewBinding.plateText.text = markers[markerItem]?.licensePlate
//                rootViewBinding.iconImage.loadUrl(markers[markerItem]?.carImageUrl)
//                return rootViewBinding.root
//            }
//
//            override fun getInfoWindow(markerItem: Marker?): View? {
//                return null
//            }
//        })
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
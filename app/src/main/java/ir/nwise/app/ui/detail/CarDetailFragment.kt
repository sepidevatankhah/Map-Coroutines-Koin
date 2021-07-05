package ir.nwise.app.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import ir.nwise.app.R
import ir.nwise.app.databinding.FragmentCarDetailBinding
import ir.nwise.app.ui.utils.loadUrl

class CarDetailFragment : Fragment() {
    private lateinit var binding: FragmentCarDetailBinding
    private val args :CarDetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_car_detail, container, false)
        showDetail()
        return binding.root
    }

    private fun showDetail() {
        binding.apply {
            args.car?.let {
                vehicleNameText.text = it.name
                vehicleMakeText.text = it.make
                carImage.loadUrl(it.carImageUrl)
                vehicleColorText.text = it.color?.replace("_", " ")
                vehicleFuelLevelText.text = it.fuelLevel?.toString()
                vehicleFuelTypeText.text = it.fuelType
                vehiclePlateText.text = it.licensePlate
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
    }
}
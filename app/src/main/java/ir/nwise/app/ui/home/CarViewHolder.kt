package ir.nwise.app.ui.home

import ir.nwise.app.databinding.ItemCarBinding
import ir.nwise.domain.models.Car
import ir.nwise.app.ui.base.BaseViewHolder
import ir.nwise.app.ui.utils.loadUrl
import java.util.*

class CarViewHolder(
    val binding: ItemCarBinding,
    private val onItemClicked: (Car) -> Unit
) : BaseViewHolder<Car>(binding) {

    override fun bind(model: Car) {
        with(model)
        {
            binding.apply {
                vehicleNameText.text = name
                vehicleMakeText.text = make
                carImage.loadUrl(carImageUrl)
                vehicleColorText.text = color?.replace("_", " ")?.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
                vehicleFuelLevelText.text = fuelLevel?.toString()
                vehicleFuelTypeText.text = fuelType
                vehiclePlateText.text = licensePlate

                root.setOnClickListener { onItemClicked.invoke(model) }
            }
        }
    }
}
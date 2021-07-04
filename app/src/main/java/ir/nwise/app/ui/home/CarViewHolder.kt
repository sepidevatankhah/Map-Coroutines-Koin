package ir.nwise.app.ui.home

import ir.nwise.app.databinding.ItemCarBinding
import ir.nwise.app.domain.models.Car
import ir.nwise.app.ui.base.BaseViewHolder
import ir.nwise.app.ui.utils.loadUrl

class CarViewHolder(
    val binding: ItemCarBinding,
    private val onItemClicked: (Car) -> Unit
) : BaseViewHolder<Car>(binding) {

    override fun bind(model: Car) {
        with(model)
        {
            binding.carModel.text = make
            binding.carImage.loadUrl(carImageUrl)
            binding.root.setOnClickListener { onItemClicked.invoke(model) }
        }
    }
}
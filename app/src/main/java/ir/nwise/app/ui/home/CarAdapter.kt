package ir.nwise.app.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.nwise.app.databinding.ItemCarBinding
import ir.nwise.app.domain.models.Car
import ir.nwise.app.ui.base.BaseViewHolder
import ir.nwise.app.ui.utils.replaceAll

internal class CarAdapter(private val onItemClicked: (Car) -> Unit) :
    RecyclerView.Adapter<BaseViewHolder<out Car>>() {

    private val items = mutableListOf<Car>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<out Car> {
        val inflater = LayoutInflater.from(parent.context)
        return CarViewHolder(
            binding = ItemCarBinding.inflate(inflater, parent, false),
            onItemClicked = onItemClicked
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<out Car>, position: Int) {
        val model = items.getOrNull(position) ?: return
        when (holder) {
            is CarViewHolder -> holder.bind(model)
        }
    }

    override fun getItemCount(): Int = items.size

    fun submitItems(newItems: List<Car>) {
        items.replaceAll(newItems)
    }
}
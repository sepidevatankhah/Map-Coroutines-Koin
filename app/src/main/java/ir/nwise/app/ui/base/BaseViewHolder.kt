package ir.nwise.app.ui.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(model: T)
}
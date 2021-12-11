package com.example.copper15.ui.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.copper15.BR

class DataBindingGenericViewHolder(val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(viewTypeHolder: ViewTypeHolder<*, *>) {
        with(viewTypeHolder) {
            binding.setVariable(BR.item, viewData)
            //callback?.let { binding.setVariable(BR.callback, callback) }
            binding.executePendingBindings()
        }
    }
}
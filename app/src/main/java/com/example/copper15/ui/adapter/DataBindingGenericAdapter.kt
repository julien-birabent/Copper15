package com.example.copper15.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.copper15.ui.LayoutManagerFactory

class DataBindingGenericAdapter : RecyclerView.Adapter<DataBindingGenericViewHolder>() {

    private var items: MutableList<ViewTypeHolder<*, *>> = mutableListOf()

    private fun setItems(listItems: List<ViewTypeHolder<*, *>>) {
        this.items = listItems.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    fun switchToGridLayout(
        recyclerView: RecyclerView,
        orientation: Int = RecyclerView.VERTICAL,
        spanCount: Int = 1,
        gridPattern: ((item: Any?, position: Int, layoutResId: Int) -> Int)? = null
    ) {

        val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return gridPattern?.invoke(
                    items[position].viewData,
                    position,
                    items[position].layoutResId
                ) ?: 1
            }
        }
        recyclerView.layoutManager = LayoutManagerFactory.createGridLayoutManager(
            recyclerView,
            spanCount,
            orientation,
            spanSizeLookup
        )
    }

    fun updateList(newList: List<ViewTypeHolder<*, *>>) {
        val diffUtil = getDiffUtilCallback(this.items, newList)
        if (diffUtil != null) {
            val result = DiffUtil.calculateDiff(diffUtil)
            result.dispatchUpdatesTo(this)
            this.items.clear()
            this.items.addAll(newList)
        } else {
            setItems(newList)
        }
    }

    private fun getDiffUtilCallback(
        oldList: List<ViewTypeHolder<*, *>>,
        newList: List<ViewTypeHolder<*, *>>
    ): BaseDiffCallback<ViewTypeHolder<*, *>>? {
        return BaseDiffCallback(oldList, newList, areItemsTheSame = { oldItem, newItem ->
            oldItem == newItem
        }, areContentTheSame = { oldItem, newItem ->
            oldItem == newItem
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingGenericViewHolder {
        return getViewHolder(parent, viewType)
    }

    private fun getViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataBindingGenericViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return DataBindingGenericViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataBindingGenericViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].layoutResId
    }
}
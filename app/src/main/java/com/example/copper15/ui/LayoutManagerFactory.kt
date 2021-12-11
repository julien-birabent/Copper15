package com.example.copper15.ui

import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.copper15.R

class LayoutManagerFactory {

    companion object {
        fun createLinearLayoutManager(
            recyclerView: RecyclerView,
            orientation: Int
        ): LinearLayoutManager {
            return LinearLayoutManager(recyclerView.context, orientation, false).apply {
                applyDecorations(recyclerView, orientation)
            }
        }

        fun createGridLayoutManager(
            recyclerView: RecyclerView,
            spanCount: Int,
            orientation: Int,
            spanSizeLookup: GridLayoutManager.SpanSizeLookup? = GridLayoutManager.DefaultSpanSizeLookup()
        ): GridLayoutManager {
            return GridLayoutManager(recyclerView.context, spanCount, orientation, false).apply {
                this.spanSizeLookup = spanSizeLookup
                applyDecorations(recyclerView, orientation)
            }
        }

        private fun applyDecorations(recyclerView: RecyclerView, orientation: Int) {
            val dividerDrawable = AppCompatResources.getDrawable(recyclerView.context,R.drawable.item_decoration_divider_transparent)
            val dividerItemDecoration = DividerItemDecoration(recyclerView.context, orientation).apply { dividerDrawable?.let { setDrawable(it) } }
            recyclerView.addItemDecoration(dividerItemDecoration)
        }
    }
}
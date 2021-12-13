package com.example.copper15.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.copper15.ui.LayoutManagerFactory

@BindingAdapter(
    value = ["app:setAdapter", "app:setOrientation", "app:disableAnimation"],
    requireAll = false
)
fun bindRecyclerViewAdapter(
    recyclerView: RecyclerView,
    adapter: RecyclerView.Adapter<*>,
    orientation: Int = RecyclerView.HORIZONTAL,
    disableAnimation: Boolean = false
) {
    recyclerView.apply {
        setHasFixedSize(false)
        layoutManager = LayoutManagerFactory.createLinearLayoutManager(this, orientation)
        if (disableAnimation) itemAnimator = null else DefaultItemAnimator()
        this.adapter = adapter
    }
}

@BindingAdapter("android:src")
fun setImageUri(view: ImageView, imageUri: String) {
    Glide.with(view).load(imageUri).into(view)
}

@BindingAdapter("app:isRefreshing")
fun setSwipeLayoutRefreshingState(view: SwipeRefreshLayout, isRefreshing: Boolean) {
    view.isRefreshing = isRefreshing
}

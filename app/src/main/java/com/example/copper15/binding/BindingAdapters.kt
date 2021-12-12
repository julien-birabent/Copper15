package com.example.copper15.binding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.copper15.ui.LayoutManagerFactory

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("android:visibility")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }
}

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

@BindingAdapter("android:visibility")
fun setVisibility(view: View, value: Boolean) {
    view.visibility = if (value) View.VISIBLE else View.GONE
}


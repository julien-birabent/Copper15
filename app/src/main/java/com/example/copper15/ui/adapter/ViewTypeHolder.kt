package com.example.copper15.ui.adapter

data class ViewTypeHolder<T, Callback>(
    val viewData: T,
    val layoutResId: Int,
    val callback: Callback? = null
) {
    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        with(other as ViewTypeHolder<*, *>) {
            return this@ViewTypeHolder.viewData == this.viewData
        }
    }

    override fun hashCode(): Int {
        return viewData.hashCode()
    }
}
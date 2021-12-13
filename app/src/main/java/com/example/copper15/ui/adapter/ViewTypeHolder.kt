package com.example.copper15.ui.adapter

data class ViewTypeHolder<T>(
    val viewData: T,
    val layoutResId: Int
) {
    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        with(other as ViewTypeHolder<*>) {
            return this@ViewTypeHolder.viewData == this.viewData
        }
    }

    override fun hashCode(): Int {
        return viewData.hashCode()
    }
}
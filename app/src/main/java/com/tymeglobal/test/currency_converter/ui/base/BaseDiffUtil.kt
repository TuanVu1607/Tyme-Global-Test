package com.tymeglobal.test.currency_converter.ui.base

import androidx.recyclerview.widget.DiffUtil

class BaseDiffUtil<T>(
    private val areItemsTheSameCustom: (oldItem: T, newItem: T) -> Boolean,
    private val areContentsTheSameCustom: (oldItem: T, newItem: T) -> Boolean
) : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T & Any, newItem: T & Any): Boolean =
        areItemsTheSameCustom(oldItem, newItem)

    override fun areContentsTheSame(oldItem: T & Any, newItem: T & Any): Boolean =
        areContentsTheSameCustom(oldItem, newItem)
}
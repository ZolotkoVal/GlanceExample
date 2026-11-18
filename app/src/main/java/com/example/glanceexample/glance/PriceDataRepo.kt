package com.example.glanceexample.glance

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random

data class StockPrice(val price: Float = 25f, val previous: Float = 0f, val revision: Long = 0) {
 val change: Int get() = percentageChange(previous, price)
 val rising: Boolean get() = previous > 0f && price > previous
}

object PriceDataRepo {
 const val ticker = "GOOGL"
 private val mutablePrice = MutableStateFlow(StockPrice())
 val quote = mutablePrice.asStateFlow()
 private val mutableCurrentPrice = MutableStateFlow(mutablePrice.value.price)
 val currentPrice: StateFlow<Float> = mutableCurrentPrice.asStateFlow()
 @Synchronized fun update() {
  val before = mutablePrice.value
  mutablePrice.value = StockPrice(Random.nextInt(20, 35) + Random.nextFloat(), if (before.revision == 0L) 0f else before.price, before.revision + 1)
  mutableCurrentPrice.value = mutablePrice.value.price
 }
}

internal fun percentageChange(previous: Float, current: Float): Int =
 if (previous <= 0f) 0 else ((current - previous) / previous * 100).toInt()

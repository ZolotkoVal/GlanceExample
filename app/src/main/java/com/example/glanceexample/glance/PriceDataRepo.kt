package com.example.glanceexample.glance

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random

data class StockPrice(val price: Float = 25f, val previous: Float = 0f, val revision: Long = 0) {
 val change: Int get() = percentageChange(previous, price)
 val rising: Boolean get() = price >= previous
}

object PriceDataRepo {
 const val ticker = "GOOGL"
 private val mutablePrice = MutableStateFlow(StockPrice())
 val currentPrice = mutablePrice.asStateFlow()
 @Synchronized fun update() {
  val before = mutablePrice.value
  mutablePrice.value = StockPrice(Random.nextInt(20, 35) + Random.nextFloat(), before.price, before.revision + 1)
 }
}

internal fun percentageChange(previous: Float, current: Float): Int =
 if (previous <= 0f) 0 else ((current - previous) / previous * 100).toInt()

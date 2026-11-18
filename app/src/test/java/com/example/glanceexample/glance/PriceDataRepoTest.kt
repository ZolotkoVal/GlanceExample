package com.example.glanceexample.glance

import org.junit.Assert.*
import org.junit.Test

class PriceDataRepoTest {
 @Test fun exposesFloatPriceFlow() {
  PriceDataRepo.update()
  val price: Any = PriceDataRepo.currentPrice.value
  assertTrue("PDF requires StateFlow<Float>", price is Float)
 }
 @Test fun updatesPublishConsistentSnapshots() {
  repeat(100) {
   val before = PriceDataRepo.quote.value
   PriceDataRepo.update()
   val after = PriceDataRepo.quote.value
   assertEquals(before.revision + 1, after.revision)
   val previous = if (before.revision == 0L) 0f else before.price
   assertEquals(previous, after.previous)
   assertTrue(after.price >= 20f && after.price < 35f)
   assertEquals(percentageChange(previous, after.price), after.change)
   assertEquals(previous > 0f && after.price > previous, after.rising)
   assertEquals(after.price, PriceDataRepo.currentPrice.value)
  }
 }
 @Test fun percentageHandlesFirstPriceAndDirections() {
  assertEquals(0, percentageChange(0f, 25f))
  assertEquals(20, percentageChange(25f, 30f))
  assertEquals(-20, percentageChange(25f, 20f))
  assertEquals(0, percentageChange(25f, 25f))
 }
}

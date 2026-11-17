package com.example.glanceexample.glance

import org.junit.Assert.*
import org.junit.Test

class PriceDataRepoTest {
 @Test fun updatesPublishConsistentSnapshots() {
  repeat(100) {
   val before = PriceDataRepo.currentPrice.value
   PriceDataRepo.update()
   val after = PriceDataRepo.currentPrice.value
   assertEquals(before.revision + 1, after.revision)
   assertEquals(before.price, after.previous)
   assertTrue(after.price >= 20f && after.price < 35f)
   assertEquals(percentageChange(before.price, after.price), after.change)
   assertEquals(after.price >= before.price, after.rising)
  }
 }
 @Test fun percentageHandlesFirstPriceAndDirections() {
  assertEquals(0, percentageChange(0f, 25f))
  assertEquals(20, percentageChange(25f, 30f))
  assertEquals(-20, percentageChange(25f, 20f))
  assertEquals(0, percentageChange(25f, 25f))
 }
}

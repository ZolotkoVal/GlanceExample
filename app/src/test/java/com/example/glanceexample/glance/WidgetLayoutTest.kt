package com.example.glanceexample.glance

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.appwidget.SizeMode
import org.junit.Assert.assertEquals
import org.junit.Test

class WidgetLayoutTest {
 @Test fun supportsBothLectureSizes() {
  assertEquals(SizeMode.Responsive(setOf(DpSize(100.dp, 80.dp), DpSize(120.dp, 120.dp))), StockAppWidget().sizeMode)
 }
}

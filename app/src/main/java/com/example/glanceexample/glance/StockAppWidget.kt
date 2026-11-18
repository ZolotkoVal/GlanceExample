package com.example.glanceexample.glance

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.*
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.*
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.text.FontWeight
import java.util.Locale

class StockAppWidget : GlanceAppWidget() {
 override suspend fun provideGlance(context: Context, id: GlanceId) {
  PriceUpdater.start(context)
  provideContent { GlanceTheme { GlanceContent() } }
 }
 @Composable private fun GlanceContent() {
  val quote by PriceDataRepo.quote.collectAsState()
  Column(GlanceModifier.fillMaxSize().background(GlanceTheme.colors.background).padding(8.dp)) {
   StockDisplay(quote)
  }
 }
 @Composable private fun StockDisplay(quote: StockPrice) {
  val color = if (quote.rising) GlanceTheme.colors.primary else GlanceTheme.colors.error
  val style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = color)
  Text(PriceDataRepo.ticker, style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold))
  Text(String.format(Locale.getDefault(), "%.2f", quote.price), style = style)
  Text("${quote.change} %", style = style)
 }
}

package com.example.glanceexample.glance

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.sp
import androidx.glance.*
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.action.clickable
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.*
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.text.FontWeight
import java.util.Locale
import com.example.glanceexample.R

class StockAppWidget : GlanceAppWidget() {
 companion object {
  val smallMode = DpSize(100.dp, 80.dp)
  val mediumMode = DpSize(120.dp, 120.dp)
 }
 override val sizeMode = SizeMode.Responsive(setOf(smallMode, mediumMode))
 override suspend fun provideGlance(context: Context, id: GlanceId) {
  PriceUpdater.start(context)
  provideContent { GlanceTheme { GlanceContent() } }
 }
 @Composable private fun GlanceContent() {
  val quote by PriceDataRepo.quote.collectAsState()
  val size = LocalSize.current
  if (size.width >= mediumMode.width && size.height >= mediumMode.height) Medium(quote) else Small(quote)
 }
 @Composable private fun Small(quote: StockPrice) {
  Column(GlanceModifier.clickable(actionRunCallback<RefreshAction>()).fillMaxSize().background(GlanceTheme.colors.background).padding(8.dp)) {
   StockDisplay(quote)
  }
 }
 @Composable private fun Medium(quote: StockPrice) {
  Column(GlanceModifier.clickable(actionRunCallback<RefreshAction>()).fillMaxSize().cornerRadius(15.dp).background(GlanceTheme.colors.background).padding(8.dp),
   horizontalAlignment = Alignment.Horizontal.CenterHorizontally) {
   StockDisplay(quote)
   if (quote.previous > 0f && quote.price != quote.previous) {
    Image(ImageProvider(if (quote.rising) R.drawable.up_arrow else R.drawable.down_arrow),
     contentDescription = if (quote.rising) "Price up" else "Price down",
     modifier = GlanceModifier.fillMaxSize().padding(20.dp))
   }
  }
 }
 @Composable private fun StockDisplay(quote: StockPrice) {
  val color = when {
   quote.previous <= 0f || quote.price == quote.previous -> GlanceTheme.colors.onSurface
   quote.rising -> GlanceTheme.colors.primary
   else -> GlanceTheme.colors.error
  }
  val style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold, color = color)
  Text(PriceDataRepo.ticker, style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold))
  Text(String.format(Locale.getDefault(), "%.2f", quote.price), style = style)
  Text("${quote.change} %", style = style)
 }
}

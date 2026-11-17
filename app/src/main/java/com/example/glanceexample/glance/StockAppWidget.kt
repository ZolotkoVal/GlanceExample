package com.example.glanceexample.glance

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.*
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.*
import androidx.glance.text.Text

class StockAppWidget : GlanceAppWidget() {
 override suspend fun provideGlance(context: Context, id: GlanceId) {
  provideContent { GlanceTheme { GlanceContent() } }
 }
 @Composable private fun GlanceContent() {
  Column(GlanceModifier.fillMaxSize().background(GlanceTheme.colors.background).padding(8.dp)) {
   Text("Demo")
  }
 }
}

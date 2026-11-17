package com.example.glanceexample.glance

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetReceiver

class StockAppWidgetReceiver : GlanceAppWidgetReceiver() {
 override val glanceAppWidget = StockAppWidget()
 override fun onEnabled(context: Context) {
  super.onEnabled(context)
  PriceDataRepo.update()
 }
}

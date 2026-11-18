package com.example.glanceexample.glance

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback

class RefreshAction : ActionCallback {
 override suspend fun onAction(context: Context, glanceId: GlanceId, parameters: ActionParameters) {
  PriceUpdater.refresh(context.applicationContext, "tap")
  PriceUpdater.start(context.applicationContext)
 }
}

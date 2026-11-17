package com.example.glanceexample.glance

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.util.Log
import androidx.glance.appwidget.updateAll
import kotlinx.coroutines.*

/** One process-local demo timer for all instances; no job per layout or updateAll call. */
object PriceUpdater {
 const val INTERVAL_MS = 20_000L
 private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
 private var job: Job? = null
 @Synchronized fun start(context: Context) {
  if (job?.isActive == true) return
  val app = context.applicationContext
  job = scope.launch {
   Log.d("GlancePrices", "timer-start")
   while (isActive) {
    delay(INTERVAL_MS)
    val ids = AppWidgetManager.getInstance(app).getAppWidgetIds(ComponentName(app, StockAppWidgetReceiver::class.java))
    if (ids.isEmpty()) break
    try { refresh(app, "automatic") }
    catch (cancelled: CancellationException) { throw cancelled }
    catch (error: Exception) { Log.e("GlancePrices", "Widget update failed", error) }
   }
   Log.d("GlancePrices", "timer-finished")
  }
 }
 suspend fun refresh(context: Context, reason: String) {
  PriceDataRepo.update()
  val quote = PriceDataRepo.currentPrice.value
  Log.d("GlancePrices", "$reason revision=${quote.revision} price=${quote.price} change=${quote.change}")
  StockAppWidget().updateAll(context)
 }
 @Synchronized fun stop() {
  job?.cancel()
  job = null
  Log.d("GlancePrices", "timer-stop")
 }
}

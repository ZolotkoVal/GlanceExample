package com.example.glanceexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
 override fun onCreate(savedInstanceState: Bundle?) {
  super.onCreate(savedInstanceState)
  setContent {
   MaterialTheme {
    Scaffold { padding ->
     Column(Modifier.padding(padding).padding(24.dp)) {
      Text("GlanceExample", style = MaterialTheme.typography.headlineMedium)
      Text("Добавьте виджет на главный экран: удерживайте свободное место → Widgets → GlanceExample. Касание виджета обновляет цену; увеличение размера показывает стрелку.")
      Text("GOOGL: учебные случайные данные, не биржевые котировки.")
     }
    }
   }
  }
 }
}

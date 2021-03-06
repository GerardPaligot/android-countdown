/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.androiddevchallenge.components.Chrono
import com.example.androiddevchallenge.components.Controllers
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.SystemUiController
import com.example.androiddevchallenge.ui.theme.grey

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = remember { SystemUiController(window) }
            MyTheme {
                systemUiController.setSystemBarsColor(color = Color.White)
                val viewModel by viewModels<TimerViewModel>()
                val times by viewModel.times.collectAsState()
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Brush.radialGradient(listOf(grey, Color.White))),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        Chrono(times)
                        Controllers(
                            onPause = { viewModel.pause() },
                            onStart = { viewModel.start() },
                            onStop = { viewModel.stop() }
                        )
                    }
                }
            }
        }
    }
}

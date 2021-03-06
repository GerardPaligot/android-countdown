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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class TimerViewModel : ViewModel() {
    private var job: Job? = null
    private val _times = MutableStateFlow(0)
    val times = _times.asStateFlow()

    fun start(times: Int = 30) {
        if (_times.value == 0) _times.value = times
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                if (_times.value <= 0) {
                    job?.cancel()
                    return@launch
                }
                delay(timeMillis = 1000)
                _times.value -= 1
            }
        }
    }

    fun pause() {
        job?.cancel()
    }

    fun stop() {
        job?.cancel()
        _times.value = 0
    }
}

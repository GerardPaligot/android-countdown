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
package com.example.androiddevchallenge.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.purple
import com.example.androiddevchallenge.ui.theme.purpleDark
import com.example.androiddevchallenge.ui.theme.purpleLight
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Chrono(
    seconds: Int,
    modifier: Modifier = Modifier
) {
    val nbMarker = 60
    val progressAngle by animateFloatAsState(
        targetValue = 360f / 30f * seconds,
        animationSpec = tween(500)
    )
    val markerActives by animateFloatAsState(
        targetValue = nbMarker / 30f * seconds,
        animationSpec = tween(500)
    )
    Box(
        modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        for (i in 0 until nbMarker) {
            Marker(
                angle = i * (360 / nbMarker),
                active = i < markerActives
            )
        }
        ChronoProgress {
            Text(
                text = "${seconds}s",
                style = MaterialTheme.typography.h4,
                color = purpleDark,
                modifier = Modifier.align(alignment = Alignment.Center)
            )
        }
        CircleProgress(angle = progressAngle)
    }
}

@Composable
internal fun ChronoProgress(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(95.dp)
            .shadow(elevation = 1.dp, shape = CircleShape)
            .background(color = Color.White, shape = CircleShape)
            .clip(CircleShape),
        content = content
    )
}

@Composable
internal fun CircleProgress(
    angle: Float,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .fillMaxSize()
            .padding(80.dp)
            .drawBehind {
                drawArc(
                    color = Color.White,
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = Stroke(width = 30f)
                )
                drawArc(
                    brush = Brush.verticalGradient(listOf(purpleDark, purple, purpleLight)),
                    startAngle = -90f,
                    sweepAngle = angle,
                    useCenter = false,
                    style = Stroke(width = 30f, cap = StrokeCap.Round)
                )
            }
    )
}

@Composable
internal fun Marker(
    angle: Int,
    active: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .fillMaxSize()
            .drawBehind {
                val theta = (angle - 90) * PI.toFloat() / 180f
                val startRadius = size.width / 2 * .72f
                val endRadius = size.width / 2 * .8f
                val startPos = Offset(cos(theta) * startRadius, sin(theta) * startRadius)
                val endPos = Offset(cos(theta) * endRadius, sin(theta) * endRadius)
                drawLine(
                    color = if (active) purpleLight else purple.copy(alpha = .1f),
                    start = center + startPos,
                    end = center + endPos,
                    strokeWidth = 8f,
                    cap = StrokeCap.Round
                )
            }
    )
}

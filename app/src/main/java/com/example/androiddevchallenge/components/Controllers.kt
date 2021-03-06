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

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.Stop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.greenDark
import com.example.androiddevchallenge.ui.theme.purpleDark
import com.example.androiddevchallenge.ui.theme.purpleLightest

@Composable
fun Controllers(
    modifier: Modifier = Modifier,
    onPause: () -> Unit,
    onStart: () -> Unit,
    onStop: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Pause(onPause = onPause)
        Start(onStart = onStart)
        Stop(onStop = onStop)
    }
}

@Composable
internal fun Stop(onStop: () -> Unit) {
    Button(
        color = purpleLightest,
        onClick = onStop
    ) {
        Icon(
            imageVector = Icons.Rounded.Stop,
            contentDescription = "Stop",
            tint = purpleDark,
            modifier = Modifier
                .size(45.dp)
                .align(alignment = Alignment.Center)
        )
    }
}

@Composable
internal fun Start(onStart: () -> Unit) {
    Button(
        color = com.example.androiddevchallenge.ui.theme.green,
        onClick = onStart
    ) {
        Text(
            text = "START",
            style = MaterialTheme.typography.button,
            color = greenDark,
            modifier = Modifier.align(alignment = Alignment.Center)
        )
    }
}

@Composable
internal fun Pause(onPause: () -> Unit) {
    Button(
        color = purpleLightest,
        onClick = onPause
    ) {
        Icon(
            imageVector = Icons.Rounded.Pause,
            contentDescription = "Pause",
            tint = purpleDark,
            modifier = Modifier
                .size(35.dp)
                .align(alignment = Alignment.Center)
        )
    }
}

@Composable
internal fun Button(
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val interaction = remember { MutableInteractionSource() }
    val isPressed by interaction.collectIsPressedAsState()
    val colorAnimated by animateColorAsState(
        targetValue = if (isPressed) color else Color.White,
        animationSpec = tween(400)
    )
    val elevation by animateDpAsState(
        targetValue = if (isPressed) 3.dp else 8.dp,
        animationSpec = tween(400)
    )
    val brush = Brush.verticalGradient(listOf(color, colorAnimated))
    Box(
        modifier
            .shadow(elevation = elevation, shape = CircleShape)
            .border(width = 5.dp, color = Color.White, shape = CircleShape)
            .background(brush = brush, shape = CircleShape)
            .clip(CircleShape)
            .width(100.dp)
            .aspectRatio(1f)
            .clickable(
                interactionSource = interaction,
                indication = null,
                onClick = onClick
            ),
        content = content
    )
}

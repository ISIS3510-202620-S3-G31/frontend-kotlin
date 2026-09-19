package com.isis3510.ark.ui.screens.custombreathing.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.isis3510.ark.R
import kotlin.math.cos
import kotlin.math.sin

private const val START_ANGLE = -90f

// In the mockup the sparkles are drawn for a ring at 40%, so they are rotated from there.
private const val SPARKLES_PROGRESS = 0.4f

// Circular timer: a dim track, an arc that goes from teal to orange, a soft glow
// and some sparkles at the head of the arc. The content goes in the middle.
@Composable
fun BreathingRing(
    progress: Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val trackColor = MaterialTheme.colorScheme.inverseOnSurface.copy(alpha = 0.14f)
    val startColor = MaterialTheme.colorScheme.secondary
    val endColor = MaterialTheme.colorScheme.primary
    val sweep = 360f * progress.coerceIn(0f, 1f)

    Box(modifier = modifier.size(180.dp), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val stroke = 12.dp.toPx()
            val radius = (size.minDimension - stroke) / 2f
            val angle = Math.toRadians((START_ANGLE + sweep).toDouble())
            val head = Offset(
                x = center.x + radius * cos(angle).toFloat(),
                y = center.y + radius * sin(angle).toFloat(),
            )

            // Glow behind the head of the arc
            val glowRadius = 25.dp.toPx()
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(endColor.copy(alpha = 0.55f), Color.Transparent),
                    center = head,
                    radius = glowRadius,
                ),
                radius = glowRadius,
                center = head,
            )

            drawCircle(color = trackColor, radius = radius, style = Stroke(width = stroke))

            // The gradient goes from the top of the ring to the head of the arc.
            // After half a turn the head goes up again, so the bottom of the ring is used.
            val gradientEnd = if (sweep < 180f) head.y else center.y + radius
            drawArc(
                brush = Brush.verticalGradient(
                    colors = listOf(startColor, endColor),
                    startY = center.y - radius,
                    endY = gradientEnd.coerceAtLeast(center.y - radius + 1f),
                ),
                startAngle = START_ANGLE,
                sweepAngle = sweep,
                useCenter = false,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = stroke, cap = StrokeCap.Round),
            )
        }
        Image(
            painter = painterResource(R.drawable.breathing_ring_sparkles),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .rotate(360f * (progress - SPARKLES_PROGRESS)),
        )
        content()
    }
}

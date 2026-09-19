package com.isis3510.ark.ui.screens.custombreathing.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.isis3510.ark.R
import com.isis3510.ark.ui.components.ArkCircleButton

@Composable
fun StepperRow(
    label: String,
    seconds: Int,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.shapes.medium)
            .padding(start = 16.dp, end = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = label, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
        StepButton(
            icon = R.drawable.ic_minus,
            contentDescription = stringResource(R.string.breathing_decrease, label),
            onClick = onDecrease,
        )
        Text(
            text = stringResource(R.string.breathing_seconds, seconds),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(52.dp),
        )
        StepButton(
            icon = R.drawable.ic_plus,
            contentDescription = stringResource(R.string.breathing_increase, label),
            onClick = onIncrease,
        )
    }
}

// 40dp circle in a 48dp box
@Composable
private fun StepButton(icon: Int, contentDescription: String, onClick: () -> Unit) {
    Box(modifier = Modifier.size(48.dp), contentAlignment = Alignment.Center) {
        ArkCircleButton(
            icon = icon,
            contentDescription = contentDescription,
            onClick = onClick,
            size = 40.dp,
            iconSize = 18.dp,
        )
    }
}

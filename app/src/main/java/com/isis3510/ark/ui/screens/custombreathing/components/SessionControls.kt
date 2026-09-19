package com.isis3510.ark.ui.screens.custombreathing.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.isis3510.ark.R
import com.isis3510.ark.ui.components.ArkCircleButton

// Bottom row that all the tools share: small teal button, big orange button with
// a dark ring, small teal button.
@Composable
fun SessionControls(
    onReset: () -> Unit,
    onPause: () -> Unit,
    onFinish: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ArkCircleButton(
            icon = R.drawable.ic_restart,
            contentDescription = stringResource(R.string.breathing_reset),
            onClick = onReset,
        )
        PauseButton(onClick = onPause)
        ArkCircleButton(
            icon = R.drawable.ic_check,
            contentDescription = stringResource(R.string.breathing_finish),
            onClick = onFinish,
        )
    }
}

@Composable
private fun PauseButton(onClick: () -> Unit) {
    val description = stringResource(R.string.breathing_pause)
    Box(
        modifier = Modifier
            .size(84.dp)
            .clip(CircleShape)
            .border(3.dp, MaterialTheme.colorScheme.onBackground, CircleShape)
            .clickable(role = Role.Button, onClickLabel = description, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .size(66.dp)
                .background(MaterialTheme.colorScheme.primary, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(R.drawable.ic_pause),
                contentDescription = description,
                modifier = Modifier.size(28.dp),
            )
        }
    }
}

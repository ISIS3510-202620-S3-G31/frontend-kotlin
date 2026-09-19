package com.isis3510.ark.ui.screens.toolhub.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.isis3510.ark.R
import com.isis3510.ark.ui.theme.ArkOnDarkMuted

// First and most visible card of the hub: a shortcut to the random tool picker.
// Bloom uses the Curious pose, the one MS6 (section 5.5) assigns to "Leave it to chance".
@Composable
fun ChanceCard(onSurpriseMe: () -> Unit, modifier: Modifier = Modifier) {
    val shape = MaterialTheme.shapes.extraLarge
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 16.dp,
                shape = shape,
                ambientColor = MaterialTheme.colorScheme.primary,
                spotColor = MaterialTheme.colorScheme.primary,
            )
            .clip(shape)
            .background(MaterialTheme.colorScheme.inverseSurface),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = stringResource(R.string.chance_card_title),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.inverseOnSurface,
            )
            Text(
                text = stringResource(R.string.chance_card_body),
                style = MaterialTheme.typography.bodyMedium,
                color = ArkOnDarkMuted,
                modifier = Modifier.width(236.dp),
            )
            Spacer(Modifier.height(8.dp))
            SurpriseMeButton(onClick = onSurpriseMe)
        }

        // Decoration, anchored to the right so it also works on wider screens.
        // Bloom goes a bit outside the card on purpose and the card clips it.
        Image(
            painter = painterResource(R.drawable.question_sparkle),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = (-56).dp, y = 4.dp)
                .size(width = 60.dp, height = 56.dp),
        )
        Image(
            painter = painterResource(R.drawable.bloom_curious),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 24.dp, y = 47.dp)
                .size(width = 118.dp, height = 129.dp)
                .rotate(-8f),
        )
    }
}

@Composable
private fun SurpriseMeButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .height(48.dp)
            .clip(CircleShape)
            .clickable(role = Role.Button, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier
                .height(44.dp)
                .background(MaterialTheme.colorScheme.primary, CircleShape)
                .padding(start = 16.dp, end = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(R.drawable.ic_random),
                contentDescription = null,
                modifier = Modifier.size(18.dp),
            )
            Text(
                text = stringResource(R.string.chance_card_button),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}

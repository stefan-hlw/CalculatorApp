package com.pepa.calculatorapp.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun CalculatorButton(
    modifier: Modifier = Modifier,
    action: CalculatorUiAction,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(
                when (action.highlightLevel) {
                    HighlightLevel.Neutral -> MaterialTheme.colorScheme.surfaceVariant
                    HighlightLevel.SemiHighlighted -> MaterialTheme.colorScheme.inverseSurface
                    HighlightLevel.Highlighted -> MaterialTheme.colorScheme.tertiary
                    HighlightLevel.StronglyHighlighted -> MaterialTheme.colorScheme.primary
                }
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if(action.text != null) {
            Text(
                text = action.text,
                textAlign = TextAlign.Center,
                fontSize = 36.sp,
                color = when(action.highlightLevel) {
                    is HighlightLevel.Neutral -> MaterialTheme.colorScheme.onSurfaceVariant
                    is HighlightLevel.SemiHighlighted -> MaterialTheme.colorScheme.inverseOnSurface
                    is HighlightLevel.Highlighted -> MaterialTheme.colorScheme.onTertiary
                    is HighlightLevel.StronglyHighlighted -> MaterialTheme.colorScheme.onPrimary
                }
            )
        } else {
            action.content()
        }
    }
}
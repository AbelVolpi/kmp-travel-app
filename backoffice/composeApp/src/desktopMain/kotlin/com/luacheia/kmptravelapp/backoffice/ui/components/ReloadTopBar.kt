package com.luacheia.kmptravelapp.backoffice.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cached
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.luacheia.kmptravelapp.backoffice.ui.theme.backgroundColor

@Composable
fun ReloadTopBar(
    onClick: ()-> Unit,
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        FilledTonalIconButton(
            onClick = { onClick.invoke() },
            modifier = Modifier
                .padding(end = 16.dp, top = 24.dp)
                .size(20.dp)
                .clip(shape = CircleShape)
                .background(backgroundColor)
                .align(Alignment.CenterEnd)
        ) {
            Icon(
                imageVector = Icons.Default.Cached,
                contentDescription = "",
            )
        }
    }
}
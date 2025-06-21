package com.luacheia.kmptravelapp.backoffice.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.luacheia.kmptravelapp.backoffice.ui.sections.categoriesplaces.home.AddButton
import com.luacheia.kmptravelapp.backoffice.ui.sections.categoriesplaces.home.Title

@Composable
fun Topic(
    text: String,
    onAddClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier.wrapContentHeight().padding(bottom = 25.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Title(text = text)
        Spacer(modifier = Modifier.width(30.dp))
        if (onAddClick != null) {
            AddButton(onClick = onAddClick)
        }
    }
}

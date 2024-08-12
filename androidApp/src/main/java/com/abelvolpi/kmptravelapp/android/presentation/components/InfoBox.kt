package com.abelvolpi.kmptravelapp.android.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.abelvolpi.kmptravelapp.android.presentation.theme.secondaryColor

sealed class IconSource {
    data class Remote(val url: String) : IconSource()
    data class Local(val resId: Int) : IconSource()
}

@Composable
fun InfoBox(
    title: String,
    iconSource: IconSource,
    onInfoBoxClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp, start = 30.dp, end = 30.dp)
            .clip(shape = RoundedCornerShape(15.dp))
            .background(secondaryColor)
            .clickable { onInfoBoxClicked.invoke() }
            .padding(20.dp)
    ) {
        when (iconSource) {
            is IconSource.Remote -> {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(iconSource.url)
                        .decoderFactory(SvgDecoder.Factory())
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.CenterVertically)
                )
            }

            is IconSource.Local -> {
                Icon(
                    painter = painterResource(id = iconSource.resId),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.CenterVertically)
                )
            }
        }
        Text(
            text = title,
            fontSize = 18.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 15.dp)
        )
    }
}
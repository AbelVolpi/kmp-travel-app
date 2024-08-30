package com.abelvolpi.kmptravelapp.android.presentation.ui.chalet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.abelvolpi.kmptravelapp.android.R
import com.abelvolpi.kmptravelapp.android.presentation.components.IconSource
import com.abelvolpi.kmptravelapp.android.presentation.components.InfoBox
import com.abelvolpi.kmptravelapp.android.presentation.components.LoadingIndicator
import com.abelvolpi.kmptravelapp.data.model.Guidance
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChaletScreen(
    chaletViewModel: ChaletViewModel = koinViewModel(),
    onWhatsAppInfoClicked: () -> Unit,
    onAccommodationsClicked: () -> Unit,
    onGuidanceClicked: (String) -> Unit
) {
    val chaletUIData = chaletViewModel.chaletModel.collectAsStateWithLifecycle().value

    if (chaletUIData != null) {
        ChaletUI(
            chaletUIData,
            onWhatsAppInfoClicked,
            onAccommodationsClicked,
            onGuidanceClicked
        )
    } else {
        LoadingIndicator()
    }
}

@Composable
fun ChaletUI(
    chaletUIData: ChaletModel,
    onWhatsAppInfoClicked: () -> Unit,
    onAccommodationsClicked: () -> Unit,
    onGuidanceClicked: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        ChaletTitle()
        WhatsAppInfoBox(
            onWhatsAppInfoClicked = onWhatsAppInfoClicked
        )
        AccommodationsComponent(
            onAccommodationsClicked = onAccommodationsClicked
        )
        GuidancesOptionsList(
            guidancesList = chaletUIData.guidances,
            onGuidanceClicked = onGuidanceClicked
        )
    }
}

@Composable
fun ChaletTitle() {
    Text(
        text = "Chalés Lua Cheia",
        color = Color.White,
        modifier = Modifier
            .padding(top = 45.dp, bottom = 15.dp, start = 30.dp, end = 30.dp),
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun WhatsAppInfoBox(
    onWhatsAppInfoClicked: () -> Unit
) {
    InfoBox(
        title = "Entre em contato conosco através do WhatsApp",
        iconSource = IconSource.Local(R.drawable.whatsapp_icon),
        onInfoBoxClicked = onWhatsAppInfoClicked
    )
}

@Composable
fun GuidancesOptionsList(
    guidancesList: List<Guidance>,
    onGuidanceClicked: (String) -> Unit
) {
    Column {
        guidancesList.forEach { guidance ->
            GuidanceComponent(
                guidance.id,
                guidance.title,
                guidance.subtitle,
                guidance.iconUrl,
                onGuidanceClicked
            )
        }
    }
}

@Composable
fun GuidanceComponent(
    id: String,
    title: String,
    subtitle: String,
    iconUrl: String,
    onGuidanceClicked: (String) -> Unit
) {
    ChaletAbstractComponent(
        id = id,
        title = title,
        subtitle = subtitle,
        iconSource = IconSource.Remote(iconUrl),
        onGuidanceClicked = ChaletOptionAction.Guidance(onGuidanceClicked)
    )
}

@Composable
fun AccommodationsComponent(
    onAccommodationsClicked: () -> Unit
) {
    ChaletAbstractComponent(
        id = "",
        title = "Acomodações",
        subtitle = "Veja outras acomodações dos Chalés Lua Cheia",
        iconSource = IconSource.Local(R.drawable.chalet_2_icon),
        onGuidanceClicked = ChaletOptionAction.Accommodations(onAccommodationsClicked)
    )
}

@Composable
private fun ChaletAbstractComponent(
    id: String,
    title: String,
    subtitle: String,
    iconSource: IconSource,
    onGuidanceClicked: ChaletOptionAction
) {
    Divider(
        color = Color.White,
        thickness = 1.dp,
        modifier = Modifier
            .alpha(0.5f)
            .padding(horizontal = 30.dp)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                when (onGuidanceClicked) {
                    is ChaletOptionAction.Accommodations -> {
                        onGuidanceClicked.action.invoke()
                    }

                    is ChaletOptionAction.Guidance -> {
                        onGuidanceClicked.action.invoke(id)
                    }
                }
            }
            .padding(vertical = 20.dp, horizontal = 30.dp)

    ) {
        when (iconSource) {
            is IconSource.Local -> {
                Icon(
                    painter = painterResource(id = iconSource.resId),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(34.dp)
                        .align(Alignment.CenterVertically)
                )
            }

            is IconSource.Remote -> {
                AsyncImage(
                    model = ImageRequest
                        .Builder(LocalContext.current)
                        .data(iconSource.url)
                        .decoderFactory(SvgDecoder.Factory())
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(34.dp)
                        .align(Alignment.CenterVertically)
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
                .weight(1f)
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Start)
            )
            Text(
                text = subtitle,
                maxLines = 2,
                fontSize = 14.sp,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Start)
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.arrow_icon),
            contentDescription = "WhatsApp",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
    }
}

sealed class ChaletOptionAction {
    data class Accommodations(val action: () -> Unit) : ChaletOptionAction()
    data class Guidance(val action: (String) -> Unit) : ChaletOptionAction()
}

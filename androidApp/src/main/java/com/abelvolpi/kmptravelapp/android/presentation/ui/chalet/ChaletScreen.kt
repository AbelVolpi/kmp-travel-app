package com.abelvolpi.kmptravelapp.android.presentation.ui.chalet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abelvolpi.kmptravelapp.android.R
import com.abelvolpi.kmptravelapp.android.presentation.theme.secondaryColor

@Preview
@Composable
fun ChaletScreenPreview() {
    ChaletScreen()
}

@Composable
fun ChaletScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        ChaletTitle()
        WhatsAppBox()
        ChaletOptionsList()
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

//TODO (move to single component with accommodation screen)
@Composable
fun WhatsAppBox() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp, start = 30.dp, end = 30.dp)
            .clip(shape = RoundedCornerShape(15.dp))
            .background(secondaryColor)
            .clickable {  }
            .padding(20.dp)


    ) {
        Icon(
            painter = painterResource(id = R.drawable.whatsapp_icon),
            contentDescription = "WhatsApp",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
        Text(
            text = "Entre em contato conosco através do WhatsApp",
            fontSize = 18.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 10.dp)
        )
    }
}

@Composable
fun ChaletOptionsList() {
    val chaletOptions = listOf(
        ChaletOption(
            R.drawable.chalet_2_icon,
            "Acomodações",
            "Veja outras acomodações dos Chalés Lua Cheia"
        ),
        ChaletOption(
            R.drawable.wind_icon,
            "Ar-condicionado",
            "Cuidados com o ar-condicionado"
        ),
        ChaletOption(
            R.drawable.trash_icon,
            "Lixo",
            "Instruções de como lidar com o lixo do chalé"
        ),
        ChaletOption(
            R.drawable.fireplace_icon,
            "Lareira",
            "Instruções e cuidados sobre o uso da lareira"
        ),
        ChaletOption(
            R.drawable.cooker_icon,
            "Fogão",
            "Instruções e cuidados sobre o uso do fogão"
        ),
        ChaletOption(
            R.drawable.pet_icon,
            "Pet Friendly",
            "Instruções e cuidados com o seu pet dentro do chalé"
        )
    )
    Column {
        chaletOptions.forEach {
            ChaletOptionComponent(it)
        }
    }

}

data class ChaletOption(
    val optionIcon: Int,
    val optionTitle: String,
    val optionDescription: String
)

@Composable
fun ChaletOptionComponent(
    chaletOption: ChaletOption
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
            .clickable { }
            .padding(vertical = 20.dp, horizontal = 30.dp)

    ) {
        Icon(
            painter = painterResource(id = chaletOption.optionIcon),
            contentDescription = "WhatsApp",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 20.dp)
                .weight(1f)
        ) {
            Text(
                text = chaletOption.optionTitle,
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Start)
            )
            Text(
                text = chaletOption.optionDescription,
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
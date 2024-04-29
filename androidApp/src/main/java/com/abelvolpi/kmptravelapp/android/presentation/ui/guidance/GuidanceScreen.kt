package com.abelvolpi.kmptravelapp.android.presentation.ui.guidance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abelvolpi.kmptravelapp.android.presentation.theme.backgroundColor

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuidanceScreen() {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize(),
        topBar = {
            LargeTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor,
                ),
                title = {
                    Text(
                        "Lareira",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                },
                navigationIcon = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) { padding ->
        Text(
            text = text,
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(
                    top = padding.calculateTopPadding(),
                    start = 30.dp,
                    end = 30.dp
                )
                .verticalScroll(rememberScrollState())
        )
    }
}

val text =
    "Nada como poder aquecer a casa numa noite fria, com o barulhinho da lenha e poder fazer um marshmallow na lareira, não é mesmo?\n" +
            "\n" +
            "Para manter a sua segurança vamos te ajudar com algumas dicas importantes sobre a utilização da Lareira:\u2028\u2028NÃO NOS RESPONSABILIZAMOS POR QUALQUER IMPREVISTO ADVINDO DO MAL USO.\n" +
            "\n" +
            "◦ Como acender?\n" +
            "\n" +
            "- Utilize as lenhas que estão disponíveis ao lado da lareira, posicione-as de forma acumulada, inicie o fogo com poucas lenhas e inicialmente com as lenhas mais finas ou gravetos;\n" +
            "- Para ascender o fogo use o acendedor de fogo que disponibilizamos junto as lenhas, posicione ele abaixo das lenhas e concentre ele no centro, dessa forma, ele acenderá gradativamente, sem precisar de outro utensílio;\n" +
            "- Vá adicionando mais lenha aos poucos conforme o fogo for baixando.\n" +
            "\n" +
            "◦ Como apagar o fogo da Lareira?\n" +
            "\n" +
            "- Utilize o borrifador de água disponível para esse procedimento.\n" +
            "\n" +
            "◦ Ao sair, devo apagar a lareira?\n" +
            "\n" +
            "- Sim, para a sua segurança e das demais acomodações.\n" +
            "\n" +
            "◦ Ao dormir, devo apagar a lareira?\n" +
            "\n" +
            "- Certifique-se de que o fogo está baixo, assim, ela deve apagar em breve. Da mesma forma, o chalé se manterá quentinho para você dormir confortavelmente.\n" +
            "\n" +
            "CUIDADO: Para a segurança da sua família, não acenda a Lareira na presença de crianças ou animais de estimação. Bem como não é recomendado ascender caso você ainda esteja se sentindo inseguro em relação ao manuseio. Entre em contato conosco que será um prazer ajudá-los na utilização.\n" +
            "\n" +
            "OBS: Em caso de imprevistos maiores, há um extintor de incêndio disponível no chalé."
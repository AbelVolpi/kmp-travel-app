package com.luacheia.kmptravelapp.android.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.luacheia.kmptravelapp.android.R
import com.luacheia.kmptravelapp.android.presentation.theme.secondaryColor

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarComponent(
    onSearch: ((String) -> Unit)? = null,
    onQueryChange: ((String) -> Unit)? = null,
    trailingIconAction: (() -> Unit)? = null,
) {
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(0.dp, 70.dp)
            .padding(horizontal = 20.dp),
        shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp),
        colors = SearchBarDefaults.colors(
            containerColor = secondaryColor,
            inputFieldColors = TextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                unfocusedPlaceholderColor = Color.White,
                focusedPlaceholderColor = Color.White
            )
        ),
        query = text,
        onQueryChange = {
            text = it
            onQueryChange?.invoke(it)
        },
        onSearch = {
            active = false
            onSearch?.invoke(it)
        },
        active = active,
        onActiveChange = { active = it },
        placeholder = {
            Text(text = "Pesquisar")
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.search_icon),
                contentDescription = "Pesquisar",
                tint = Color.White
            )
        },
        trailingIcon = {
            if (active) {
                Icon(
                    modifier = Modifier.clickable {
                        if (text.isNotEmpty()) {
                            text = ""
                        } else {
                            active = false
                            trailingIconAction?.invoke()
                        }
                    },
                    imageVector = Icons.Default.Close,
                    contentDescription = "Clear",
                    tint = Color.White
                )
            }
        }
    ) {
    }
}

// TODO update search bar
@Preview
@Composable
fun CustomSearchBar(
    onSearch: ((String) -> Unit)? = null,
    onQueryChange: ((String) -> Unit)? = null,
    trailingIconAction: (() -> Unit)? = null,
) {
    var text by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(0.dp, 70.dp)
            .padding(horizontal = 20.dp)
            .background(
                color = secondaryColor,
                shape = RoundedCornerShape(15.dp)
            )
            .focusRequester(focusRequester)
            .onFocusChanged { isFocused = it.isFocused }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.search_icon),
                contentDescription = "Pesquisar",
                tint = Color.White
            )

            Spacer(modifier = Modifier.width(12.dp))

            BasicTextField(
                value = text,
                onValueChange = {
                    text = it
                    onQueryChange?.invoke(it)
                },
                singleLine = true,
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        focusManager.clearFocus()
                        onSearch?.invoke(text)
                    }
                ),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                cursorBrush = SolidColor(Color.White),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterStart // Alinha o texto ao centro verticalmente
                    ) {
                        if (text.isEmpty()) {
                            Text(
                                text = "Pesquisar",
                                color = Color.White.copy(alpha = 0.6f),
                                fontSize = 16.sp
                            )
                        }
                        innerTextField()
                    }
                }
            )

            if (isFocused) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Clear",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable {
                            if (text.isNotEmpty()) {
                                text = ""
                            } else {
                                focusManager.clearFocus()
                                trailingIconAction?.invoke()
                            }
                        }
                )
            }
        }
    }
}





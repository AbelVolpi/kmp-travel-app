package com.luacheia.kmptravelapp.backoffice.sections.core

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kmptravelapp.backoffice.composeapp.generated.resources.Res
import kmptravelapp.backoffice.composeapp.generated.resources.ic_lua_cheia_desktop
import org.jetbrains.compose.resources.painterResource

@Composable
fun SideNavigationBar(currentSection: Section, onScreenSelected: (Section) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(200.dp)
            .background(Color(0xFF161616)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .padding(top = 20.dp, start = 10.dp)
                .align(Alignment.Start)
                .size(70.dp),
            painter = painterResource(Res.drawable.ic_lua_cheia_desktop),
            contentDescription = null
        )

        Spacer(Modifier.height(16.dp))

        NavigationButton(
            Section.CategoriesAndPlaces,
            currentSection == Section.CategoriesAndPlaces,
        ) { onScreenSelected(Section.CategoriesAndPlaces) }
        NavigationButton(
            Section.Accommodations,
            currentSection == Section.Accommodations
        ) { onScreenSelected(Section.Accommodations) }
        NavigationButton(
            Section.Guidelines,
            currentSection == Section.Guidelines
        ) { onScreenSelected(Section.Guidelines) }
    }
}

@Composable
fun NavigationButton(section: Section, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp, vertical = 3.dp)
            .background(
                if (isSelected) Color(color = 0xFF555555) else Color.Transparent,
                RoundedCornerShape(10.dp),
            )
            .clip(RoundedCornerShape(10.dp))
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = section.icon,
            tint = MaterialTheme.colorScheme.onPrimary,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = section.name, color = Color.White)
    }
}
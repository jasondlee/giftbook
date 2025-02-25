package com.steeplesoft.mobile.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.steeplesoft.giftbook.Navy200
import com.steeplesoft.giftbook.Navy700
import com.steeplesoft.mobile.model.NavigationDrawerItem
import giftbook.composeapp.generated.resources.Res
import giftbook.composeapp.generated.resources.app_name
import giftbook.composeapp.generated.resources.compose_multiplatform
import giftbook.composeapp.generated.resources.home
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun DrawerContent(
    gradientColors: List<Color> = listOf(Navy700, Navy200),
    itemClick: (String) -> Unit
) {

    val itemsList = listOf(
        NavigationItem.Home
    )
    //prepareNavigationDrawerItems()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = gradientColors)),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(vertical = 36.dp)
    ) {

        item {
            // user's image
            Image(
                modifier = Modifier
                    .size(size = 120.dp)
                    .clip(shape = CircleShape),
                painter = painterResource(Res.drawable.compose_multiplatform),
                contentDescription = "Profile Image"
            )

            // user's name
            Text(
                modifier = Modifier
                    .padding(top = 12.dp),
                text = stringResource(Res.string.app_name),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

//            // user's email
//            Text(
//                modifier = Modifier.padding(top = 8.dp, bottom = 30.dp),
//                text = stringResource(Res.string.help_email),
//                fontWeight = FontWeight.Normal,
//                fontSize = 16.sp,
//                color = Color.White
//            )
        }

        items(itemsList) {
            NavigationListItem(it) {
                itemClick(it.route)
            }
        }
    }
}

@Composable
fun prepareNavigationDrawerItems(): List<NavigationDrawerItem> {
    return listOf(
        NavigationDrawerItem(
            image = Icons.Default.Home,
            label = stringResource(Res.string.home)
        ),
        /*
        NavigationDrawerItem(
            image = Icons.Default.Search,
            label = stringResource(Res.string.search)
        ),
        NavigationDrawerItem(
            image = Icons.AutoMirrored.Filled.List,
            label = stringResource(Res.string.ministries),
            text = stringResource(Res.string.view_rosters)
        ),
        NavigationDrawerItem(
            image = Icons.Default.Person,
            label = stringResource(Res.string.attendance)
        ),
        NavigationDrawerItem(
            image = Icons.Default.Email,
            label = stringResource(Res.string.communications)
        ),
        NavigationDrawerItem(
            image = Icons.Default.Lock,
            label = stringResource(Res.string.logout)
        )

        */
    )
}

package com.steeplesoft.giftbook.ui.occasionRecip

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.steeplesoft.giftbook.database.model.GiftIdea
import com.steeplesoft.giftbook.ui.drawer.NavigationConfig
import com.steeplesoft.giftbook.ui.general.ActionButton
import com.steeplesoft.giftbook.ui.general.AddEditHeader
import com.steeplesoft.kmpform.components.ConfirmationDialog
import com.steeplesoft.kmpform.components.asyncLoad
import org.koin.compose.koinInject

@ExperimentalMaterial3Api
@Composable
fun viewOccasionRecip(
    component: ViewOccasionRecipient,
    modifier: Modifier = Modifier
) {
    val showDialog = remember { mutableStateOf(false) }
    val status by component.requestStatus.subscribeAsState()
    val gifts by component.gifts.subscribeAsState()
    val nav: StackNavigation<NavigationConfig> = koinInject<StackNavigation<NavigationConfig>>()

    if (showDialog.value) {
        ConfirmationDialog(
            onDismissRequest = { showDialog.value = false },
            onConfirmation = {
                showDialog.value = false
                component.delete()
            },
            dialogTitle = "Confirmation",
            dialogText = "Are you sure you want to remove ${component.recip.name} from ${component.occasion.name}?",
            icon = Icons.Filled.QuestionMark
        )
    }
    asyncLoad(status) {
        ActionButton(
            onClick = {
                component.edit()
            }
        )
        Column(modifier = Modifier.padding(10.dp)) {
            AddEditHeader(
                label = component.occasion.name,
                editClick = { component.edit() },
                deleteClick = { showDialog.value = true }
            )
            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)) {
                    append(component.recip.name)
                }

            }/*, modifier = modifier.bottomBorder(1.dp, color = Color.Red)*/)
            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Target Gift Count: ")
                }
                append(component.occasionRecip.targetCount.toString())
            })
            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Target Gift Cost: $")
                }
                append(component.occasionRecip.targetCost.toString())
            })

            var showCostDialog by remember { mutableStateOf(false) }
            var currentGift by remember { mutableStateOf<GiftIdea?>(null) }

            if (showCostDialog) {
                GiftCostDialog(
                    onSaveRequest = { cost ->
                        currentGift?.let { gift ->
                            component.giftGiven(gift.id, cost)
                        }
                        showCostDialog = false
                    },
                    onCancelRequest = {
                        showCostDialog = false;
                    }
                )
            }

            LazyColumn {
                items(gifts) { gift ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column {
                            Switch(
                                checked = gift.occasionId != null,
                                onCheckedChange = { checked ->
                                    currentGift = gift
                                    showCostDialog = true
                                }
                            )
                        }
                        Column(modifier = Modifier.padding(start = 10.dp)) {
                            Text(
                                text = "${gift.title}${gift.actualCost?.let { " - \$$it" } ?: ""}", fontSize = 18.sp,
                                modifier = modifier.wrapContentHeight(align = Alignment.Bottom),
                            )
                            gift.notes?.let {
                                Text(it, fontStyle = FontStyle.Italic)
                            }
                        }
                    }
                }
                item {
                    Button(modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            nav.bringToFront(NavigationConfig.Home(component.occasion))
                        }) {
                        Text("Done")
                    }
                }
            }
        }
    }
}

@Composable
fun Modifier.bottomBorder(
    thickness: Dp,
    color: Color = colorScheme.primaryContainer
): Modifier {
    val density = LocalDensity.current
    val strokeWidthPx = density.run { thickness.toPx() }
    return this then Modifier.drawBehind {
        val width = size.width
        val height = size.height

        drawLine(
            color = color,
            start = Offset(x = 0f, y = height),
            end = Offset(x = width, y = height),
            strokeWidth = strokeWidthPx
        )
    }
}

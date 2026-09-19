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
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.steeplesoft.giftbook.theme.Spacing
import com.steeplesoft.giftbook.theme.Typography
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.steeplesoft.camper.components.AsyncLoad
import com.steeplesoft.giftbook.NavigationConfig
import com.steeplesoft.giftbook.model.GiftIdea
import com.steeplesoft.giftbook.ui.general.AddEditHeader
import com.steeplesoft.giftbook.ui.general.DeleteConfirmationDialog
import com.steeplesoft.giftbook.ui.general.GiftCostDialog
import org.koin.compose.koinInject

@ExperimentalMaterial3Api
@Composable
fun ViewOccasionRecip(
    component: ViewOccasionRecipient,
    modifier: Modifier = Modifier
) {
    val showDialog = remember { mutableStateOf(false) }
    val status by component.requestStatus.subscribeAsState()
    val gifts by component.gifts.subscribeAsState()
    val recip by component.recip.subscribeAsState()
    val occasion by component.occasion.subscribeAsState()
    val occasionRecip by component.occasionRecip.subscribeAsState()
    val nav: StackNavigation<NavigationConfig> = koinInject<StackNavigation<NavigationConfig>>()

    AsyncLoad(status) {
        val currentRecip = recip ?: return@AsyncLoad
        val currentOccasion = occasion ?: return@AsyncLoad
        val currentOccasionRecip = occasionRecip ?: return@AsyncLoad

        DeleteConfirmationDialog(
            showDialog = showDialog,
            itemName = "${currentRecip.name} from ${currentOccasion.name}",
            onConfirm = { component.delete() }
        )

        Column(modifier = modifier) {
            AddEditHeader(
                label = currentOccasion.name,
                editClick = { component.edit() },
                deleteClick = { showDialog.value = true }
            )
            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = Typography.primaryTextSize)) {
                    append(currentRecip.name)
                }
            })
            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Target Gift Count: ")
                }
                append(currentOccasionRecip.targetCount.toString())
            })
            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Target Gift Cost: $")
                }
                append(currentOccasionRecip.targetCost.toString())
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

            LazyColumn(modifier = Modifier.padding(top = Spacing.screenPadding, bottom = Spacing.screenPadding)) {
                items(gifts, key = { it.id }) { gift ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column {
                            Switch(
                                checked = gift.occasionId != null,
                                onCheckedChange = { checked ->
                                    currentGift = gift
                                    if (checked) {
                                        showCostDialog = true
                                    } else {
                                        component.resetGiftGiven(gift.id)
                                    }
                                }
                            )
                        }
                        Column(modifier = Modifier.padding(start = Spacing.screenPadding)) {
                            val noPad = Modifier.padding(0.dp)
                            Text(
                                text = "${gift.title}${gift.actualCost?.let { " - \$$it" } ?: " - (up to \$${gift.estimatedCost})"}", 
                                fontSize = Typography.secondaryTextSize,
                                modifier = noPad.wrapContentHeight(align = Alignment.Bottom)
                            )
                            gift.notes?.let {
                                Text(it, fontStyle = FontStyle.Italic, modifier = noPad)
                            }
                        }
                    }
                }
                item {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            nav.bringToFront(NavigationConfig.Home(currentOccasion.id))
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

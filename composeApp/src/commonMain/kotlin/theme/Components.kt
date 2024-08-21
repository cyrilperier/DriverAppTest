package theme

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import driverapp.composeapp.generated.resources.Res
import driverapp.composeapp.generated.resources.arrow_back
import driverapp.composeapp.generated.resources.chevron_right

import navigation.Screen
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


private const val SKELETON_LOADING_ANIMATION_LABEL = "skeleton loading animation"
fun Modifier.shimmerBackground(shape: Shape = RoundedCornerShape(16.dp)): Modifier = composed {
    val transition = rememberInfiniteTransition(label = SKELETON_LOADING_ANIMATION_LABEL)
    val translateAnimation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 400f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1500, easing = LinearOutSlowInEasing),
            RepeatMode.Restart
        ),
        label = SKELETON_LOADING_ANIMATION_LABEL,
    )
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.9f),
        Color.LightGray.copy(alpha = 0.4f),
    )
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnimation, translateAnimation),
        end = Offset(translateAnimation + 100f, translateAnimation + 100f),
        tileMode = TileMode.Mirror,
    )
    return@composed this.then(background(brush, shape))
}

@Composable
fun MDWPrimaryButton(
    modifier: Modifier = Modifier,
    isEnable: Boolean = true,
    onClick: () -> Unit,
    label: String,
    borderColor: Color,
    labelColor: Color,
    iconTint: Color
) {
    OutlinedButton(
        enabled = isEnable,
        modifier = modifier.alpha(if (isEnable) 1f else 0f),
        onClick = onClick,
        border = BorderStroke(1.5.dp, borderColor),
        contentPadding = PaddingValues(
            horizontal = 12.dp,
            vertical = 4.dp
        )
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = labelColor
        )
        Icon(
            modifier = Modifier.padding(start = 4.dp).size(16.dp),
            painter = painterResource(resource = Res.drawable.chevron_right),
            contentDescription = null,
            tint = iconTint
        )
    }
}

@Composable
fun MDWCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    borderColor: Color = Color.Transparent,
    paddingValues: PaddingValues = PaddingValues(
        start = 16.dp,
        top = 8.dp,
        end = 16.dp,
        bottom = 8.dp
    ),
    verticalSpace: Int = 8,
    content: @Composable (ColumnScope) -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        border = BorderStroke(2.dp, borderColor),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(verticalSpace.dp)
        ) {
            content(this)
        }
    }
}

@Composable
fun MDWDevicesCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    painter: Painter,
    opacity: Float = 0.9f,
    content: @Composable (ColumnScope) -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.fillMaxSize().alpha(opacity).background(backgroundColor)
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp)
            ) {
                content(this)
            }
        }
    }
}

@Composable
fun BottomAppBar(
    modifier: Modifier = Modifier,
    selectedScreen: State<Screen>,
    onItemSelected: (Screen) -> Unit
) {
    val rootScreens = listOf(
       Screen.Stay
    )
    Row(
        modifier = modifier.fillMaxWidth().height(90.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        rootScreens.forEach { item ->
            // if the screen shows the details, we should consider the parent screen
            val selectedRootScreen = if (
                rootScreens.contains(selectedScreen.value)
            ) {
                selectedScreen.value
            } else {
                selectedScreen.value.parentRootScreen
            }
            val isSelected = if (false) {
                // if the screen is the tips screen, we should select it manually because of the dynamic content
                true
            } else {
                item == selectedRootScreen
            }
            Column(
                modifier = Modifier
                    .weight(0.25f)
                    .fillMaxHeight()
                    .clickable {
                        if (!isSelected) {
                            onItemSelected(item)
                        }
                    }
            )
            {
                HorizontalDivider(
                    modifier = Modifier.alpha(if (isSelected) 1f else 0f),
                    thickness = 2.dp,
                    color = skyBlue_80
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
                ) {
                    if (item.bottomBarIcon != null && item.bottomBarSelectedIcon != null) {
                        Image(
                            painter = painterResource(
                                if (isSelected) item.bottomBarSelectedIcon else item.bottomBarIcon
                            ),
                            contentDescription = null,
                        )
                    }
                    item.bottomBarLabel?.let {
                        Text(
                            text = stringResource(it),
                            style = TextStyle(
                                fontWeight = FontWeight.W400,
                                fontSize = 12.sp,
                                lineHeight = 20.sp,
                                letterSpacing = 0.sp
                            ),
                            color = if (isSelected) {
                                skyBlue_80
                            } else {
                                skyBlue_60
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MDWGenericTopAppBar(
    modifier: Modifier = Modifier,
    currentScreen: Screen,
    onBackPressed: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            modifier = Modifier.clickable {
                onBackPressed()
            },
            painter = painterResource(Res.drawable.arrow_back),
            contentDescription = null
        )
        currentScreen.topBarIcon?.let {
            Image(
                painter = painterResource(currentScreen.topBarIcon),
                contentDescription = null
            )
        }
        currentScreen.topBarLabel?.let {
            Text(
                text = stringResource(currentScreen.topBarLabel),
                style = MaterialTheme.typography.titleMedium,
                color = skyBlue_80
            )
        }
        currentScreen.customTopBarLabel?.let {
            Text(
                text = currentScreen.customTopBarLabel,
                style = MaterialTheme.typography.titleMedium,
                color = skyBlue_80
            )
        }
    }
}

@Composable
fun NoTipCard(
    message: StringResource,
    buttonLabel: StringResource,
    onClick: () -> Unit
) {
    MDWCard(
        backgroundColor = skyBlue_20
    ) { columnScope ->
        columnScope.run {
            Text(
                text = stringResource(message),
                style = MaterialTheme.typography.bodyLarge,
                fontStyle = FontStyle.Italic,
                color = skyBlue_80
            )
            MDWPrimaryButton(
                modifier = Modifier.align(Alignment.End),
                onClick = onClick,
                label = stringResource(buttonLabel),
                labelColor = skyBlue_80,
                iconTint = skyBlue_80,
                borderColor = skyBlue_80
            )
        }
    }
}

@Composable
fun SectionTitle(
    modifier: Modifier = Modifier,
    icon: DrawableResource,
    title: StringResource,
    color: Color = skyBlue_80,
    iconColor: Color = skyBlue_80
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(13.dp)
    ) {
        Icon(
            painter = painterResource(resource = icon),
            contentDescription = null,
            tint = iconColor
        )
        Text(
            text = stringResource(title),
            style = MaterialTheme.typography.titleMedium,
            color = color
        )
    }
}
//
//@Composable
//expect fun MDWWebView(
//    modifier: Modifier = Modifier,
//    htmlContent: String
//)


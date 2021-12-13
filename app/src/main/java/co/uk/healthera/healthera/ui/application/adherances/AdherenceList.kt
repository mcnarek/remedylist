package co.uk.healthera.healthera.ui.application.adherances

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import co.uk.healthera.healthera.R
import co.uk.healthera.healthera.domain.model.AdherenceDataModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


/**
 * Created by Narek Hayrapetyan on 11 Dec 2021.
 * Copyright: Healthera
 * E-Mail: mcnarek@gmail.com
 */

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AdherenceDatePager(
    data: Map<String, List<AdherenceDataModel>>,
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit = {},
) {
    Column(modifier = modifier.fillMaxSize()) {
        val state: PagerState = rememberPagerState()
        val keys: Set<String> = data.keys
        val count: Int = keys.size
        var date: String by remember {
            mutableStateOf(keys.elementAt(state.currentPage))
        }
        val corScope: CoroutineScope = rememberCoroutineScope()

        var enableBack: Boolean by remember {
            mutableStateOf(state.currentPage > 0)
        }
        var enableForward: Boolean by remember {
            mutableStateOf(state.currentPage < count - 1)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            ButtonNavs(Icons.Default.ArrowBack, "back", enable = enableBack) {
                enableBack = state.currentPage > 0
                if (enableBack) {
                    corScope.launch {
                        state.animateScrollToPage(state.currentPage - 1)
                    }
                }
            }

            Text(
                text = date,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                fontSize = 22.sp,
                color = colorResource(
                    id = R.color.purple_200
                ),
                textAlign = TextAlign.Center
            )

            ButtonNavs(Icons.Default.ArrowForward, "forward", enable = enableForward) {
                enableForward = state.currentPage < count
                if (enableForward) {
                    corScope.launch {
                        state.animateScrollToPage(state.currentPage + 1)
                    }
                }
            }
        }

        HorizontalPager(
            count, modifier = Modifier
                .fillMaxSize()
                .weight(1f), state, contentPadding = PaddingValues(8.dp)
        ) {
            AdherenceList(data = data[keys.elementAt(it)] ?: emptyList(), onRefresh = onRefresh)
        }

        LaunchedEffect(state) {
            // Collect from the a snapshotFlow reading the currentPage
            snapshotFlow { state.currentPage }.collect { page ->
                enableForward = page < count - 1
                enableBack = page > 0
                date = keys.elementAt(page)
            }
        }
    }
}

@Composable
private fun AdherenceList(
    data: List<AdherenceDataModel>,
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit = {},
) {
    SwipeRefresh(
        state = SwipeRefreshState(false), onRefresh = {
            onRefresh.invoke()
        }) {
        LazyColumn(modifier = modifier.fillMaxSize()) {
            items(data) {
                AdherenceItem(data = it)
            }
        }
    }
}

@Composable
private fun AdherenceItem(data: AdherenceDataModel, modifier: Modifier = Modifier) {
    val min = 50.dp
    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .heightIn(min = min),
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            val (icon, date, desc, action) = createRefs()

            Image(
                modifier = Modifier
                    .background(Color.DarkGray, CircleShape)
                    .sizeIn(min, min)
                    .constrainAs(icon) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                painter = painterResource(id = R.drawable.ic_pill),
                contentDescription = data.remedy?.name,
                contentScale = ContentScale.Inside
            )

            Text(
                text = data.alarmTime, modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(date) {
                        start.linkTo(icon.end, 8.dp)
                        top.linkTo(parent.top, 8.dp)
                    },
                fontSize = 20.sp
            )

            data.remedy?.let {
                Text(
                    text = "${it.name}, ${data.alarmTime}",
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(desc) {
                            start.linkTo(date.start, 8.dp)
                            top.linkTo(date.bottom, 8.dp)
                            bottom.linkTo(parent.bottom, 8.dp)
                        })
            }

            if (!data.action.isNullOrEmpty()) {
                Text(
                    text = data.action, modifier = Modifier
                        .constrainAs(action) {
                            end.linkTo(parent.end, 8.dp)
                            top.linkTo(parent.top, 8.dp)
                        },
                    fontSize = 18.sp,
                    color = colorResource(id = R.color.purple_200)
                )
            }
        }
    }
}

@Composable
private fun ButtonNavs(
    icon: ImageVector,
    contentDesc: String? = null,
    enable: Boolean = true,
    onClick: () -> Unit = {},
) {
    IconButton(onClick = onClick, enabled = enable) {
        Icon(
            imageVector = icon,
            contentDescription = contentDesc,
            tint = if (enable) colorResource(
                id = R.color.purple_200
            ) else colorResource(
                id = R.color.purple_500
            )
        )
    }
}

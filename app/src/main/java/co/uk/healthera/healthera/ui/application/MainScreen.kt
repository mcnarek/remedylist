package co.uk.healthera.healthera.ui.application

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import co.uk.healthera.healthera.R
import co.uk.healthera.healthera.ui.application.adherances.AdherenceDatePager
import co.uk.healthera.healthera.ui.application.adherances.AdherenceViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


/**
 * Created by Narek Hayrapetyan on 11 Dec 2021.
 * Copyright: Healthera
 * E-Mail: mcnarek@gmail.com
 */

private val showError: MutableStateFlow<Boolean> by lazy {
    MutableStateFlow(false)
}

@Composable
fun MainScreen(viewModel: AdherenceViewModel = hiltViewModel()) {
    Column(modifier = Modifier.fillMaxSize()) {
        val error: String? by viewModel.error.collectAsState()
        showError.value = !error.isNullOrEmpty()
        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = colorResource(id = R.color.purple_200)
                ) {
                    Text(text = stringResource(R.string.title_schedule))
                }
            },
            snackbarHost = {
                val show: Boolean by showError.collectAsState()

                if (show) {
                    SnackBar(error, it) {
                        viewModel.getData()
                    }
                }
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                contentAlignment = Alignment.Center
            ) {
                val loading by viewModel.loading.collectAsState()
                if (loading) {
                    CircularProgressIndicator(color = colorResource(id = R.color.purple_200))
                }

                val data by viewModel.adherenceData.collectAsState()
                if (data.isNotEmpty()) {
                    AdherenceDatePager(data = data, modifier = Modifier.fillMaxSize()) {
                        viewModel.getData()
                    }
                } else {
                    if (!loading) {
                        Text(text = stringResource(R.string.text_no_items))
                    }
                }
            }
        }

        LaunchedEffect(key1 = viewModel) {
            viewModel.getData()
        }
    }
}

@Composable
private fun SnackBar(
    error: String?,
    it: SnackbarHostState,
    onAction: () -> Unit = {},
) {
    val scope = rememberCoroutineScope()

    Snackbar(
        action = {
            onAction()
        }, backgroundColor = MaterialTheme.colors.onBackground) {
        Row(Modifier
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = error ?: "",
                Modifier
                    .padding(end = 18.dp)
                    .weight(1f))

            Text(text = stringResource(R.string.retry_action),
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        onAction.invoke()
                    }, color = MaterialTheme.colors.primaryVariant)
        }
    }

    LaunchedEffect(key1 = error, block = {
        scope.launch {
            delay(3000)

            showError.value = false
        }
    })
}
package co.uk.healthera.healthera.ui.application

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import co.uk.healthera.healthera.R
import co.uk.healthera.healthera.ui.application.adherances.AdherenceDatePager
import co.uk.healthera.healthera.ui.application.adherances.AdherenceViewModel


/**
 * Created by Narek Hayrapetyan on 11 Dec 2021.
 * Copyright: Healthera
 * E-Mail: mcnarek@gmail.com
 */

@Composable
fun MainScreen(viewModel: AdherenceViewModel = hiltViewModel()) {
    Column(modifier = Modifier.fillMaxSize()) {
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
                val error: String? by viewModel.error.collectAsState()
                if (!error.isNullOrEmpty()) {
                    Snackbar {
                        Text(text = error ?: "", modifier = Modifier.fillMaxWidth())
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
                    AdherenceDatePager(data = data, modifier = Modifier.fillMaxSize())
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
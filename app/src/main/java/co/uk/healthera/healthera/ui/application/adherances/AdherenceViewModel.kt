package co.uk.healthera.healthera.ui.application.adherances

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.uk.healthera.healthera.domain.model.AdherenceDataModel
import co.uk.healthera.healthera.domain.usecase.AdherenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by Narek Hayrapetyan on 11 Dec 2021.
 * Copyright: Healthera
 * E-Mail: mcnarek@gmail.com
 */
@HiltViewModel
class AdherenceViewModel
@Inject
constructor(
    private val _adherenceUseCase: AdherenceUseCase,
) : ViewModel() {
    private val _adherenceData: MutableStateFlow<Map<String, List<AdherenceDataModel>>> by lazy {
        MutableStateFlow(mutableMapOf())
    }
    val adherenceData: StateFlow<Map<String, List<AdherenceDataModel>>>
        get() = _adherenceData.asStateFlow()

    private val _loading: MutableStateFlow<Boolean> by lazy {
        MutableStateFlow(false)
    }

    val loading: StateFlow<Boolean>
        get() = _loading.asStateFlow()

    private val _error: MutableStateFlow<String?> by lazy {
        MutableStateFlow(null)
    }
    val error: StateFlow<String?>
        get() = _error.asStateFlow()

    fun getData() {
        viewModelScope.launch {
            _loading.value = true
            _adherenceUseCase.getAdherenceList()
                .catch {
                    _loading.value = false
                    _error.value = it.message
                }
                .collect {
                    _loading.value = false
                    _adherenceData.value = it
                }
        }
    }
}
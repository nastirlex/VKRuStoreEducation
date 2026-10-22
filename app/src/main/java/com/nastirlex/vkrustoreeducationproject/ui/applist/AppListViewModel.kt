package com.nastirlex.vkrustoreeducationproject.ui.applist

import androidx.lifecycle.ViewModel
import com.nastirlex.vkrustoreeducationproject.data.AppCatalog
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class AppListViewModel : ViewModel() {
    // Created once for this navigation entry, not on every screen recomposition/rotation.
    private val _uiState = MutableStateFlow(AppListUiState(apps = AppCatalog.apps))
    val uiState = _uiState.asStateFlow()

    // Effects are transient: a new collector must not receive a previous toolbar click.
    private val _effects = MutableSharedFlow<AppListEffect>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val effects = _effects.asSharedFlow()

    fun onToolbarClick() {
        _effects.tryEmit(AppListEffect.ShowToolbarSnackbar)
    }
}

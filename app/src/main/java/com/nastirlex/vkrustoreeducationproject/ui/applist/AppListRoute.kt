package com.nastirlex.vkrustoreeducationproject.ui.applist

import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nastirlex.vkrustoreeducationproject.R
import com.nastirlex.vkrustoreeducationproject.data.CatalogApp
import com.nastirlex.vkrustoreeducationproject.ui.AppListScreen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AppListRoute(
    onAppClick: (CatalogApp) -> Unit,
    viewModel: AppListViewModel = viewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    // Not saved: a visible Snackbar is not restored after activity recreation.
    val snackbarHostState = remember { SnackbarHostState() }
    val toolbarMessage = stringResource(R.string.app_list_toolbar_snackbar)

    LaunchedEffect(viewModel, lifecycleOwner, toolbarMessage) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effects.collectLatest { effect ->
                when (effect) {
                    AppListEffect.ShowToolbarSnackbar -> snackbarHostState.showSnackbar(toolbarMessage)
                }
            }
        }
    }

    AppListScreen(
        state = state,
        onAppClick = onAppClick,
        onToolbarClick = viewModel::onToolbarClick,
        snackbarHost = { SnackbarHost(snackbarHostState) },
    )
}

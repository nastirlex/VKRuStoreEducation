package com.nastirlex.vkrustoreeducationproject.ui.applist

import com.nastirlex.vkrustoreeducationproject.data.CatalogApp

data class AppListUiState(val apps: List<CatalogApp>)

sealed interface AppListEffect {
    data object ShowToolbarSnackbar : AppListEffect
}

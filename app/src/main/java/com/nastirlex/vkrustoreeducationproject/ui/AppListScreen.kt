package com.nastirlex.vkrustoreeducationproject.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nastirlex.vkrustoreeducationproject.R
import com.nastirlex.vkrustoreeducationproject.data.AppCatalog
import com.nastirlex.vkrustoreeducationproject.data.CatalogApp
import com.nastirlex.vkrustoreeducationproject.ui.applist.AppListUiState
import com.nastirlex.vkrustoreeducationproject.ui.components.AppListItem
import com.nastirlex.vkrustoreeducationproject.ui.theme.VKRuStoreEducationProjectTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppListScreen(
    state: AppListUiState,
    onAppClick: (CatalogApp) -> Unit,
    onToolbarClick: () -> Unit,
    snackbarHost: @Composable () -> Unit = {},
) {
    Scaffold(
        modifier = Modifier.testTag("app-list"),
        snackbarHost = snackbarHost,
        topBar = {
            TopAppBar(
                modifier = Modifier.clickable(
                    role = Role.Button,
                    onClickLabel = stringResource(R.string.app_list_toolbar_click),
                    onClick = onToolbarClick,
                ),
                title = { Text("Приложения") },
                navigationIcon = {
                    Image(painterResource(R.drawable.ic_store), "RuStore Education",
                        Modifier.padding(start = 16.dp, end = 12.dp).size(36.dp).clip(RoundedCornerShape(10.dp)))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
            )
        },
    ) { padding ->
        LazyColumn(contentPadding = PaddingValues(bottom = 16.dp), modifier = Modifier.padding(padding)) {
            items(state.apps, key = { it.id }) { app ->
                AppListItem(app, onClick = { onAppClick(app) })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CatalogPreview() {
    VKRuStoreEducationProjectTheme {
        AppListScreen(
            state = AppListUiState(AppCatalog.apps),
            onAppClick = {},
            onToolbarClick = {},
        )
    }
}

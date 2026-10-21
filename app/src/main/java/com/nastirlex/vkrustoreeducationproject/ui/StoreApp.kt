package com.nastirlex.vkrustoreeducationproject.ui

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nastirlex.vkrustoreeducationproject.data.AppCatalog
import com.nastirlex.vkrustoreeducationproject.ui.appdetails.AppDetailsContent
import com.nastirlex.vkrustoreeducationproject.ui.appdetails.AppDetailsState
import kotlinx.coroutines.launch

private const val LIST = "apps"
private const val DETAILS = "apps/{appId}"

@Composable
fun StoreApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = LIST) {
        composable(LIST) {
            AppListScreen(onAppClick = { navController.navigate("apps/${it.id}") { launchSingleTop = true } })
        }
        composable(DETAILS, arguments = listOf(navArgument("appId") { type = NavType.StringType })) { entry ->
            val app = AppCatalog.find(entry.arguments?.getString("appId"))
            if (app != null) {
                var collapsed by rememberSaveable { mutableStateOf(true) }
                val context = LocalContext.current
                val snackbar = remember { SnackbarHostState() }
                val scope = rememberCoroutineScope()
                Scaffold(snackbarHost = { SnackbarHost(snackbar) }) { padding ->
                    AppDetailsContent(
                        content = AppDetailsState.Content(app.details(), collapsed),
                        onBackClick = { navController.popBackStack() },
                        onShareClick = {
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, "${app.name} — ${app.summary}")
                            }
                            context.startActivity(Intent.createChooser(intent, "Поделиться приложением"))
                        },
                        onInstallClick = { scope.launch { snackbar.showSnackbar("Учебный каталог: установка недоступна") } },
                        onReadMoreClick = { collapsed = false },
                        onDeveloperClick = { scope.launch { snackbar.showSnackbar("Разработчик: ${app.developer}") } },
                        // Return to the existing catalog entry and preserve its scroll position.
                        onShowcaseAppClick = { navController.popBackStack(LIST, inclusive = false) },
                        modifier = Modifier.padding(padding).fillMaxSize().testTag("app-details"),
                    )
                }
            } else {
                Column(Modifier.fillMaxSize().safeDrawingPadding()) {
                    Text("Приложение не найдено")
                    TextButton(onClick = { navController.popBackStack() }) { Text("К списку приложений") }
                }
            }
        }
    }
}

package com.nastirlex.vkrustoreeducationproject.ui.appdetails

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.nastirlex.vkrustoreeducationproject.data.AppCatalog
import com.nastirlex.vkrustoreeducationproject.ui.components.AppListItem

@Composable
fun Showcase(onAppClick: () -> Unit, modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(pageCount = { AppCatalog.PAGE_COUNT })
    Column(modifier.fillMaxWidth().testTag("showcase")) {
        Row(Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Витрина", style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.semantics { heading() })
            Text("${pagerState.currentPage + 1} / ${AppCatalog.PAGE_COUNT}",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        HorizontalPager(state = pagerState, verticalAlignment = Alignment.Top,
            modifier = Modifier.fillMaxWidth().testTag("showcase-pager")) { page ->
            Column(Modifier.fillMaxWidth()) {
                AppCatalog.showcasePages[page].forEach { app ->
                    AppListItem(app = app, onClick = onAppClick)
                }
            }
        }
    }
}

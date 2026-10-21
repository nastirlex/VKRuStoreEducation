// Adapted from maltsevmicyou/VkEducation, b4c673ba (see THIRD_PARTY_NOTICES.md).
package com.nastirlex.vkrustoreeducationproject.ui.appdetails

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nastirlex.vkrustoreeducationproject.data.AppDetails
import com.nastirlex.vkrustoreeducationproject.data.Category

@Composable
fun AppDetailsContent(
    content: AppDetailsState.Content,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onInstallClick: () -> Unit,
    onReadMoreClick: () -> Unit,
    onDeveloperClick: () -> Unit,
    onShowcaseAppClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val appDetails = content.appDetails
    val descriptionCollapsed = content.descriptionCollapsed

    Column(modifier.verticalScroll(rememberScrollState())) {
        Toolbar(
            onBackClick = onBackClick,
            onShareClick = onShareClick,
        )
        Spacer(Modifier.height(8.dp))
        AppDetailsHeader(
            appDetails = appDetails,
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        Spacer(Modifier.height(16.dp))
        InstallButton(
            onClick = onInstallClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(12.dp))
        ScreenshotsList(
            screenshotResList = appDetails.screenshotResList,
            contentPadding = PaddingValues(horizontal = 16.dp),
        )
        Spacer(Modifier.height(12.dp))
        AppDescription(
            description = appDetails.description,
            collapsed = descriptionCollapsed,
            onReadMoreClick = onReadMoreClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )
        Spacer(Modifier.height(12.dp))
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.outlineVariant,
        )
        Spacer(Modifier.height(12.dp))
        Developer(
            name = appDetails.developer,
            onClick = onDeveloperClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
        )
        Spacer(Modifier.height(24.dp))
        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
        Showcase(onAppClick = onShowcaseAppClick)
        Spacer(Modifier.height(24.dp))
    }
}

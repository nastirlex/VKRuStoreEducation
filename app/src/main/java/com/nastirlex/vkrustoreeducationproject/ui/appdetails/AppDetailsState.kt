package com.nastirlex.vkrustoreeducationproject.ui.appdetails

import com.nastirlex.vkrustoreeducationproject.data.AppDetails

object AppDetailsState {
    data class Content(val appDetails: AppDetails, val descriptionCollapsed: Boolean)
}

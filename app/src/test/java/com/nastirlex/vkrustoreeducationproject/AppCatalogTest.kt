package com.nastirlex.vkrustoreeducationproject

import com.nastirlex.vkrustoreeducationproject.data.AppCatalog
import org.junit.Assert.*
import org.junit.Test

class AppCatalogTest {
    @Test fun showcaseHasExactlyNineDistinctAppsInThreeFullPages() {
        assertEquals(3, AppCatalog.showcasePages.size)
        assertTrue(AppCatalog.showcasePages.all { it.size == 3 })
        assertEquals(9, AppCatalog.showcasePages.flatten().map { it.id }.distinct().size)
    }

    @Test fun everyCatalogAppHasMatchingDetailsAndRequiredListFields() {
        AppCatalog.apps.forEach { app ->
            assertEquals(app, AppCatalog.find(app.id))
            assertEquals(app.id, app.details().id)
            assertEquals(app.name, app.details().name)
            assertEquals(app.iconRes, app.details().iconRes)
            assertTrue(app.summary.isNotBlank())
            assertTrue(app.categoryLabel.isNotBlank())
            assertTrue(app.details().screenshotResList.isNotEmpty())
        }
        assertNull(AppCatalog.find("missing-app"))
    }
}

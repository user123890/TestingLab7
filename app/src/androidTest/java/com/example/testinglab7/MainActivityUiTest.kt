
package com.example.testinglab7

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class MainActivityUiTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private val page: MainScreenPage
        get() = MainScreenPage(composeRule)

    @Test
    fun debugButtonIsDisplayed() {
        page.debugButton().assertIsDisplayed()
    }

    @Test
    fun infoButtonIsDisplayed() {
        page.infoButton().assertIsDisplayed()
    }

    @Test
    fun warningButtonIsDisplayed() {
        page.warningButton().assertIsDisplayed()
    }

    @Test
    fun errorButtonIsDisplayed() {
        page.errorButton().assertIsDisplayed()
    }

    @Test
    fun debugButtonCanBeClicked() {
        page.debugButton().performClick()
    }

    @Test
    fun infoButtonCanBeClicked() {
        page.infoButton().performClick()
    }

    @Test
    fun warningButtonCanBeClicked() {
        page.warningButton().performClick()
    }

    @Test
    fun errorButtonCanBeClicked() {
        page.errorButton().performClick()
    }

    @Test
    fun allLevelsButtonCanBeClicked() {
        page.allLevelsButton().performClick()
    }

    @Test
    fun allLevelsButtonIsDisplayed() {
        page.allLevelsButton().assertIsDisplayed()
    }
}

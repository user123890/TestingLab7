
package com.example.testinglab7

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithText

class MainScreenPage(
    private val rule: ComposeContentTestRule
) {

    fun debugButton(): SemanticsNodeInteraction {
        return rule.onNodeWithText("Log.d() — Debug")
    }

    fun infoButton(): SemanticsNodeInteraction {
        return rule.onNodeWithText("Log.i() — Info")
    }

    fun warningButton(): SemanticsNodeInteraction {
        return rule.onNodeWithText("Log.w() — Warning")
    }

    fun errorButton(): SemanticsNodeInteraction {
        return rule.onNodeWithText("Log.e() — Error")
    }

    fun allLevelsButton(): SemanticsNodeInteraction {
        return rule.onNodeWithText("Проверить все уровни")
    }
}

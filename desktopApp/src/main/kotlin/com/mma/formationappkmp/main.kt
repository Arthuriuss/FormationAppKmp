package com.mma.formationappkmp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.mma.formationappkmp.di.initKoin

fun main() = application {

    initKoin()

    Window(
        onCloseRequest = ::exitApplication,
        title = "FormationAppKmp",
    ) {
        App()
    }
}
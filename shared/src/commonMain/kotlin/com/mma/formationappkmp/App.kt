package com.mma.formationappkmp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mma.formationappkmp.presentation.ui.AppNavigation
import com.mma.formationappkmp.presentation.ui.theme.FormationKmpTheme

@Composable
@Preview
fun App() {
    FormationKmpTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            AppNavigation (
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}
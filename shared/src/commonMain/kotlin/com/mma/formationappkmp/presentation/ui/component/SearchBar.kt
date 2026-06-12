package com.mma.formationappkmp.presentation.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mma.formationappkmp.presentation.ui.theme.FormationKmpTheme

@Composable
fun SearchBar(
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {}
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchBarPreview() {
    FormationKmpTheme() {
        SearchBar(
            value = "Tapez ici...",
            modifier = Modifier.padding(16.dp)
        )
    }
}
package com.betapp.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ErrorText(error: Throwable) {
    Text(
        text = "Error: ${error.message}",
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}
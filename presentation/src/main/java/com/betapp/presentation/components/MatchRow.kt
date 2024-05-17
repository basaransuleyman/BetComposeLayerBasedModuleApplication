package com.betapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.betapp.domain.model.Match

@Composable
fun MatchRow(match: Match, onMatchClicked: (Long) -> Unit, onFavoriteClicked: (Match) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {
                onMatchClicked(match.id)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = match.homeTeam.name,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Start
        )

        Text(
            text = "${match.score.homeTeamScore} - ${match.score.awayTeamScore}",
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = match.awayTeam.name,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.End
        )

        IconButton(onClick = { onFavoriteClicked(match) }) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Favorite",
                tint = if (match.isFavorite) Color.Red else Color.Gray
            )
        }
    }
}

package com.betapp.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Tournament(val id: Long, val name: String, val flagUrl: String)

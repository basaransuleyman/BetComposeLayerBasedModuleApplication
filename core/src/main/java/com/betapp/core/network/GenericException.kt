package com.betapp.core.network

import java.io.IOException

data class GenericException(
    override val message: String?,
    val hasUserFriendlyMessage: Boolean
) : IOException()
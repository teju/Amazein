package com.amazein.model.home

import com.amazein.model.Error

data class Home(
    val `data`: Data,
    val error: Error = Error(),
    val status: Boolean
)
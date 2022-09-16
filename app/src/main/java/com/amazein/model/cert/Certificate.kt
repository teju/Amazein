package com.amazein.model.cert

import com.amazein.model.Error

data class Certificate(
    val `data`: Data = Data(),
    val error: Error = Error(),
    val status: Boolean = false
)
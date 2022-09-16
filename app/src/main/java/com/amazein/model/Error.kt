package com.amazein.model

data class Error(
    var error: String = "",
    var errorCode: String = "",
    var title: String = "",
    var message: String = ""
)
package com.amazein.model.cert

data class Data(
    val holderImage: String = "",
    val holderTags: List<HolderTag> = listOf(),
    val nfcTag: String = "",
    val verifiedIcon: String = "",
    val verifiedMsg: String = "",
    val verifiedMsgColor: String = ""
)
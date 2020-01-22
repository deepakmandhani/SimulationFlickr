package com.pankaj.halodoc.model

import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("page")
    var page: Int = 0,
    @SerializedName("nbPages")
    var nbPage: Int = 0,
    @SerializedName("hits")
    var hits: List<Hits> = emptyList(),
    @SerializedName("query")
    var query: String = ""
)
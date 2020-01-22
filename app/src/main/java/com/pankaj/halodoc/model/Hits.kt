package com.pankaj.halodoc.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "hits")
data class Hits(
    @SerializedName("objectID")
    @PrimaryKey var objectID: Long = 0,
    @SerializedName("created_at_i")
    @ColumnInfo(name = "created_at_i")
    var createdAt: Long = System.currentTimeMillis(),
    @SerializedName("title")
    @ColumnInfo(name = "title")
    var title: String? = null,
    @SerializedName("url")
    @ColumnInfo(name = "url")
    var url: String? = null,
    @SerializedName("author")
    @ColumnInfo(name = "author")
    var author: String = "",
    @SerializedName("points")
    @ColumnInfo(name = "points")
    var points: String = "",
    @SerializedName("num_comments")
    @ColumnInfo(name = "num_comments")
    var comments: String = "",
    @ColumnInfo(name = "searchFor")
    var searchFor: String = ""
)
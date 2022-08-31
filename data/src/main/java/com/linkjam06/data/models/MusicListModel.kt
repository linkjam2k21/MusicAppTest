package com.linkjam06.data.models


data class MusicListModel (
    val resultCount: Long,
    val results: List<MusicListResult>
)

data class MusicListResult (

    val collectionType: String? = null,
    val amgArtistId: Long? = null,
    val copyright: String? = null,

    val wrapperType: String,
    val kind: String,

    val artistId: Long,

    val collectionId: Long,

    val trackId: Long,

    val artistName: String,
    val collectionName: String,
    val trackName: String,
    val collectionCensoredName: String,
    val trackCensoredName: String,

    val artistViewURL: String,

    val collectionViewURL: String,

    val trackViewURL: String,

    val previewURL: String,

    val artworkUrl30: String,
    val artworkUrl60: String,
    val artworkUrl100: String,
    val collectionPrice: Double,
    val trackPrice: Double,
    val releaseDate: String,
    val collectionExplicitness: String,
    val trackExplicitness: String,
    val discCount: Long,
    val discNumber: Long,
    val trackCount: Long,
    val trackNumber: Long,
    val trackTimeMillis: Long,
    val country: String,
    val currency: String,
    val primaryGenreName: String,
    val isStreamable: Boolean
)

package com.itunesapp.repository.model

enum class MediaType(val value: String?) {

    ALL("all"),
    MOVIES("movie"),
    MUSIC("music"),
    APPS("software"),
    BOOKS("ebook");

    companion object {

        fun getTypeByString(kind: String?): MediaType{

            return when (kind) {
                MOVIES.value -> MOVIES
                MUSIC.value -> MUSIC
                APPS.value -> APPS
                BOOKS.value -> BOOKS
                else -> ALL
            }
        }
    }
}
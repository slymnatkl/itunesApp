package com.itunesapp.repository.model

enum class EntityType(val value: String?) {

    NONE(null),
    MOVIES("movie"),
    MUSIC("musicTrack"),
    APPS("software"),
    BOOKS("ebook");

    companion object {
        fun getTypeByString(kind: String?): EntityType{

            return when (kind) {
                MOVIES.value -> MOVIES
                MUSIC.value -> MUSIC
                APPS.value -> APPS
                BOOKS.value -> BOOKS
                else -> NONE
            }
        }
    }
}
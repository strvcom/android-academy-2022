package com.strv.movies.data.mapper

interface Mapper<I, O> {
    fun map(from: I): O
}
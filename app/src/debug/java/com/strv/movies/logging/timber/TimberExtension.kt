package com.strv.movies.logging.timber

import com.strv.movies.MoviesApplication
import timber.log.Timber

fun MoviesApplication.plantLogReportingTree() {
    Timber.plant(TimberDebugTree())
}
package com.strv.com

import com.strv.movies.MoviesApplication
import com.strv.movies.logging.timber.TimberDebugTree
import timber.log.Timber


fun MoviesApplication.plantCrashReportingTree() {
    Timber.plant(TimberDebugTree())
}
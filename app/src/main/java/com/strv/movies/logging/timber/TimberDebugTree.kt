package com.strv.movies.logging.timber

import timber.log.Timber

class TimberDebugTree : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String {
        return "Otter:" + element.fileName + ":" + element.lineNumber + "#" + element.methodName
    }
}
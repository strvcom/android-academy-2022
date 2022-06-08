package com.strv.movies.logging.timber

import android.util.Log
import timber.log.Timber

private const val CALL_STACK_INDEX = 5

/** A tree which logs important information for crash reporting.  */
class CrashReportingTree() : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        val priorityString = logPriorityToString(priority)

        val stackTrace = Throwable().stackTrace
        val customTag = if (stackTrace.size > CALL_STACK_INDEX) {
            try {
                val element = stackTrace[CALL_STACK_INDEX]
                "movies:(${element.fileName}:${element.lineNumber})#${element.methodName}"
            } catch (e: Exception) {
                "fallbackTag:$tag"
            }
        } else {
            tag
        }
        val messageWithPrefix = "| $priorityString/$customTag: $message"


        //TODO: connect here your error tracking endpoints
        //FirebaseCrashlytics.getInstance().log(messageWithPrefix)


    }

    private fun logPriorityToString(priority: Int): String {
        return when (priority) {
            Log.VERBOSE -> "V"
            Log.DEBUG -> "D"
            Log.INFO -> "I"
            Log.WARN -> "W"
            Log.ERROR -> "E"
            Log.ASSERT -> "A"
            else -> "?"
        }
    }
}
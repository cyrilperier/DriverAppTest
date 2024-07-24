package services.core.logger

import co.touchlab.kermit.Logger

fun Throwable.logError() {
    Logger.e { this.stackTraceToString() }
}

fun String.logError() {
    Logger.e { this }
}
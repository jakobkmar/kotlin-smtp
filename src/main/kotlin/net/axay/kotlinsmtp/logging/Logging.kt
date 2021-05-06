package net.axay.kotlinsmtp.logging

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

val logScope = CoroutineScope(Dispatchers.IO)

inline fun log(logLevel: LogLevel = LogLevel.Info, crossinline message: () -> String) {
    if (logLevel.enabled)
        logScope.launch {
            println("[${logLevel.prefix}] ${message()}")
        }
}

open class LogLevel(
    val prefix: String,
    var enabled: Boolean = false,
) {
    object Info : LogLevel("INFO", true)
}

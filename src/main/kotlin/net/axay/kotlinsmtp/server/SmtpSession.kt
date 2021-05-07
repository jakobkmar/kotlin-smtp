package net.axay.kotlinsmtp.server

import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import net.axay.kotlinsmtp.logging.LogLevel
import net.axay.kotlinsmtp.logging.log
import net.axay.kotlinsmtp.server.command.api.SmtpCommands

@Suppress("MemberVisibilityCanBePrivate")
class SmtpSession(
    val socket: Socket,
    val server: SmtpServer,
) {
    object SessionLog : LogLevel("SESSION", false)

    private val readChannel = socket.openReadChannel()
    private val writeChannel = socket.openWriteChannel(autoFlush = true)

    internal var shouldQuit = false

    val sessionData = SessionData()

    class SessionData {
        var helo: String? = null; internal set
    }

    var transactionHandler: SmtpTransactionHandler? = null
        get() {
            if (field == null && server.transactionHandlerCreator != null)
                field = server.transactionHandlerCreator.invoke()
            return field
        }

    suspend fun handle() {
        socket.use {
            sendResponse(220, "${server.hostname} ${server.servicename} Service ready")

            while (!shouldQuit) {
                val line = readLine()
                if (line != null) {
                    SmtpCommands.handle(line, this)
                } else break
            }
        }
    }

    internal suspend fun readLine(): String? {
        val line = readChannel.readUTF8Line()
        log(SessionLog) { "-> $line" }
        return line
    }

    private suspend fun respondLine(message: String) {
        writeChannel.writeStringUtf8(message + "\r\n")
        log(SessionLog) { "<- $message" }
    }

    suspend fun sendResponse(code: Int, message: String) {
        respondLine("$code $message")
    }

    suspend fun sendMultilineResponse(code: Int, lines: List<String>) {
        lines.forEachIndexed { index, line ->
            if (index != lines.lastIndex)
                respondLine("$code-$line")
            else
                respondLine("$code $line")
        }
    }

    suspend fun resetTransaction() {
        transactionHandler?.done()
        transactionHandler = null
    }
}

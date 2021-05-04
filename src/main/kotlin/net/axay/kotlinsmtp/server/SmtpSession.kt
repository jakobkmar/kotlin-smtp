package net.axay.kotlinsmtp.server

import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import net.axay.kotlinsmtp.server.command.api.SmtpCommands

@Suppress("MemberVisibilityCanBePrivate")
class SmtpSession(
    val socket: Socket,
    val server: SmtpServer,
) {
    private val readChannel = socket.openReadChannel()
    private val writeChannel = socket.openWriteChannel(autoFlush = true)

    private var shouldQuit = false

    /**
     * This objects holds the data that was currently collected during the session.
     */
    val sessionData = SessionData()

    class SessionData {
        var helo: String? = null; internal set
        var from: String? = null; internal set
    }

    suspend fun handle() {
        socket.use {
            sendResponse(220, "${server.hostname} ${server.servicename} Service ready")

            while (!shouldQuit) {
                val line = readChannel.readUTF8Line()
                if (line != null) {
                    SmtpCommands.handle(line, this)
                } else break
            }
        }
    }

    suspend fun sendResponse(code: Int, message: String) {
        writeChannel.writeStringUtf8("$code $message")
    }
}

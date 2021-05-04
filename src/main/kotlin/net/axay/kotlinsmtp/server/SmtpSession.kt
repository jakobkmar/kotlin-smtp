package net.axay.kotlinsmtp.server

import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import net.axay.kotlinsmtp.server.command.api.SmtpCommands

@Suppress("MemberVisibilityCanBePrivate")
class SmtpSession(
    val socket: Socket,
    val server: SmtpServer,
) {
    val readChannel = socket.openReadChannel()
    val writeChannel = socket.openWriteChannel(autoFlush = true)

    private var shouldQuit = false

    var helo: String? = null; internal set

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

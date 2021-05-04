package net.axay.kotlinsmtp.server

import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import net.axay.kotlinsmtp.server.command.api.SmtpCommands

class SmtpSession(
    val socket: Socket,
    val server: SmtpServer,
) {
    val readChannel = socket.openReadChannel()
    val writeChannel = socket.openWriteChannel(autoFlush = true)

    private var shouldQuit = false

    suspend fun handle() {
        sendResponse(220, "${server.hostname} ${server.servicename} Service ready")

        while (!shouldQuit) {
            val line = readChannel.readUTF8Line()
            if (line != null) {
                SmtpCommands.handle(line, this)
            } else return
        }
    }

    suspend fun sendResponse(code: Int, message: String) {
        writeChannel.writeStringUtf8("$code $message")
    }
}

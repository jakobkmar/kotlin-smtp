package net.axay.kotlinsmtp.server.command

import io.ktor.utils.io.*
import net.axay.kotlinsmtp.server.SmtpSession
import net.axay.kotlinsmtp.server.command.api.ParsedCommand
import net.axay.kotlinsmtp.server.command.api.SmtpCommand
import net.axay.kotlinsmtp.server.utils.SmtpStatusCode

class DataCommand : SmtpCommand(
    "DATA",
    "The text following this command is the message which should be sent.",
) {
    override suspend fun execute(command: ParsedCommand, session: SmtpSession) {
        if (command.parts.size != 1)
            respondSyntax()

        session.sendResponse(SmtpStatusCode.StartMailInput, "Start mail input - end data with <CRLF>.<CRLF>")

        val stringBuilder = StringBuilder()
        var terminated = false
        while (!terminated) {
            val line = session.readChannel.readUTF8Line()
            if (line == "." || line == null)
                terminated = true
            else stringBuilder.append(line)
        }

        session.sendResponse(250, "Ok")
    }
}

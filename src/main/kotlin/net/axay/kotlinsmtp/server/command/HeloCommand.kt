package net.axay.kotlinsmtp.server.command

import net.axay.kotlinsmtp.server.SmtpSession
import net.axay.kotlinsmtp.server.command.api.ParsedCommand
import net.axay.kotlinsmtp.server.command.api.SmtpCommand
import net.axay.kotlinsmtp.server.utils.SmtpStatusCode

class HeloCommand : SmtpCommand(
    "HELO",
    "<hostname>"
) {
    override suspend fun execute(command: ParsedCommand, session: SmtpSession) {
        if (command.parts.size < 2)
            session.respondSyntax()

        session.helo = command.parts[1]

        session.sendResponse(SmtpStatusCode.Okay, session.server.hostname)
    }
}

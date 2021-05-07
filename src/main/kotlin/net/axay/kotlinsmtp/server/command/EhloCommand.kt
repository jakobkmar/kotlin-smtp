package net.axay.kotlinsmtp.server.command

import net.axay.kotlinsmtp.server.internal.SmtpSession
import net.axay.kotlinsmtp.server.command.api.ParsedCommand
import net.axay.kotlinsmtp.server.command.api.SmtpCommand
import net.axay.kotlinsmtp.server.utils.SmtpStatusCode

class EhloCommand : SmtpCommand(
    "EHLO",
    "Extended HELO - The client introduces itself."
) {
    override suspend fun execute(command: ParsedCommand, session: SmtpSession) {
        session.sendMultilineResponse(
            SmtpStatusCode.Okay,
            listOf(
                session.server.hostname,
                "8BITMIME",
                // "VRFY",
                // "EXPN",
                "HELP"
            )
        )
    }
}

package net.axay.kotlinsmtp.server.command

import net.axay.kotlinsmtp.server.internal.SmtpSession
import net.axay.kotlinsmtp.server.command.api.ParsedCommand
import net.axay.kotlinsmtp.server.command.api.SmtpCommand
import net.axay.kotlinsmtp.server.utils.SmtpStatusCode

class RsetCommand : SmtpCommand(
    "RSET",
    "Resets the current session."
) {
    override suspend fun execute(command: ParsedCommand, session: SmtpSession) {
        if (command.parts.size != 1)
            respondSyntax()

        session.resetTransaction()

        session.sendResponse(SmtpStatusCode.Okay, "Ok")
    }
}

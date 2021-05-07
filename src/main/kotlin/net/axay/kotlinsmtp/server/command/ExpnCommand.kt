package net.axay.kotlinsmtp.server.command

import net.axay.kotlinsmtp.server.internal.SmtpSession
import net.axay.kotlinsmtp.server.command.api.ParsedCommand
import net.axay.kotlinsmtp.server.command.api.SmtpCommand
import net.axay.kotlinsmtp.server.utils.SmtpStatusCode

class ExpnCommand : SmtpCommand(
    "EXPN",
    "Checks if the given mailing list exists (and if so, this may return the membership)",
    "searchterm"
) {
    override suspend fun execute(command: ParsedCommand, session: SmtpSession) {
        val searchterm = command.rawWithoutCommand

        // TODO add implementation when user storage is added

        session.sendResponse(SmtpStatusCode.CommandNotImplemented, "The expand command is not supported")
    }
}

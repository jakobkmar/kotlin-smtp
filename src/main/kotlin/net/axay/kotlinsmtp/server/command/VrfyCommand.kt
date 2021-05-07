package net.axay.kotlinsmtp.server.command

import net.axay.kotlinsmtp.server.internal.SmtpSession
import net.axay.kotlinsmtp.server.command.api.ParsedCommand
import net.axay.kotlinsmtp.server.command.api.SmtpCommand
import net.axay.kotlinsmtp.server.utils.SmtpStatusCode

class VrfyCommand : SmtpCommand(
    "VRFY",
    "Checks if the given mailbox exists.",
    "searchterm"
) {
    override suspend fun execute(command: ParsedCommand, session: SmtpSession) {
        val searchTerm = command.rawWithoutCommand

        // TODO add implementation when user storage is added

        session.sendResponse(SmtpStatusCode.CommandNotImplemented, "The verify command is not supported")
    }
}

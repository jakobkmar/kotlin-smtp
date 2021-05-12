package net.axay.kotlinsmtp.server.command

import net.axay.kotlinsmtp.server.SmtpSession
import net.axay.kotlinsmtp.server.command.api.ParsedCommand
import net.axay.kotlinsmtp.server.command.api.SmtpCommand
import net.axay.kotlinsmtp.server.utils.SmtpStatusCode

class VrfyCommand : SmtpCommand(
    "VRFY",
    "Checks if the given mailbox exists.",
    "searchterm"
) {
    override suspend fun execute(command: ParsedCommand, session: SmtpSession) {
        val users = session.server.userHandler?.verify(command.rawWithoutCommand)

        if (users == null) {
            session.sendResponse(SmtpStatusCode.CommandNotImplemented, "The verify command is not supported")
        } else {
            if (users.isNotEmpty()) {
                if (users.size != 1) {
                    val response = ArrayList<String>()
                    response += " Ambiguous; Possibilities are"
                    session.sendMultilineResponse(
                        SmtpStatusCode.InvalidMailbox,
                        users.mapTo(response) { it.stringRepresentation }
                    )
                } else {
                    session.sendResponse(SmtpStatusCode.Okay, users.first().stringRepresentation)
                }
            } else {
                session.sendResponse(SmtpStatusCode.CannotVerifyUser, "The given mailbox could not be verified")
            }
        }
    }
}

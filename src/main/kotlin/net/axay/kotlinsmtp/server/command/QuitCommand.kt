package net.axay.kotlinsmtp.server.command

import net.axay.kotlinsmtp.server.SmtpSession
import net.axay.kotlinsmtp.server.command.api.ParsedCommand
import net.axay.kotlinsmtp.server.command.api.SmtpCommand
import net.axay.kotlinsmtp.server.utils.SmtpStatusCode

class QuitCommand : SmtpCommand(
    "QUIT",
    "Closes the current session."
) {
    override suspend fun execute(command: ParsedCommand, session: SmtpSession) {
        session.resetTransaction()
        session.sendResponse(SmtpStatusCode.ServiceClosingChannel, "Ok")
        session.shouldQuit = true
    }
}

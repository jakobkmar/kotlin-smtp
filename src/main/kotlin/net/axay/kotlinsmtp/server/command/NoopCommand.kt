package net.axay.kotlinsmtp.server.command

import net.axay.kotlinsmtp.server.internal.SmtpSession
import net.axay.kotlinsmtp.server.command.api.ParsedCommand
import net.axay.kotlinsmtp.server.command.api.SmtpCommand
import net.axay.kotlinsmtp.server.utils.SmtpStatusCode

class NoopCommand : SmtpCommand(
    "NOOP",
    "This command will lead to no operations being issued."
) {
    override suspend fun execute(command: ParsedCommand, session: SmtpSession) {
        session.sendResponse(SmtpStatusCode.Okay, "Ok")
    }
}

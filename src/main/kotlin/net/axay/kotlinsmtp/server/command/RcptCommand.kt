package net.axay.kotlinsmtp.server.command

import net.axay.kotlinsmtp.server.SmtpSession
import net.axay.kotlinsmtp.server.command.api.ParsedCommand
import net.axay.kotlinsmtp.server.command.api.SmtpCommand
import net.axay.kotlinsmtp.server.exception.SmtpSendResponse
import net.axay.kotlinsmtp.server.utils.AddressUtils
import net.axay.kotlinsmtp.server.utils.SmtpStatusCode

class RcptCommand : SmtpCommand(
    "RCPT",
    "TO:<(path:)address>"
) {
    override suspend fun execute(command: ParsedCommand, session: SmtpSession) {
        if (!command.rawCommand.startsWith("RCPT TO:", true))
            respondSyntax()

        val recipient = AddressUtils.extractFromBrackets(command.parts[1].split(':')[1]) ?: respondSyntax()

        if (!AddressUtils.validateAddress(recipient))
            throw SmtpSendResponse(SmtpStatusCode.InvalidMailboxSyntax, "Invalid email address")

        session.sendResponse(SmtpStatusCode.Okay, "Ok")
    }
}

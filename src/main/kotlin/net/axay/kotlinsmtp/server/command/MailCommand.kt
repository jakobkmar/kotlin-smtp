package net.axay.kotlinsmtp.server.command

import net.axay.kotlinsmtp.server.SmtpSession
import net.axay.kotlinsmtp.server.command.api.ParsedCommand
import net.axay.kotlinsmtp.server.command.api.SmtpCommand
import net.axay.kotlinsmtp.server.exception.SmtpSendResponse
import net.axay.kotlinsmtp.server.utils.AddressUtils
import net.axay.kotlinsmtp.server.utils.SmtpStatusCode

class MailCommand : SmtpCommand(
    "MAIL",
    "Starts a mail transaction and specifies the sender.",
    "FROM:<senderaddress>"
) {
    override suspend fun execute(command: ParsedCommand, session: SmtpSession) {
        if (!command.rawCommand.startsWith("MAIL FROM:", true))
            respondSyntax()

        val from = AddressUtils.extractFromBrackets(command.parts[1].split(':')[1]) ?: respondSyntax()

        if (!AddressUtils.validateAddress(from))
            throw SmtpSendResponse(SmtpStatusCode.InvalidMailbox, "Invalid email address")

        session.transactionHandler?.from(from)
        session.sendResponse(SmtpStatusCode.Okay, "Ok")
    }
}

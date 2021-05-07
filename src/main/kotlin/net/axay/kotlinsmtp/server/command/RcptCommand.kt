package net.axay.kotlinsmtp.server.command

import net.axay.kotlinsmtp.server.internal.SmtpSession
import net.axay.kotlinsmtp.server.command.api.ParsedCommand
import net.axay.kotlinsmtp.server.command.api.SmtpCommand
import net.axay.kotlinsmtp.server.exception.SmtpSendResponse
import net.axay.kotlinsmtp.server.utils.AddressUtils
import net.axay.kotlinsmtp.server.utils.SmtpStatusCode

class RcptCommand : SmtpCommand(
    "RCPT",
    "Specified a recipient who should receive the mail. This command can be called multiple times.",
    "TO:<(path:)address>"
) {
    override suspend fun execute(command: ParsedCommand, session: SmtpSession) {
        if (!command.rawCommand.startsWith("RCPT TO:", true))
            respondSyntax()

        val addressParts = AddressUtils.extractFromBrackets(command.parts[1].split(':')[1])
            ?.split(':') ?: respondSyntax()

        var forwardPath: List<String>? = null
        val recipient = when (addressParts.size) {
            1 -> addressParts[0]
            2 -> {
                forwardPath = addressParts[0].split(',')
                addressParts[1]
            }
            else -> throw SmtpSendResponse(SmtpStatusCode.InvalidMailboxSyntax, "Invalid recipient syntax")
        }

        if (forwardPath?.any { !AddressUtils.validateHost(it) } == true)
            throw SmtpSendResponse(SmtpStatusCode.InvalidMailboxSyntax, "Invalid forward path")

        if (!AddressUtils.validateAddress(recipient))
            throw SmtpSendResponse(SmtpStatusCode.InvalidMailboxSyntax, "Invalid email address")

        session.sendResponse(SmtpStatusCode.Okay, "Ok")
    }
}

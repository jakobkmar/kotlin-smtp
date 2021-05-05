package net.axay.kotlinsmtp.server.command.api

import net.axay.kotlinsmtp.server.SmtpSession
import net.axay.kotlinsmtp.server.command.HeloCommand
import net.axay.kotlinsmtp.server.command.MailCommand
import net.axay.kotlinsmtp.server.command.RcptCommand
import net.axay.kotlinsmtp.server.exception.SmtpSendResponse
import net.axay.kotlinsmtp.server.utils.SmtpStatusCode

enum class SmtpCommands(val instance: SmtpCommand) {
    HELO(HeloCommand()),
    MAIL(MailCommand()),
    RCPT(RcptCommand()),
    ;

    companion object {
        suspend fun handle(rawCommand: String, session: SmtpSession) {
            val parsedCommand = ParsedCommand(rawCommand)

            val smtpCommand = try {
                valueOf(parsedCommand.commandName)
            } catch (exc: IllegalArgumentException) { null }

            if (smtpCommand != null)
                try {
                    smtpCommand.instance.execute(parsedCommand, session)
                } catch (response: SmtpSendResponse) {
                    session.sendResponse(response.statusCode, response.message)
                }
            else
                session.sendResponse(SmtpStatusCode.CommandRejected, "Command unrecognized")
        }
    }
}

package net.axay.kotlinsmtp.server.command.api

import net.axay.kotlinsmtp.server.SmtpSession
import net.axay.kotlinsmtp.server.command.HeloCommand
import net.axay.kotlinsmtp.server.utils.SmtpStatusCode

enum class SmtpCommands(val instance: SmtpCommand) {
    HELO(HeloCommand()),
    ;

    companion object {
        suspend fun handle(rawCommand: String, session: SmtpSession) {
            val parsedCommand = ParsedCommand(rawCommand)

            val smtpCommand = try {
                valueOf(parsedCommand.commandName)
            } catch (exc: IllegalArgumentException) { null }

            if (smtpCommand != null)
                smtpCommand.instance.execute(parsedCommand, session)
            else
                session.sendResponse(SmtpStatusCode.CommandRejected, "Command unrecognized")
        }
    }
}

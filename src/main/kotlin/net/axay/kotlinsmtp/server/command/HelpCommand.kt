package net.axay.kotlinsmtp.server.command

import net.axay.kotlinsmtp.server.internal.SmtpSession
import net.axay.kotlinsmtp.server.command.api.ParsedCommand
import net.axay.kotlinsmtp.server.command.api.SmtpCommand
import net.axay.kotlinsmtp.server.command.api.SmtpCommands
import net.axay.kotlinsmtp.server.utils.SmtpStatusCode

class HelpCommand : SmtpCommand(
    "HELP",
    "Displays helpful information about the server in general, or about the given (optional) search term.",
    "(searchterm)"
) {
    override suspend fun execute(command: ParsedCommand, session: SmtpSession) {
        val searchterm = command.rawWithoutCommand

        if (searchterm.isNotEmpty()) {
            val searchedCommand = SmtpCommands.values().find { searchterm.uppercase().contains(it.name) }
            if (searchedCommand != null) {
                session.sendResponse(SmtpStatusCode.HelpMessage, searchedCommand.instance.description)
            } else {
                session.sendResponse(SmtpStatusCode.HelpMessage, "The given searchterm / command was not found.")
            }
        } else {
            session.sendResponse(SmtpStatusCode.HelpMessage, "Issue 'HELP searchterm' to get more information about a specific command.")
        }
    }
}

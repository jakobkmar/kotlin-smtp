package net.axay.kotlinsmtp.server.command.api

import net.axay.kotlinsmtp.server.SmtpSession
import net.axay.kotlinsmtp.server.utils.SmtpStatusCode

abstract class SmtpCommand(
    private val name: String,
    private val expectedSyntax: String,
) {
    abstract suspend fun execute(command: ParsedCommand, session: SmtpSession)

    protected suspend fun SmtpSession.respondSyntax(message: String = "Syntax error in parameters or arguments") =
        sendResponse(SmtpStatusCode.CommandSyntaxError, "$message - Expected syntax: $name $expectedSyntax")
}

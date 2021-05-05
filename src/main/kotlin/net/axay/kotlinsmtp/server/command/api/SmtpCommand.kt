package net.axay.kotlinsmtp.server.command.api

import net.axay.kotlinsmtp.server.SmtpSession
import net.axay.kotlinsmtp.server.exception.SmtpSendResponse
import net.axay.kotlinsmtp.server.utils.SmtpStatusCode

abstract class SmtpCommand(
    private val name: String,
    private val description: String,
    private val expectedSyntax: String? = null
) {
    abstract suspend fun execute(command: ParsedCommand, session: SmtpSession)

    protected fun respondSyntax(message: String = "Syntax error in parameters or arguments"): Nothing =
        throw SmtpSendResponse(SmtpStatusCode.CommandSyntaxError, "$message - Expected syntax: $name $expectedSyntax")
}

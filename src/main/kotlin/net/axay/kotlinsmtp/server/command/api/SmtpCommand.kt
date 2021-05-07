package net.axay.kotlinsmtp.server.command.api

import net.axay.kotlinsmtp.server.internal.SmtpSession
import net.axay.kotlinsmtp.server.exception.SmtpSendResponse
import net.axay.kotlinsmtp.server.utils.SmtpStatusCode

abstract class SmtpCommand(
    private val name: String,
    val description: String,
    private val expectedSyntax: String? = null
) {
    abstract suspend fun execute(command: ParsedCommand, session: SmtpSession)

    protected fun respondSyntax(message: String = "Syntax error in parameters or arguments"): Nothing {
        val syntaxResponse = if (expectedSyntax != null) "$name $expectedSyntax" else name
        throw SmtpSendResponse(SmtpStatusCode.CommandSyntaxError, "$message - Expected syntax: $syntaxResponse")
    }
}

package net.axay.kotlinsmtp.server.command.api

import net.axay.kotlinsmtp.server.SmtpSession

interface SmtpCommand {
    fun execute(command: String, session: SmtpSession)
}

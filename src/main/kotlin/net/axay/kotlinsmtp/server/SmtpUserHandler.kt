package net.axay.kotlinsmtp.server

import net.axay.kotlinsmtp.server.data.SmtpUser

abstract class SmtpUserHandler {
    abstract fun verify(searchterm: String): Collection<SmtpUser>
}

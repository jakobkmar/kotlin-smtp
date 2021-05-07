package net.axay.kotlinsmtp.server

@Suppress("MemberVisibilityCanBePrivate")
abstract class SmtpTransactionHandler {
    lateinit var sessionData: SmtpSession.SessionData
        private set

    internal fun init(sessionData: SmtpSession.SessionData) {
        this.sessionData = sessionData
    }

    open suspend fun from(sender: String) { }

    open suspend fun to(recipient: String) { }

    open suspend fun data(data: StringBuilder) { }

    open suspend fun done() { }
}

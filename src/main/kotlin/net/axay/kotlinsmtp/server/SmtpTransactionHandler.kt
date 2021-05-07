package net.axay.kotlinsmtp.server

abstract class SmtpTransactionHandler {
    lateinit var sessionData: SmtpSession.SessionData
        internal set

    open suspend fun from(sender: String) { }

    open suspend fun to(recipient: String) { }

    open suspend fun data(data: StringBuilder) { }

    open suspend fun done() { }
}

package net.axay.kotlinsmtp.server

abstract class SmtpTransactionHandler {
    open suspend fun from(sender: String) { }

    open suspend fun to(recipient: String) { }

    open suspend fun data(data: StringBuilder) { }

    open suspend fun done() { }
}

package net.axay.kotlinsmtp.server.utils

/**
 * Taken from https://tools.ietf.org/html/rfc5321#section-4.2.2
 */
@Suppress("unused")
object SmtpStatusCode {
    const val CommandRejected = 500
    const val CommandSyntaxError = 501
    const val CommandNotImplemented = 502
    const val BadCommandSequence = 503
    const val CommandParameterNotImplemented = 504

    const val SystemInfo = 211
    const val HelpMessage = 214
    const val ServiceReady = 220
    const val ServiceClosingChannel = 221
    const val ServiceNotAvailable = 421

    const val MailActionOkay = 250
    const val UserNotLocalWillForward = 251
    const val CannotVerifyUser = 252

    const val CannotAccommodateParameters = 455
    const val RecipientNotRecognized = 555

    const val MailboxTemporarilyUnavailable = 450
    const val MailboxUnavailable = 550

    const val ErrorInProcessing = 451

    const val UserNotLocalTryOther = 551

    const val InsufficientStorage = 452
    const val ExceededStorageAllocation = 552

    const val InvalidMailboxSyntax = 553

    const val StartMailInput = 354

    const val TransactionFailed = 554
}

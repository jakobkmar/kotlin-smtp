package net.axay.kotlinsmtp.server.exception

class SmtpException(override val message: String) : Exception()

class SmtpSendResponse(val statusCode: Int, override val message: String) : Throwable()

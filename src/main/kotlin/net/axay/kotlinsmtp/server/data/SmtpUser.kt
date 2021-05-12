package net.axay.kotlinsmtp.server.data

@Suppress("MemberVisibilityCanBePrivate")
class SmtpUser(
    val localpart: String,
    val domain: String,
    val username: String? = null,
) {
    val stringRepresentation by lazy {
        if (username != null)
            "$username <$localpart@$domain>"
        else
            "$localpart@$domain"
    }
}

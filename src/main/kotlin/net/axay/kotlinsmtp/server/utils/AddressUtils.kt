package net.axay.kotlinsmtp.server.utils

object AddressUtils {
    private val addressRegex = Regex("([A-Za-z.0-9]+)(@)([A-Za-z0-9]+\\.)+([A-Za-z]+)")

    fun removeBrackets(address: String) = address.removePrefix("<").removeSuffix(">")

    fun validateAddress(address: String) = addressRegex.matches(address)
}

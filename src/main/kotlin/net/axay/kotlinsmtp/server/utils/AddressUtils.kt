package net.axay.kotlinsmtp.server.utils

object AddressUtils {
    private val addressRegex = Regex("([A-Za-z.0-9]+)(@)([A-Za-z0-9]+\\.)+([A-Za-z]+)")
    private val hostRegex = Regex("(@)([A-Za-z0-9]+\\.)+([A-Za-z]+)")

    fun extractFromBrackets(string: String, openingBracket: String = "<", closingBracket: String = ">"): String? {
        val fromIndex = string.indexOf(openingBracket)
        val toIndex = string.indexOf(closingBracket)
        if (fromIndex < 0 || toIndex < 0)
            return null
        return string.slice(fromIndex + 1 until toIndex)
    }

    fun validateAddress(address: String) = addressRegex.matches(address)

    fun validateHost(host: String) = hostRegex.matches(host)
}

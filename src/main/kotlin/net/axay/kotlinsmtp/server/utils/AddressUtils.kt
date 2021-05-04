package net.axay.kotlinsmtp.server.utils

object AddressUtils {
    fun removeBrackets(address: String) = address.removePrefix("<").removeSuffix(">")
}

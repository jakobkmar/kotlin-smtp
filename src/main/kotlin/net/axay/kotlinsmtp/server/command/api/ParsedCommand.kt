package net.axay.kotlinsmtp.server.command.api

import net.axay.kotlinsmtp.Values

class ParsedCommand(private val rawCommand: String) {
    val commandName = rawCommand.takeWhile { it != ' ' }.toUpperCase()

    val parts by lazy { rawCommand.split(Values.whitespaceRegex) }
}

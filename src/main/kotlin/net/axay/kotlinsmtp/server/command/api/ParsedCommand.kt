package net.axay.kotlinsmtp.server.command.api

import net.axay.kotlinsmtp.Values

class ParsedCommand(val rawCommand: String) {
    val commandName = rawCommand.takeWhile { it != ' ' }.uppercase()

    val parts by lazy { rawCommand.split(Values.whitespaceRegex) }

    val rawWithoutCommand by lazy { rawCommand.removePrefix(rawCommand.takeWhile { it != ' ' } + ' ') }
}

package xkcdbot

import com.jessecorbett.diskord.api.rest.Embed
import com.jessecorbett.diskord.dsl.*
import xkcd.*

const val PREFIX = "ù"

suspend fun main() {
    val DISCORD_TKN = System.getenv("DISCORD_TKN")

    bot(DISCORD_TKN) {
        started{println("I'm ready !")}
        messageCreated {
            // Separate the message into prefix and arguments
            val words = it.content.split(" ")
            val prefix = words[0]
            // Use a by lazy because we need it for tp notation
            val args: List<String> by lazy {
                words.slice(1 until words.size)
            }

            // Check if the prefix is present
            if (prefix != PREFIX) {
                return@messageCreated
            }

            // Dispatch argument to function
            val requestComic: () -> Comic = when {
                // The user don't specify anything, so we give him a random comic
                args.isEmpty() -> {{getRandomComic()}}
                // The user ask for the latest comic
                args[0] == "latest" -> {{getLatestComic()}}
                // The user ask for a specific comic
                args[0].toIntOrNull() != null -> {{getComic(args[0].toInt())}}
                // This case mustn't append
                else -> {it.reply("Error parsing command"); return@messageCreated}
            }

            // Request comic
            lateinit var c: Comic
            try {
                c = requestComic()
            } catch (e: Exception) {
                it.reply("We can't get you the comic.\n${e.message}")
                return@messageCreated
            }

            // Convert the comic into a discord embedded message
            val e = comicToEmbedded(c)
            // Reply to the user
            it.reply("", e)
            return@messageCreated
        }
    }
}

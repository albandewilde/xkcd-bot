package xkcdbot

import com.jessecorbett.diskord.api.rest.Embed
import com.jessecorbett.diskord.dsl.*
import xkcd.fetchLatestComic

const val PREFIX = "ù"

suspend fun main() {
    val DISCORD_TKN = System.getenv("DISCORD_TKN")

    bot(DISCORD_TKN) {
        started{println("I'm ready !")}
        messageCreated {
            // Separate the message into prefix and arguments
            val words = it.content.split(" ")
            val prefix = words[0]
            val args = words.slice(1 until words.size)

            // Check if the prefix is present
            if (prefix != PREFIX) {
                return@messageCreated
            }

            // Assume when no arguments is given the user want the latest xkcd comic
            if (args.isEmpty() || args[0] == "latest") {
                // Fetch latest xkcd comic
                val c = fetchLatestComic()
                // Convert the comic into a discord embedded message
                val e = comicToEmbedded(c)
                // Reply to the user
                it.reply("", e)
                return@messageCreated
            }
        }
    }
}

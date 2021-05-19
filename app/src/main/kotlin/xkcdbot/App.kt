package xkcdbot

import com.jessecorbett.diskord.dsl.*
import index.*
import xkcd.Comic
import kotlin.random.Random

const val PREFIX = "ù"

var index = ComicsIndex()

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
                args.isEmpty() -> {{index.getComic(Random.nextInt(1, index.getIdx()))}}
                // The user ask for the latest comic
                args[0] == "latest" -> {{index.getComic(index.getIdx())}}
                // The user ast what are the bot's commands
                args[0] == "aled" -> {
                    it.reply(
                        "xkcd-bot available commands:\n" +
                        "    - `ù latest` → Show the latest xkcd comic\n" +
                        "    - `ù <id>` → Show the xkcd n°<id>\n" +
                        "    - `ù` → Show a random xkcd comic\n" +
                        "    - `ù aled` → Show help\n"
                    )
                    return@messageCreated
                }
                // The user ask for a specific comic
                args[0].toIntOrNull() != null -> {{index.getComic(args[0].toInt())}}
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
            val e = c.let(::comicToEmbedded) // Scope function because we need it for the tp
            // Reply to the user
            it.reply("", e)
            return@messageCreated
        }
    }
}
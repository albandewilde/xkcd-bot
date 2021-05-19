package xkcdbot

import com.jessecorbett.diskord.dsl.*

suspend fun main() {
    val DISCORD_TKN: String = System.getenv("DISCORD_TKN")

    bot(DISCORD_TKN) {
        commands("ù") {
            command("ping") {
                reply("¨pong")
            }
        }
    }
}

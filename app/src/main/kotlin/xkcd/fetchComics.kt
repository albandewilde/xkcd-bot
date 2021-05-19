package xkcd

import com.google.gson.Gson
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import kotlin.random.Random

private fun fetch(url: String): Pair<String, Int> {
    val c = HttpClient.newHttpClient()
    val r = HttpRequest.newBuilder(URI.create(url)).build()

    val resp = c.send(r, HttpResponse.BodyHandlers.ofString())
    return Pair(resp.body(), resp.statusCode())
}

private fun fetchComic(url: String): Comic {
    val (json, status) = fetch(url)
    if (status == 200) {
        return Gson().fromJson(json, Comic::class.java)
    } else {
        throw(
            when (status) {
                in 100..199 -> Exception("Informational response from xkcd.com")
                in 200..299 -> Exception("Success, but no comic returned from xkcd.com")
                in 300..399 -> Exception("Redirected by xkcd.com")
                in 400..499 -> Exception("Error from the bot")
                in 500..599 -> Exception("Error from xkcd.com")
                else -> Exception("Unknown error")
            }
        )
    }
}


fun getLatestComic(): Comic {
    return fetchComic("https://xkcd.com/info.0.json")
}

fun getComic(id: Int): Comic {
    return fetchComic("https://xkcd.com/$id/info.0.json")
}

fun getRandomComic(): Comic {
    // Get latest xkcd id
    val latestId = getLatestComic().num
    return getComic(Random.nextInt(1, latestId))
}
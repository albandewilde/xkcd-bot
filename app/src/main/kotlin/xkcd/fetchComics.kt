package xkcd

import com.google.gson.Gson
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

private val cantGetComic = Exception("Can't get comic")

private fun fetch(url: String): Pair<String, Int> {
    val c = HttpClient.newHttpClient()
    val r = HttpRequest.newBuilder(URI.create(url)).build()

    val resp = c.send(r, HttpResponse.BodyHandlers.ofString())
    return Pair(resp.body(), resp.statusCode())
}

private fun fetchComic(url: String): Pair<Comic?, Exception?> {
    val (json, status) = fetch(url)
    return if (status == 200) {
        Pair(
            Gson().fromJson(json, Comic::class.java),
            null,
        )
    } else {
        Pair(null, cantGetComic)
    }
}


fun getLatestComic(): Pair<Comic?, Exception?> {
    return fetchComic("https://xkcd.com/info.0.json")
}

fun getComic(id: Int): Pair<Comic?, Exception?> {
    return fetchComic("https://xkcd.com/$id/info.0.json")
}

fun getRandomComic(): Pair<Comic?, Exception?> {
    return Pair(null, cantGetComic)
}
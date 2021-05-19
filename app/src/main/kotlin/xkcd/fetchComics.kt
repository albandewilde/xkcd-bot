package xkcd

import com.google.gson.Gson
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

fun fetch(url: String): String {
    val c = HttpClient.newHttpClient()
    val r = HttpRequest.newBuilder(URI.create(url)).build()

    val resp = c.send(r, HttpResponse.BodyHandlers.ofString())
    return resp.body()
}

fun fetchLatestComic(): Comic {
    val json = fetch("https://xkcd.com/info.0.json")
    return Gson().fromJson(json, Comic::class.java)
}
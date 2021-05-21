package xkcdbot

import com.jessecorbett.diskord.api.rest.Embed
import com.jessecorbett.diskord.api.rest.EmbedFooter
import com.jessecorbett.diskord.api.rest.EmbedImage
import xkcd.Comic
import java.text.DecimalFormat

fun comicToEmbedded(c: Comic, fmtFunc: (Int) -> String): Embed {
    val m = c.month
    val d = c.day

    // Use smart cast because we need it for the tp
    val month = if (m is String && m.length < 2) fmtFunc(m.toInt()) else m
    val day = if (d is String && d.length < 2) fmtFunc(d.toInt()) else d

    return Embed(
        title = c.title,
        description = c.alt,
        image = EmbedImage(url = c.img),
        url = "https://xkcd.com/${c.num}/",
        footer = EmbedFooter(text = "#${c.num} (${c.year}/${month}/${day})")
    )
}

fun twoDigitNumber(): (Int) -> String {
    val pattern = "00"
    return {n -> DecimalFormat(pattern).format(n)}
}

// Use extension
fun Comic.toEmbedded() = this.let{comicToEmbedded(it, twoDigitNumber())} // Scope function and an HOF because we need it for the tp

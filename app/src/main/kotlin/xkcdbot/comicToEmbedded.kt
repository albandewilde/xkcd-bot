package xkcdbot

import com.jessecorbett.diskord.api.rest.Embed
import com.jessecorbett.diskord.api.rest.EmbedFooter
import com.jessecorbett.diskord.api.rest.EmbedImage
import xkcd.Comic
import java.text.DecimalFormat

fun comicToEmbedded(c: Comic, fmtFunc: (Int) -> String): Embed {
    val month = fmtFunc(c.month.toInt())
    val day = fmtFunc(c.day.toInt())

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

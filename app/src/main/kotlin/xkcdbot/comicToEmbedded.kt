package xkcdbot

import com.jessecorbett.diskord.api.rest.Embed
import com.jessecorbett.diskord.api.rest.EmbedFooter
import com.jessecorbett.diskord.api.rest.EmbedImage
import xkcd.Comic
import java.text.DecimalFormat

fun comicToEmbedded(c: Comic): Embed {
    val pattern = "00"
    val fmt = {n: String -> DecimalFormat(pattern).format(n.toInt())}
    val month = fmt(c.month)
    val day = fmt(c.day)

    return Embed(
        title = c.title,
        description = c.alt,
        image = EmbedImage(url = c.img),
        url = "https://xkcd.com/${c.num}/",
        footer = EmbedFooter(text = "#${c.num} (${c.year}/${month}/${day})")
    )
}
package xkcd

data class Comic (
    val num: Int,
    val img: String,
    val title: String,
    val safeTitle: String,
    val alt: String,
    val year: String,
    val month: String,
    val day: String,
    val transcript: String,
    val news: String,
    val link: String,
)
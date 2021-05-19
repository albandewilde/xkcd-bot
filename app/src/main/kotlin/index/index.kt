package index

import kotlinx.coroutines.*
import xkcd.Comic


class ComicsIndex {
    private val comics: HashMap<Int, Comic> = hashMapOf() // Comic indexed
    private var idx: Int // The latest comic index

    // Register the latest comic on the initialisation
    // And start the update and indexing coroutine
    init {
        val latest = xkcd.getLatestComic()
        comics[latest.num] = latest
        idx = latest.num

        CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
            indexing()
            update()
        }
    }

    // Method to get the last comic number
    fun getIdx() = idx

    // Method use to get a Comic
    fun getComic(id: Int): Comic {
        // Check if we already have the comic
        if (!comics.containsKey(id)) {
            comics[id] = xkcd.getComic(id)
        }
        return comics[id]!!
    }

    // Method to index all already existing comics
    suspend fun indexing() = coroutineScope {
        launch {
            // Existing comic indexed
            for (i in idx downTo 1) {
                // We already got the comic
                if (comics.containsKey(i)) {
                    continue
                }

                // Fetch the comic we don't have
                val c = xkcd.getComic(i)
                comics[c.num] = c

                // Sleep to don't overload the site
                Thread.sleep(1_000) // Sleep one second
            }
        }
    }

    // Method to get the latest comic
    suspend fun update() = coroutineScope {
        launch {
            while(true) {
                // Get the latest comic
                val c = xkcd.getLatestComic();

                // Check if it's a new one
                if (idx != c.num) {
                    comics[c.num] = c
                }

                // Sleep to don't overload the site
                Thread.sleep(1_000 * 60 * 10) // Sleep 10 minutes
            }
        }
    }
}
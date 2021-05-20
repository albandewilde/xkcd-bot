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
            // Add the comic to our index
            comics[id] = xkcd.getComic(id)
        }
        return comics[id]!!
    }

    // Method to index all already existing comics
    suspend fun indexing() = coroutineScope {
        launch {
            // Fetch already publiched comics
            fetchComicRange(1, idx-1)
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

                // Also fetch possible missing comics
                fetchComicRange(idx+1, c.num-1)

                // Update the new latest comis idx
                idx = c.num

                // Sleep to don't overload the site
                Thread.sleep(1_000 * 60 * 10) // Sleep 10 minutes
            }
        }
    }

    fun fetchComicRange(begin: Int, end: Int) {
        for (i in begin..end) {
            if (comics.containsKey(i)) {
                continue
            }

            getComic(i)

            // Sleep to don't overload the site
            Thread.sleep(1_000) // Sleep one second
        }
    }

}

package com.vanskarner.movie.persistence.local

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.vanskarner.movie.MovieDetailDS
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class RoomMovieLocalRepositoryTest {
    private lateinit var roomRepository: RoomMovieLocalRepository
    private lateinit var testRoomDB: TestRoomDB

    @Before
    fun setup() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        testRoomDB = Room.inMemoryDatabaseBuilder(appContext, TestRoomDB::class.java).build()

        roomRepository = RoomMovieLocalRepository(testRoomDB.movieDao())
    }

    @After
    fun tearDown() {
        testRoomDB.close()
    }

    @Test
    fun saveItem_savedItem() = runTest {
        val item = createItem(33)
        roomRepository.saveMovie(item).getOrThrow()
        val expectedItems = 1
        val actualItems = roomRepository.getNumberMovies().getOrThrow()

        assertEquals(expectedItems, actualItems)
    }

    @Test
    fun getMovie_withValidId_returnItem() = runTest {
        val expectedItem = createItem(32)
        roomRepository.saveMovie(expectedItem).getOrThrow()
        val actualItem = roomRepository.getMovie(expectedItem.id).getOrThrow()

        assertEquals(expectedItem.id, actualItem.id)
        assertEquals(expectedItem.title, actualItem.title)
        assertEquals(expectedItem.image, actualItem.image)
        assertEquals(expectedItem.backgroundImage, actualItem.backgroundImage)
        assertEquals(expectedItem.voteCount, actualItem.voteCount)
        assertEquals(expectedItem.voteAverage, actualItem.voteAverage, 0.01f)
        assertEquals(expectedItem.releaseDate, actualItem.releaseDate)
        assertEquals(expectedItem.overview, actualItem.overview)
    }

    @Test
    fun deleteMovie_withValidId_deletedItem() = runTest {
        val expectedNumberItems = 0
        val item = createItem(31)
        roomRepository.saveMovie(item).getOrThrow()
        roomRepository.deleteMovie(item.id).getOrThrow()
        val actualNumberItems = roomRepository.getNumberMovies().getOrThrow()

        assertEquals(expectedNumberItems, actualNumberItems)
    }

    @Test
    fun deleteAllMovies_withTwoItemsSaved_TwoItemsDeleted() = runTest {
        val item1 = createItem(30)
        val item2 = createItem(31)
        roomRepository.saveMovie(item1).getOrThrow()
        roomRepository.saveMovie(item2).getOrThrow()
        val actualNumberItems = roomRepository.deleteAllMovies().getOrThrow()
        val expectedNumberItems = 2

        assertEquals(expectedNumberItems, actualNumberItems)
    }

    @Test
    fun getNumberMovies_withTwoItemsSaved_returnTwoItems() = runTest {
        val item1 = createItem(30)
        val item2 = createItem(31)
        roomRepository.saveMovie(item1).getOrThrow()
        roomRepository.saveMovie(item2).getOrThrow()
        val actualNumberItems = roomRepository.getNumberMovies().getOrThrow()
        val expectedNumberItems = 2

        assertEquals(expectedNumberItems, actualNumberItems)
    }

    @Test
    fun checkMovie_withValidID_itemExists() = runTest {
        val item1 = createItem(30)
        roomRepository.saveMovie(item1).getOrThrow()
        val exists = roomRepository.checkMovie(item1.id).getOrThrow()

        assertTrue(exists)
    }

    @Test
    fun checkMovie_withInvalidID_itemNotExist() = runTest {
        val exists = roomRepository.checkMovie(33).getOrThrow()

        assertFalse(exists)
    }


    private fun createItem(movieId: Int) =
        MovieDetailDS(
            movieId,
            "Any Title",
            "https://anyurl.jpg",
            "https://anyurl.jpg",
            100,
            9.5f,
            "023-08-15",
            "any description"
        )

}
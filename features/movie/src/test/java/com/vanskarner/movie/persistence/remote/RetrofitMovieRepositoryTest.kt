package com.vanskarner.movie.persistence.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vanskarner.movie.MovieRemoteError
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.util.Objects
import java.util.concurrent.TimeUnit

class RetrofitMovieRepositoryTest {
    private lateinit var retrofitRepository: RetrofitMovieRepository
    private val baseImageUrl = "https://image.tmdb.org/t/p/w500"
    private val mockServer: MockWebServer = MockWebServer()

    @Before
    fun setup() {
        mockServer.start(1055)
        val deserializer = MovieDeserializer(baseImageUrl)
        val gsonConverter = GsonConverterFactory.create(
            GsonBuilder().registerTypeAdapter(
                MovieDTO::class.java,
                deserializer
            ).create()
        )
        val interceptor = MovieRemoteErrorInterceptor()
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(80, TimeUnit.MILLISECONDS)
            .readTimeout(80, TimeUnit.MILLISECONDS)
            .writeTimeout(80, TimeUnit.MILLISECONDS)
            .addInterceptor(interceptor)
            .build()
        val baseUrl = mockServer.url("").toString()
        val movieApiClient = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverter)
            .client(httpClient)
            .build().create(MovieApiClient::class.java)
        retrofitRepository =
            RetrofitMovieRepository("anyApiKey", movieApiClient)
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }

    @Test
    fun getMovies_whenHttpIsOK_returnList() = runTest {
        val fileName = "upcoming_list.json"
        val json = readFile(fileName)
        val mockResponse = MockResponse().setResponseCode(HttpURLConnection.HTTP_OK).setBody(json)
        mockServer.enqueue(mockResponse)
        val expectedList = Gson().fromJson(json, MoviesResultDTO::class.java).results
        val actualList = retrofitRepository.getMovies(1).getOrThrow()

        assertEquals(expectedList.size, actualList.size)
    }

    @Test
    fun getMovie_whenHttpIsOK_returnItem() = runTest {
        val fileName = "upcoming_item.json"
        val json = readFile(fileName)
        val mockResponse = MockResponse().setResponseCode(HttpURLConnection.HTTP_OK).setBody(json)
        mockServer.enqueue(mockResponse)
        val expectedItem = Gson().fromJson(json, MovieDTO::class.java)
        expectedItem.posterPath = baseImageUrl.plus(expectedItem.posterPath)
        expectedItem.backdropPath = baseImageUrl.plus(expectedItem.backdropPath)
        val actualItem = retrofitRepository.getMovie(1).getOrThrow()

        assertEquals(expectedItem.id, actualItem.id)
        assertEquals(expectedItem.title, actualItem.title)
        assertEquals(expectedItem.posterPath, actualItem.image)
        assertEquals(expectedItem.backdropPath, actualItem.backgroundImage)
        assertEquals(expectedItem.voteCount, actualItem.voteCount)
        assertEquals(expectedItem.voteAverage, actualItem.voteAverage, 0.01f)
    }

    @Test(expected = MovieRemoteError.NoInternet::class)
    fun getMovies_WhenNoResponse_throwNoInternet() = runTest {
        retrofitRepository.getMovies(1).getOrThrow()
    }

    @Test(expected = MovieRemoteError.Unauthorised::class)
    fun getMovies_whenHttpUnauthorized_throwUnauthorised() = runTest {
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_UNAUTHORIZED)
        mockServer.enqueue(mockResponse)
        retrofitRepository.getMovies(1).getOrThrow()
    }

    @Test(expected = MovieRemoteError.NotFound::class)
    fun getMovies_whenHttpNotFound_throwNotFound() = runTest {
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
        mockServer.enqueue(mockResponse)
        retrofitRepository.getMovies(1).getOrThrow()
    }

    @Test(expected = MovieRemoteError.ServiceUnavailable::class)
    fun getMovies_whenHttpUnavailable_throwServiceUnavailable() = runTest {
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_UNAVAILABLE)
        mockServer.enqueue(mockResponse)
        retrofitRepository.getMovies(1).getOrThrow()
    }

    @Test(expected = MovieRemoteError.Server::class)
    fun getMovies_whenHttpOtherErrors_throwServer() = runTest {
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_VERSION)
        mockServer.enqueue(mockResponse)
        retrofitRepository.getMovies(1).getOrThrow()
    }

    @Throws(IOException::class)
    private fun readFile(fileName: String): String {
        var receiveString: String? = ""
        val classLoader: ClassLoader = RetrofitMovieRepositoryTest::class.java.classLoader!!
        val inputStream = Objects.requireNonNull(classLoader).getResourceAsStream(fileName)
        val inputStreamReader = InputStreamReader(
            Objects.requireNonNull(inputStream)
        )
        val bufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder = StringBuilder()
        while (receiveString != null) {
            stringBuilder.append(receiveString)
            receiveString = bufferedReader.readLine()
        }
        inputStream.close()
        return stringBuilder.toString()
    }

}
package ir.maziz.batman.feature.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import ir.maziz.batman.data.BatmanResponse
import ir.maziz.batman.data.Search
import ir.maziz.batman.data.repo.BatmanMoviesRepository
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class MainViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mainViewModel: MainViewModel
    private lateinit var mockRepository: BatmanMoviesRepository

    @Before
    fun setUp() {
        // Set RxJava Schedulers for testing
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }

        mockRepository = mock(BatmanMoviesRepository::class.java)
        mainViewModel = MainViewModel(mockRepository)
    }

    @Test
    fun `testSearchMovies_SuccessfulResponse_updatesLiveDataCorrectly`() {
        // Define a sample search term
        val searchTerm = "Batman Begins"
        val apiKey = ir.maziz.batman.common.apiKey // Assuming apiKey is accessible or use anyString()

        // Create sample search results
        val movie1 = Search(imdbID = "tt0372784", title = "Batman Begins", year = "2005", type = "movie", poster = "poster_url1")
        val movie2 = Search(imdbID = "tt0468569", title = "The Dark Knight", year = "2008", type = "movie", poster = "poster_url2") // Example of another movie if needed
        val sampleMovies = listOf(movie1)
        val sampleResponse = BatmanResponse(Response = "True", Search = sampleMovies, totalResults = "1", Error = null)

        // Stub the repository method
        `when`(mockRepository.getMovies(eq(apiKey), eq(searchTerm)))
            .thenReturn(Single.just(sampleResponse))

        // Call the searchMovies method
        mainViewModel.searchMovies(searchTerm)

        // Assertions
        assertNotNull(mainViewModel.moviesLiveData.value)
        assertEquals(1, mainViewModel.moviesLiveData.value?.size)
        assertEquals("Batman Begins", mainViewModel.moviesLiveData.value?.get(0)?.Title)
        assertEquals(false, mainViewModel.progressBarLiveData.value) // Progress bar should be false after completion
    }

    @Test
    fun `testSearchMovies_ApiReturnsError_updatesLiveDataWithEmptyList`() {
        // Define a sample search term
        val searchTerm = "Unknown Movie"
        val apiKey = ir.maziz.batman.common.apiKey

        // Create a sample error response from API (Response="False")
        val errorResponse = BatmanResponse(Response = "False", Search = null, totalResults = null, Error = "Movie not found!")

        // Stub the repository method
        `when`(mockRepository.getMovies(eq(apiKey), eq(searchTerm)))
            .thenReturn(Single.just(errorResponse))

        // Call the searchMovies method
        mainViewModel.searchMovies(searchTerm)

        // Assertions
        assertNotNull(mainViewModel.moviesLiveData.value)
        assertTrue(mainViewModel.moviesLiveData.value?.isEmpty() == true)
        assertEquals(false, mainViewModel.progressBarLiveData.value)
    }

    @Test
    fun `testSearchMovies_RepositoryReturnsError_updatesLiveDataWithEmptyList`() {
        // Define a sample search term
        val searchTerm = "Error Case"
        val apiKey = ir.maziz.batman.common.apiKey

        // Stub the repository method to return an error
        `when`(mockRepository.getMovies(eq(apiKey), eq(searchTerm)))
            .thenReturn(Single.error(RuntimeException("Network error")))

        // Call the searchMovies method
        mainViewModel.searchMovies(searchTerm)

        // Assertions
        assertNotNull(mainViewModel.moviesLiveData.value)
        assertTrue(mainViewModel.moviesLiveData.value?.isEmpty() == true)
        assertEquals(false, mainViewModel.progressBarLiveData.value)
    }

    @Test
    fun `testInit_LoadsInitialMovies_SuccessfulResponse`() {
        // This test checks the init block's behavior, assuming "batman" is the default search
        val apiKey = ir.maziz.batman.common.apiKey
        val initialSearchTerm = INITIAL_SEARCH_TERM // "batman"

        val movie1 = Search(imdbID = "id1", title = "Batman Movie 1", year = "2000", type = "movie", poster = "p1")
        val sampleMovies = listOf(movie1)
        val sampleResponse = BatmanResponse(Response = "True", Search = sampleMovies, totalResults = "1", Error = null)

        // Stub the repository for the initial call in init
        // Note: This requires mockRepository to be initialized before MainViewModel, which it is in setUp.
        // However, the init block runs when MainViewModel is created. So, this specific stubbing
        // for init needs to happen *before* MainViewModel(mockRepository) if it were not for setUp.
        // Since MainViewModel is reset in setUp, we need a fresh mock for this specific scenario or ensure this runs first.

        // For simplicity, we'll assume the setUp's MainViewModel instance is what we're testing for init
        // and that its init block will use the mockRepository defined in setUp.
        // The key is that the `when` for the init call must match what init actually calls.

        // Re-initialize ViewModel here to ensure the mock is set up BEFORE init runs for *this specific test's conditions*
        // This is a bit tricky because init runs upon instantiation.
        // A cleaner way might be to have a separate test for init or use a test rule for ViewModel.
        // For now, let's ensure the mock is ready for the *next* instantiation if we were to create one.
        // The existing `mainViewModel` in `setUp` already made its `init` call.
        // To test `init` properly, we'd ideally control repository behavior *before* `MainViewModel` is constructed.

        // Let's reset and create a new ViewModel instance for this init test for clarity.
        mockRepository = mock(BatmanMoviesRepository::class.java) // Fresh mock
        `when`(mockRepository.getMovies(eq(apiKey), eq(initialSearchTerm)))
            .thenReturn(Single.just(sampleResponse))

        val testViewModelForInit = MainViewModel(mockRepository) // Init block is called here

        // Assertions
        assertNotNull(testViewModelForInit.moviesLiveData.value)
        assertEquals(1, testViewModelForInit.moviesLiveData.value?.size)
        assertEquals("Batman Movie 1", testViewModelForInit.moviesLiveData.value?.get(0)?.Title)
        assertEquals(false, testViewModelForInit.progressBarLiveData.value)
    }


    @After
    fun tearDown() {
        // Reset RxJava Schedulers after tests
        RxAndroidPlugins.reset()
        RxJavaPlugins.reset()
    }
}

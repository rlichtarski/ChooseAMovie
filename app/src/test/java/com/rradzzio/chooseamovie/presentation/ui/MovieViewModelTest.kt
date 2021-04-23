package com.rradzzio.chooseamovie.presentation.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import com.rradzzio.chooseamovie.FakeMovieDataTest.movie
import com.rradzzio.chooseamovie.FakeMovieDataTest.movies
import com.rradzzio.chooseamovie.MainCoroutineRule
import com.rradzzio.chooseamovie.domain.model.Movie
import com.rradzzio.chooseamovie.getOrAwaitValueTest
import com.rradzzio.chooseamovie.repositories.FakeMovieRepository
import com.rradzzio.chooseamovie.util.Constants
import com.rradzzio.chooseamovie.util.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: MovieViewModel

    @Mock
    private lateinit var observerState: Observer<Status>

    @Captor
    private lateinit var argumentCaptor: ArgumentCaptor<Status>

    @Before
    fun setup() {
        viewModel = MovieViewModel(FakeMovieRepository())
    }

    @Test
    fun `get movies from network with empty query returns error`() {
        viewModel.getMovies("")

        val value = viewModel.movies.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `get movies from network with too long query returns error`() {
        val string = buildString {
            for(i in 1..Constants.MAX_MOVIE_TITLE_LENGTH + 1) {
                append("a")
            }
        }
        viewModel.getMovies(string)

        val value = viewModel.movies.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `get movies before network request returns loading then success`() = runBlocking{
        val expectedLoadingState = Status.LOADING
        val expectedSuccessState = Status.SUCCESS

        viewModel.movies.observeForever {
            argumentCaptor.apply {
                verify(observerState).onChanged(argumentCaptor.capture())
                val (loadingState, successState) = allValues
                //expect first emitted value to be Resource's State (Status.Loading)
                assertThat(loadingState).isEqualTo(expectedLoadingState)
                //expect last emitted value to be Resource's State (Status.Success)
                assertThat(successState).isEqualTo(expectedSuccessState)
            }
        }
    }

    @Test
    fun `get movies from network with valid query returns success`() {
        viewModel.getMovies("query")

        val value = viewModel.movies.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `insert movie returns success`() {
        viewModel.insertMovie(movie)
        viewModel.returnAllMoviesFromDb()

        val value = viewModel.moviesFromDb.getOrAwaitValueTest()

        assertThat(value).contains(movie)
    }

    @Test
    fun `insert movie list returns success`() {
        viewModel.insertMovieList(movies)
        viewModel.returnAllMoviesFromDb()

        val value = viewModel.moviesFromDb.getOrAwaitValueTest()

        assertThat(value).containsExactlyElementsIn(movies)
    }

    @Test
    fun `delete movie returns success`() {
        viewModel.insertMovie(movie)
        viewModel.deleteMovie(movie)
        viewModel.returnAllMoviesFromDb()

        val value = viewModel.moviesFromDb.getOrAwaitValueTest()

        assertThat(value).doesNotContain(movie)
    }

    @Test
    fun `delete movie list returns success`() {
        viewModel.insertMovieList(movies)
        viewModel.deleteMovieList(movies)
        viewModel.returnAllMoviesFromDb()

        val value = viewModel.moviesFromDb.getOrAwaitValueTest()

        assertThat(value).doesNotContain(movies)
    }

}
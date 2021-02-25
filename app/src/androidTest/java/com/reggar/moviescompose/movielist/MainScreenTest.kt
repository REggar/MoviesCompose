package com.reggar.moviescompose.movielist

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.reggar.moviescompose.MainActivity
import com.reggar.moviescompose.common.ui.theme.MoviesComposeTheme
import com.reggar.moviescompose.movielist.presentation.MovieScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MainScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private var mockWebServer = MockWebServer()

    @Before
    fun setup() {
        mockWebServer.start(8080)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun onLoadMovieScreen_showLoadingSpinner() {
        composeTestRule.setContent {
            MoviesComposeTheme {
                MovieScreen()
            }
        }

        composeTestRule.onNodeWithTag("spinner").assertExists()

        queueSuccessResponse()
    }

    @Test
    fun onLoadMovieScreen_returnedSuccess_showContent() {
        queueSuccessResponse()

        composeTestRule.setContent {
            MoviesComposeTheme {
                MovieScreen()
            }
        }

        composeTestRule.onNodeWithText("Dunkirk").assertExists()
        composeTestRule.onNodeWithText("Jumanji: welcome to the jungle").assertExists()
        composeTestRule.onNodeWithText("The Maze Runner").assertExists()
    }

    @Test
    fun onLoadMovieScreen_returnedError_showError() {
        queueErrorResponse()

        composeTestRule.setContent {
            MoviesComposeTheme {
                MovieScreen()
            }
        }

        composeTestRule.onNodeWithText("An error occurred fetching films.")
            .assertExists()
    }

    @Test
    fun onLoadMovieScreenAndFilter_returnedSuccess_showFilteredContent() {
        queueSuccessResponse()

        composeTestRule.setContent {
            MoviesComposeTheme {
                MovieScreen()
            }
        }

        composeTestRule.onNodeWithTag("searchBar").performTextInput("Dunki")

        composeTestRule.onNodeWithText("Dunkirk").assertExists()
        composeTestRule.onNodeWithText("Jumanji: welcome to the jungle").assertDoesNotExist()
        composeTestRule.onNodeWithText("The Maze Runner").assertDoesNotExist()
    }

    private fun queueSuccessResponse() {
        mockWebServer.enqueue(
            MockResponse().setBody(
                """
            {
              "data": [
                {
                  "id": 912312,
                  "title": "Dunkirk",
                  "year": "2017",
                  "genre": "History",
                  "poster": "https://goo.gl/1zUyyq"
                },
                {
                  "id": 11241,
                  "title": "Jumanji: welcome to the jungle",
                  "year": "2017",
                  "genre": "Action",
                  "poster": "https://image.tmdb.org/t/p/w370_and_h556_bestv2/bXrZ5iHBEjH7WMidbUDQ0U2xbmr.jpg"
                },
                {
                  "id": 55122,
                  "title": "The Maze Runner",
                  "year": "2014",
                  "genre": "Action",
                  "poster": "https://image.tmdb.org/t/p/w370_and_h556_bestv2/coss7RgL0NH6g4fC2s5atvf3dFO.jpg"
                }
              ]
            }
                """.trimIndent()
            )
        )
    }

    private fun queueErrorResponse() {
        mockWebServer.enqueue(MockResponse().setResponseCode(404))
    }
}

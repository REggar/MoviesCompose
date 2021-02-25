package com.reggar.moviescompose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.fragment.app.FragmentActivity
import com.reggar.moviescompose.common.ui.theme.MoviesComposeTheme
import com.reggar.moviescompose.movielist.presentation.MovieScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MovieScreen()
                }
            }
        }
    }
}

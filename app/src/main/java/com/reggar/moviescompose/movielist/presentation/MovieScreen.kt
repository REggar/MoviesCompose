package com.reggar.moviescompose.movielist.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reggar.moviescompose.R
import com.reggar.moviescompose.movielist.data.model.Movie
import dev.chrisbanes.accompanist.glide.GlideImage

@Composable
fun MovieScreen(
    viewModel: MovieViewModel = viewModel()
) {
    val state by viewModel.state

    Scaffold(
        topBar = {
            MovieTopBar {
                viewModel.onEvent(MovieEvent.OnSearchChanged(it))
            }
        }
    ) {
        when (state) {
            is MovieState.Loading -> {
                MovieLoadingBody()
            }
            is MovieState.Error -> {
                MovieErrorBody()
            }
            is MovieState.Content -> {
                MovieContentBody((state as MovieState.Content).movies)
            }
        }
    }
}

@Preview
@Composable
fun MovieTopBar(
    modifier: Modifier = Modifier,
    onSearchTextChanged: (searchText: String) -> Unit = {},
) {
    var searchTerm by rememberSaveable { mutableStateOf("") }

    Surface(
        color = MaterialTheme.colors.primary,
        elevation = dimensionResource(R.dimen.gap_medium),
        modifier = modifier,
    ) {
        TextField(
            value = searchTerm,
            placeholder = { Text(stringResource(R.string.movie_list_search_hint)) },
            singleLine = true,
            modifier = Modifier.testTag("searchBar")
                .padding(dimensionResource(R.dimen.gap_medium))
                .fillMaxWidth(),
            onValueChange = {
                searchTerm = it
                onSearchTextChanged(it)
            }
        )
    }
}

@Composable
fun MovieLoadingBody(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.testTag("spinner")
        )
    }
}

@Composable
fun MovieErrorBody(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(stringResource(R.string.movie_list_error))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieContentBody(
    movies: List<Movie>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        cells = GridCells.Adaptive(
            dimensionResource(R.dimen.movie_grid_cell_min_size)
        ),
        contentPadding = PaddingValues(
            dimensionResource(R.dimen.gap_small)
        ),
        modifier = modifier
    ) {
        items(movies) { movie ->
            MovieCard(
                movie,
                modifier = Modifier.padding(dimensionResource(R.dimen.gap_small))
            )
        }
    }
}

@Composable
fun MovieCard(
    movie: Movie,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column {
            Box(
                contentAlignment = Alignment.TopEnd
            ) {
                MoviePoster(
                    posterUrl = movie.poster,
                    title = movie.title
                )
                MovieTag(
                    text = movie.genre,
                    modifier = Modifier.padding(dimensionResource(R.dimen.gap_small))
                )
            }
            Text(
                movie.title,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.gap_medium))
                    .padding(top = dimensionResource(R.dimen.gap_medium))
            )
            Text(
                movie.year.toString(),
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.gap_medium))
                    .padding(bottom = dimensionResource(R.dimen.gap_medium))
            )
        }
    }
}

@Composable
fun MovieTag(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colors.secondary)
            .padding(4.dp)
    ) {
        Text(
            text,
            style = MaterialTheme.typography.overline.copy(color = MaterialTheme.colors.onSecondary)
        )
    }
}

private const val MOVIE_POSTER_RATIO = 0.695f

@Composable
fun MoviePoster(
    posterUrl: String,
    title: String,
    modifier: Modifier = Modifier
) {
    val image = painterResource(R.drawable.ic_baseline_error_outline_24)
    GlideImage(
        data = posterUrl,
        contentDescription = title,
        contentScale = ContentScale.Crop,
        modifier = modifier.fillMaxWidth()
            .aspectRatio(MOVIE_POSTER_RATIO, false)
            .background(Color.LightGray),
        error = {
            Image(
                painter = image,
                contentDescription = stringResource(
                    R.string.movie_poster_content_description,
                    title
                ),
                modifier = Modifier.width(dimensionResource(R.dimen.movie_poster_error_icon_size))
                    .aspectRatio(1f)
            )
        }
    )
}
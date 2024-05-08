package com.qndev.moviesapp.presentation.screens.movie_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.qndev.moviesapp.presentation.navigation.Route
import com.qndev.moviesapp.presentation.theme.Gray80
import com.qndev.moviesapp.presentation.theme.LightSalmon
import com.qndev.moviesapp.util.MOVIE_LIST_TEST_TAG
import com.qndev.moviesapp.util.SEARCH_BAR_TEST_TAG


@Composable
fun MovieListScreen(
    navController: NavController,
    movieListState: MovieListState,
    onSearchTextChange: (String) -> Unit,
    onLoadMore: (String) -> Unit
) {

//    val movieListViewModel = hiltViewModel<MovieListViewModel>()
//    val movieListState = movieListViewModel.movieListState.collectAsState().value


    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.navigationBars)
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .background(color = Color.Black)
    ) {
        var searchText by rememberSaveable { mutableStateOf("") }

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .testTag(SEARCH_BAR_TEST_TAG),
            shape = RoundedCornerShape(40.dp),
            value = searchText,
            onValueChange = {
                searchText = it
//                movieListViewModel.resetMovieListOnQueryChange()
//                movieListViewModel.searchOrGetMovies(it)
                onSearchTextChange(it)
            },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "search") },
            placeholder = { Text("Search movie") },
            maxLines = 1,
            colors = TextFieldDefaults.colors(

                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = if (searchText.isBlank()) "Trending movies" else "Search results",
            color = LightSalmon,
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        MovieListContent(
            movieListState = movieListState,
            navController = navController,
            onLoadMore = {
//                movieListViewModel.searchOrGetMovies(searchText)
                onLoadMore(searchText)
            }
        )
    }
}

@Composable
fun MovieListContent(
    movieListState: MovieListState,
    navController: NavController,
    onLoadMore: () -> Unit
) {
    if (movieListState.errorMassage.isNotBlank() && movieListState.movieList.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = movieListState.errorMassage, color = Gray80)
        }

    } else if (movieListState.isLoading && movieListState.movieList.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .testTag(MOVIE_LIST_TEST_TAG)
        ) {

            items(movieListState.movieList.size) { position ->
                val movieItem = movieListState.movieList[position]
                MovieCard(
                    title = movieItem.title,
                    releaseDate = movieItem.releaseDate,
                    posterPath = movieItem.posterPath,
                    rating = movieItem.voteAverage,
                    onItemClick = {
                        navController.navigate(Route.DETAIL_SCREEN + "/${movieItem.id}")
                    }
                )
                if (position >= movieListState.movieList.size - 1) {
                    onLoadMore.invoke()
                }

            }
        }
    }
}

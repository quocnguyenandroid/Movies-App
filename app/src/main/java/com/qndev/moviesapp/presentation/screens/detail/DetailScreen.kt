package com.qndev.moviesapp.presentation.screens.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.qndev.moviesapp.presentation.theme.DarkGray
import com.qndev.moviesapp.presentation.theme.Gray80

@Composable
fun DetailScreen() {

    val detailViewModel = hiltViewModel<DetailViewModel>()
    val detailState = detailViewModel.detailState.collectAsState().value

    val backDropImageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(detailState.movieDetail?.backdropPath)
            .size(Size.ORIGINAL)
            .build()
    ).state

    val posterImageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(detailState.movieDetail?.posterPath)
            .size(Size.ORIGINAL)
            .build()
    ).state

    if (detailState.errorMessage.isNotBlank()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = detailState.errorMessage, color = Gray80)
        }
    } else if (detailState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .background(color = Color.Black)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .windowInsetsPadding(WindowInsets.navigationBars)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(color = Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                if (backDropImageState is AsyncImagePainter.State.Error) {
                    Icon(
                        modifier = Modifier.size(70.dp),
                        imageVector = Icons.Rounded.MoreHoriz,
                        contentDescription = "backdrop"
                    )
                }

                if (backDropImageState is AsyncImagePainter.State.Success) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = backDropImageState.painter,
                        contentDescription = "backdrop",
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(160.dp)
                        .height(240.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(color = Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    if (posterImageState is AsyncImagePainter.State.Error) {
                        Icon(
                            modifier = Modifier.size(70.dp),
                            imageVector = Icons.Rounded.ImageNotSupported,
                            contentDescription = "poster"
                        )
                    }

                    if (posterImageState is AsyncImagePainter.State.Success) {
                        Image(
                            painter = posterImageState.painter,
                            contentDescription = "poster",
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                detailState.movieDetail?.let { detail ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)

                    ) {
                        Text(
                            text = detail.title,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = DarkGray
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row {
                            Icon(
                                modifier = Modifier
                                    .size(18.dp)
                                    .align(Alignment.CenterVertically),
                                imageVector = Icons.Default.Star,
                                contentDescription = "rating",
                                tint = Color.Yellow
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = detail.voteAverage.toString().take(3),
                                color = Gray80
                            ) // Take 1 digit after the decimal point
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(text = "Language: " + detail.originalLanguage, color = Gray80)

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(text = "Release Date: " + detail.releaseDate, color = Gray80)

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(text = detail.voteCount.toString() + " votes", color = Gray80)
                    }
                }
            }
            detailState.movieDetail?.let {
                val context = LocalContext.current
                val homePage = it.homepage
                val imdbUrl = it.imdbUrl
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Overview",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Gray80,
                    )

                    Spacer(modifier = Modifier.height(8.dp))


                    Text(
                        text = it.overview,
                        fontSize = 16.sp,
                        color = Gray80,
                    )


                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "References",
                        fontSize = 19.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Gray80,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = homePage,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier
                            .clickable {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(homePage))
                                context.startActivity(intent)
                            },
                        color = Color.Blue
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = imdbUrl,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(imdbUrl))
                            context.startActivity(intent)
                        },
                        color = Color.Blue
                    )
                }
            }

        }
    }
}
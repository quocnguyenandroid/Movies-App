package com.qndev.moviesapp.presentation.screens.movie_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.qndev.moviesapp.presentation.theme.DarkGray
import com.qndev.moviesapp.presentation.theme.Gray80
import com.qndev.moviesapp.presentation.theme.Onyx

@Composable
fun MovieCard(
    title: String,
    releaseDate: String,
    rating: Double,
    posterPath: String,
    onItemClick: () -> Unit
) {

    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(posterPath)
            .size(Size.ORIGINAL)
            .build()
    ).state

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClick)
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(color = Onyx),
    )
    {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .height(150.dp)
                .width(100.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(color = Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            if (imageState is AsyncImagePainter.State.Error) {
                Icon(
                    modifier = Modifier.size(70.dp),
                    imageVector = Icons.Rounded.MoreHoriz,
                    contentDescription = title
                )
            }

            if (imageState is AsyncImagePainter.State.Success) {
                Image(
                    painter = imageState.painter,
                    contentDescription = title,
                    contentScale = ContentScale.Crop
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = DarkGray
            )
            Spacer(modifier = Modifier.height(4.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    modifier = Modifier
                        .size(16.dp)
                        .align(Alignment.CenterVertically),
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = "release",
                    tint = Color.Yellow
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = releaseDate.split("-")[0],
                    color = Gray80
                ) // Only show the year
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
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
                    text = rating.toString().take(3),
                    color = Gray80
                ) // Take 1 digit after the decimal point
            }
        }
    }
}
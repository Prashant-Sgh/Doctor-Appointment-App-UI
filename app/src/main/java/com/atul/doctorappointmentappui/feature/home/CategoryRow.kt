package com.atul.doctorappointmentappui.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.atul.doctorappointmentappui.R
import com.atul.doctorappointmentappui.core.model.CategoryModel

@Composable
fun CategoryItem(item: CategoryModel) {
    Column(
        Modifier
            .wrapContentSize()
            .padding(horizontal = 8.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            Modifier
                .clip(CircleShape)
                .size(70.dp)
                .background(color = colorResource(R.color.lightPuurple)),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = item.Picture,
                contentDescription = null,
                modifier = Modifier.size(30.dp),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(Modifier.height(8.dp))
        Text(
            text = item.Name,
            color = colorResource(R.color.darkPuurple)
        )
    }
}

@Composable
fun CategoryRow(items: List<CategoryModel>) {
    Box(Modifier.fillMaxWidth().height(100.dp)) {
        if (items.isEmpty()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
        else {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                items (items) {item->
                    CategoryItem(item)
                }
            }
        }
    }
}


@Preview
@Composable
private fun Preview() {
    CategoryItem(CategoryModel(0, "Category 1", "https://yt3.googleusercontent.com/ytc/AIdro_ldUJ4E23r9SokViYq6Y6Wj0bcSuv6ILJs8EFjw8gGDIA=s160-c-k-c0x00ffffff-no-rj"))
}
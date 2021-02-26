/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.Dog
import com.example.androiddevchallenge.data.DogData
import com.google.gson.Gson
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun DogListView(dogList: List<Dog>, navController: NavController?) {
    Column {
        TopAppBar(title = { Text(text = "Puppy Adoption") })

        LazyColumn {
            items(dogList) { dogList ->
                DogListItem(dogList, navController)
            }
        }
    }
}

@Composable
fun DogListItem(dog: Dog, navController: NavController?) {

    fun navigateToDogDetailView(dog: Dog) {
        val dogJson = Gson().toJson(dog)
        navController?.navigate("dogDetailsView/$dogJson")
    }

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(all = 16.dp)
            .fillMaxWidth()
            .height(height = 300.dp)
            .clickable {
                navigateToDogDetailView(dog)
            }
    ) {
        Column {
            val imageModifier = Modifier
                .height(250.dp)
                .fillMaxWidth()

            CoilImage(
                data = dog.image,
                contentDescription = dog.name,
                modifier = imageModifier,
                fadeIn = true,
                contentScale = ContentScale.FillWidth,
                error = {
                    Image(
                        painter = painterResource(R.drawable.ic_no_dog),
                        contentDescription = "Error Load Image"
                    )
                }
            )

            Text(
                text = dog.name,
                modifier = Modifier.padding(all = 8.dp),
                style = MaterialTheme.typography.h6
            )
        }
    }
}

@Preview
@Composable
fun ImageListPreview() {
    DogListView(DogData.list, null)
}

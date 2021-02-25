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
package com.example.androiddevchallenge

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.androiddevchallenge.data.Dog
import dev.chrisbanes.accompanist.coil.CoilImage

private val defaultSpacerSize = 16.dp

@Composable
fun DogDetailsView(
    dog: Dog,
    navController: NavHostController,
) {
    Column {
        TopAppBar(
            title = {
                Text(text = dog.name)
            },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.cd_navigate_up)
                    )
                }
            }
        )
        Box {
            DogContent(dog)
        }
    }
}

@Composable
fun DogContent(dog: Dog, modifier: Modifier = Modifier) {
    val textStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 1.sp
    )
    Column {
        DogHeaderImage(dog)
        LazyColumn(
            modifier = modifier.padding(horizontal = defaultSpacerSize)
        ) {
            item {
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = "Breed: ${dog.breed}",
                        style = textStyle,
                        lineHeight = 20.sp
                    )
                }
                Spacer(Modifier.height(defaultSpacerSize))
            }
            item {
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = "Description: \n${dog.description}",
                        style = textStyle,
                        lineHeight = 20.sp
                    )
                }
            }
            item {
                Spacer(Modifier.height(48.dp))
            }
        }
    }
}

@Composable
private fun DogHeaderImage(dog: Dog) {
    val imageModifier = Modifier
        .height(250.dp)
        .fillMaxWidth()

    CoilImage(
        data = dog.image,
        contentDescription = dog.name,
        fadeIn = true,
        modifier = imageModifier,
        contentScale = ContentScale.FillWidth,
        error = {
            Image(
                painter = painterResource(R.drawable.ic_no_dog),
                contentDescription = "Error Load Image"
            )
        }
    )
    Spacer(Modifier.height(defaultSpacerSize))
}

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

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.data.Dog
import com.example.androiddevchallenge.data.DogData
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                AppNavigator()
            }
        }
    }
}

@Composable
fun AppNavigator() {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = "dogListView",
        builder = {
            composable("dogListView") { DogListView(DogData.list, navController = navController) }
            composable(
                "dogDetailsView/{dog}",
                arguments = listOf(
                    navArgument("dog") {
                        // Prefer to pass NavType.ParcelableType but this is crash so we pass json string as work around
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                backStackEntry.arguments?.getString("dog")?.let { json ->
                    val dog = Gson().fromJson(json, Dog::class.java)
                    DogDetailsView(dog = dog, navController = navController)
                }
            }
        }
    )
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    MyTheme {
        Surface {
            content()
        }
    }
}

@Preview("MyScreen preview")
@Composable
fun DefaultPreview() {
    MyApp {
        AppNavigator()
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp {
            AppNavigator()
        }
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp {
            AppNavigator()
        }
    }
}

/*
 * Copyright (C) 2023 The Android Open Source Project
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

package ca.suthakaran.assignment3.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ca.suthakaran.assignment3.ui.home.MainScreen
import ca.suthakaran.assignment3.ui.home.MainViewModel
import ca.suthakaran.assignment3.ui.item.details.ProductDetailsScreen
import ca.suthakaran.assignment3.ui.item.details.ProductDetailsViewModel

import ca.suthakaran.assignment3.ui.item.edit.ItemEditScreen
import ca.suthakaran.assignment3.ui.item.edit.ProductEditViewModel
import ca.suthakaran.assignment3.ui.item.entry.ProductEntryScreen
import ca.suthakaran.assignment3.ui.item.entry.ProductEntryViewModel

/**
 * Provides Navigation graph for the application.
 */
@Composable
fun ShoppingNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = MainDestination.route,
        modifier = modifier
    ) {
        composable(route = MainDestination.route) {
            val viewModel: MainViewModel = hiltViewModel()
            MainScreen(
                navigateToProductEntry = { navController.navigate(ProductEntryDestination.route) },
                navigateToProductDetails = { id->
                    navController.navigate("${ProductDetailsDestination.route}/${id}")
                },
                viewModel = viewModel
            )
        }
        composable(route = ProductEntryDestination.route) {
            val viewModel: ProductEntryViewModel = hiltViewModel()
            ProductEntryScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
                viewModel = viewModel
            )
        }
        composable(
            route = ProductDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(ProductDetailsDestination.productIdArg) {
                type = NavType.IntType
            })
        ) {
            val viewModel: ProductDetailsViewModel = hiltViewModel()
            ProductDetailsScreen(
                navigateToEditProduct = { navController.navigate("${ProductEditDestination.route}/$it") },
                navigateBack = { navController.navigateUp() },
                viewModel = viewModel
            )
        }
        composable(
            route = ProductEditDestination.routeWithArgs,
            arguments = listOf(navArgument(ProductEditDestination.itemIdArg) {
                type = NavType.IntType
            })
        ) {
            val viewModel: ProductEditViewModel = hiltViewModel()
            ProductEditScreen(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() },
                viewModel = viewModel
            )
        }
    }
}

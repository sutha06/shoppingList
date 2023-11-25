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

package ca.suthakaran.assignment3.ui.item.edit

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ca.suthakaran.assignment3.ui.common.ShoppingTopAppBar
import ca.suthakaran.assignment3.ui.item.form.ProductFormBody
import ca.suthakaran.assignment3.ui.navigation.ProductEditDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductEditScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    viewModel: ProductEditViewModel,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            ShoppingTopAppBar(
                title = stringResource(ProductEditDestination.titleRes),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        ProductFormBody(
            productFormUiState = viewModel.uiState,
            onNameChange = viewModel::onNameChange,
            onPriceChange = viewModel::onPriceChange,
            onQuantityChange = viewModel::onQuantityChange,
            onSaveClick = {
                viewModel.updateItem()
                navigateBack()
            },
            onBrandNameChange = viewModel::onBrandNameChange,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ItemEditScreenPreview() {
//    InventoryTheme {
//        ItemEditScreen(navigateBack = { /*Do nothing*/ }, onNavigateUp = { /*Do nothing*/ })
//    }
//}

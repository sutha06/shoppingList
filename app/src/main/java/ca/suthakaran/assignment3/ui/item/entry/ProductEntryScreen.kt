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

package ca.suthakaran.assignment3.ui.item.entry

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ca.suthakaran.assignment3.ui.common.ShoppingTopAppBar
import ca.suthakaran.assignment3.ui.item.form.ProductFormBody
import ca.suthakaran.assignment3.ui.item.form.ProductFormUiState
import ca.suthakaran.assignment3.ui.model.ProductFormModel
import ca.suthakaran.assignment3.ui.navigation.ProductEntryDestination
import ca.suthakaran.assignment3.ui.theme.ShoppingTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductEntryScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: ProductEntryViewModel
) {
    Scaffold(
        topBar = {
            ShoppingTopAppBar(
                title = stringResource(ProductEntryDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        ProductFormBody(
            productFormUiState = viewModel.uiState,
            onNameChange = viewModel::onNameChange,
            onPriceChange = viewModel::onPriceChange,
            onQuantityChange = viewModel::onQuantityChange,
            onSaveClick = {
                viewModel.saveItem()
                navigateBack()
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemEntryScreenPreview() {
    ShoppingTheme {
        ProductFormBody(
            productFormUiState = ProductFormUiState(
                ProductFormModel(
                    name = "Item name", price = "10.00", quantity = "5"
                )
            ),
            onNameChange = {}, onPriceChange = {}, onQuantityChange = {},
            onSaveClick = {}
        )
    }
}

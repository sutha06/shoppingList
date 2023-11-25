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

package ca.suthakaran.assignment3.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.suthakaran.assignment3.R
import ca.suthakaran.assignment3.ui.common.ShoppingTopAppBar
import ca.suthakaran.assignment3.ui.model.ListProductModel
import ca.suthakaran.assignment3.domain.Product
import ca.suthakaran.assignment3.ui.model.toListProductModel
import ca.suthakaran.assignment3.ui.navigation.MainDestination
import ca.suthakaran.assignment3.ui.theme.ShoppingTheme

/**
 * Entry route for Home screen
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navigateToProductEntry: () -> Unit,
    navigateToProductDetails: (Int) -> Unit,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val mainUiState:MainUiState by viewModel.mainUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ShoppingTopAppBar(
                title = stringResource(MainDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToProductEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.item_entry_title)
                )
            }
        },
    ) { innerPadding ->
        MainBody(
            productList = mainUiState.productList,
            onItemClick = navigateToProductDetails,
            onToggleSelect = viewModel::toggleSelect,
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
private fun MainBody(
    productList: List<ListProductModel>,
    onItemClick: (Int) -> Unit,
    onToggleSelect: (ListProductModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        if (productList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_item_description),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            ShoppingList(
                productList = productList,
                onItemClick = onItemClick,
                onToggleSelect = onToggleSelect,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Composable
private fun ShoppingList(
    productList: List<ListProductModel>,
    onItemClick: (Int) -> Unit,
    onToggleSelect: (ListProductModel) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(items = productList, key = { it.id }) { item ->
            ShoppingProduct(
                item = item,
                onToggleSelect = onToggleSelect,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onItemClick(item.id) })
        }
    }
}

@Composable
private fun ShoppingProduct(
    item: ListProductModel,
    onToggleSelect: (ListProductModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier, elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Checkbox(checked = item.selected, onCheckedChange = { onToggleSelect(item)})
            Column(
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
            ){
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        text = item.price,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Text(
                    text = stringResource(R.string.in_stock, item.quantity),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun MainBodyPreview() {
    ShoppingTheme {
        MainBody(listOf(
            Product(1, "Game", 100.0, 20).toListProductModel(),
            Product(2, "Pen", 200.0, 30, true).toListProductModel(),
            Product(3, "TV", 300.0, 50).toListProductModel()
        ), onItemClick = {}, onToggleSelect = {})
    }
}

@Preview(showBackground = true)
@Composable
fun MainBodyEmptyListPreview() {
    ShoppingTheme {
        MainBody(listOf(), onItemClick = {}, onToggleSelect = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingProductPreview() {
    ShoppingTheme {
        ShoppingProduct(
            Product(1, "Game", 100.0, 20).toListProductModel(),
            onToggleSelect = {}
        )
    }
}

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

package ca.suthakaran.assignment3.ui.item.details

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import ca.suthakaran.assignment3.R
import ca.suthakaran.assignment3.data.local.LocalProduct
import ca.suthakaran.assignment3.domain.Product
import ca.suthakaran.assignment3.ui.common.ShoppingTopAppBar
import ca.suthakaran.assignment3.ui.model.ProductDetailsModel
import ca.suthakaran.assignment3.ui.navigation.ProductDetailsDestination
import ca.suthakaran.assignment3.ui.theme.ShoppingTheme
import ca.suthakaran.assignment3.data.local.Priority // Import the Priority enum


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsScreen(
    navigateToEditProduct: (Int) -> Unit,
    navigateBack: () -> Unit,
    viewModel: ProductDetailsViewModel,
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            ShoppingTopAppBar(
                title = stringResource(ProductDetailsDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToEditProduct(uiState.value.product.id) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))

            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.edit_item_title),
                )
            }
        }, modifier = modifier
    ) { innerPadding ->
        ProductDetailsBody(
            productDetailsUiState = uiState.value,
            onDelete = {
                viewModel.deleteItem()
                navigateBack()
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        )
    }
}

@Composable
private fun ProductDetailsBody(
    productDetailsUiState: ProductDetailsUiState,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        var showConfirmationDialog by rememberSaveable { mutableStateOf(false) }
        ProductDetails(
            product = productDetailsUiState.product, modifier = Modifier.fillMaxWidth()
        )
        OutlinedButton(
            onClick = { showConfirmationDialog = true },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.delete))
        }
        if (showConfirmationDialog) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    showConfirmationDialog = false
                    onDelete()
                },
                onDeleteCancel = { showConfirmationDialog = false },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}


@Composable
fun ProductDetails(
    product: ProductDetailsModel, modifier: Modifier = Modifier
) {
    var selectedPriority by rememberSaveable { mutableStateOf(Priority.HIGH) } // Default to HIGH priority
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
        ) {
            // ... (other product details)

            // Priority selection row
            PrioritySelectionRow(
                selectedPriority = selectedPriority,
                onPrioritySelected = { selectedPriority = it }
            )
        }
    }
}

@Composable
private fun PrioritySelectionRow(
    selectedPriority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "Priority:")
        Spacer(modifier = Modifier.weight(1f))

        // Radio buttons for priority selection
        PriorityRadioButton(
            priority = Priority.HIGH,
            selectedPriority = selectedPriority,
            onPrioritySelected = onPrioritySelected
        )
        PriorityRadioButton(
            priority = Priority.MEDIUM,
            selectedPriority = selectedPriority,
            onPrioritySelected = onPrioritySelected
        )
        PriorityRadioButton(
            priority = Priority.LOW,
            selectedPriority = selectedPriority,
            onPrioritySelected = onPrioritySelected
        )
    }
}

@Composable
private fun PriorityRadioButton(
    priority: Priority,
    selectedPriority: Priority,
    onPrioritySelected: (Priority) -> Unit
) {
    val isSelected = priority == selectedPriority

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.selectable(
            selected = isSelected,
            onClick = { if (!isSelected) onPrioritySelected(priority) }
        )
    ) {
        RadioButton(
            selected = isSelected,
            onClick = null, // Disable onClick on the RadioButton itself
            modifier = Modifier.size(24.dp),
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.primary
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = priority.name)
    }
}




@Composable
private fun ProductDetailsRow(
    @StringRes labelResID: Int, productDetail: String, modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        Text(text = stringResource(labelResID))
        Spacer(modifier = Modifier.weight(1f))
        Text(text = productDetail, fontWeight = FontWeight.Bold)



    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit, modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.attention)) },
        text = { Text(stringResource(R.string.delete_question)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = stringResource(R.string.yes))
            }
        })
}

@SuppressLint("ResourceType")
@Preview(showBackground = true)
@Composable
fun ProductDetailsScreenPreview() {
    ShoppingTheme {
        ProductDetailsBody(
            ProductDetailsUiState(Product(
                    1,
                    "Pen",
                    6.25,
                    0,
                selected = true,
                brandName = "Bic",
                )
        ), onDelete = {})
    }
}

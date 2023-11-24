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

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.suthakaran.assignment3.data.repository.ProductRepository
import ca.suthakaran.assignment3.ui.navigation.ProductDetailsDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel to retrieve, update and delete an item from the [ProductRepository]'s data source.
 */
@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val productRepository: ProductRepository,
) : ViewModel() {

    private val productId: Int = checkNotNull(savedStateHandle[ProductDetailsDestination.productIdArg])

    /**
     * Holds the item details ui state. The data is retrieved from [ProductRepository] and mapped to
     * the UI state.
     */
    val uiState: StateFlow<ProductDetailsUiState> =
        productRepository.getProductByIdStream(productId)
            .filterNotNull()
            .map { product ->
                ProductDetailsUiState(product)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ProductDetailsUiState()
            )

    /**
     * Reduces the item quantity by one and update the [ProductRepository]'s data source.
     */
    fun reduceQuantityByOne() {
        viewModelScope.launch {
            val currentItem = uiState.value.product
            if (currentItem.quantity > 0) {
                productRepository.updateProductQuantityById(currentItem.id, currentItem.quantity - 1)
            }
        }
    }

    /**
     * Deletes the item from the [ProductRepository]'s data source.
     */
    fun deleteItem() = viewModelScope.launch{
        productRepository.deleteProductById(uiState.value.product.id)
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

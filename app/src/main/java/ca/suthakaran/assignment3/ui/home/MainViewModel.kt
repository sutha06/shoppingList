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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.suthakaran.assignment3.data.repository.ProductsRepository
import ca.suthakaran.assignment3.ui.model.ListProductModel
import ca.suthakaran.assignment3.ui.model.toListProductModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel to retrieve all items in the Room database.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
) : ViewModel() {

    /**
     * Holds home ui state. The list of items are retrieved from [ProductsRepository] and mapped to
     * [MainUiState]
     */
    val mainUiState: StateFlow<MainUiState> =
        productsRepository.getAllProductsStream()
            .map { list -> MainUiState(list.map { product -> product.toListProductModel() }) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = MainUiState()
            )

    fun toggleSelect(product: ListProductModel) {
        viewModelScope.launch {
                productsRepository.updateProductSelectedById(product.id, !product.selected)
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}


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

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import ca.suthakaran.assignment3.data.repository.ProductsRepository
import ca.suthakaran.assignment3.ui.item.form.FormViewModel
import ca.suthakaran.assignment3.ui.item.form.toProductFormUiState
import ca.suthakaran.assignment3.ui.model.ProductFormModel
import ca.suthakaran.assignment3.ui.navigation.ProductEditDestination

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel to retrieve and update an item from the [ItemsRepository]'s data source.
 */
@HiltViewModel
class ProductEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val itemsRepository: ProductsRepository
) : FormViewModel() {

    private val itemId: Int = checkNotNull(savedStateHandle[ProductEditDestination.itemIdArg])

    init {
        viewModelScope.launch {
            uiState = itemsRepository.getProductByIdStream(itemId)
                .filterNotNull()
                .first()
                .toProductFormUiState(isEntryValid = true)
        }
    }

    /**
     * Update the item in the [ProductsRepository]'s data source
     */
    fun updateItem() = viewModelScope.launch {
        val formData: ProductFormModel = uiState.productFormModel
        if (formData.isValid()) {
            itemsRepository.updateProduct(formData.toProduct())
        }
    }
}

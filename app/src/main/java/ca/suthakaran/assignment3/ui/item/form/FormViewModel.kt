package ca.suthakaran.assignment3.ui.item.form

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ca.suthakaran.assignment3.ui.model.ProductFormModel

abstract class FormViewModel(
): ViewModel() {

    var uiState: ProductFormUiState by mutableStateOf(ProductFormUiState())
        protected set


    fun onNameChange(newName: String) =
        updateUiState(uiState.productFormModel.copy(name = newName))

    fun onPriceChange(newPrice: String) =
        updateUiState(uiState.productFormModel.copy(price = newPrice))

    fun onQuantityChange(newQuantity: String) =
        updateUiState(uiState.productFormModel.copy(quantity = newQuantity))

    private fun updateUiState(itemFormModel: ProductFormModel) {
        uiState =
            ProductFormUiState(
                productFormModel = itemFormModel,
                isEntryValid = itemFormModel.isValid()
            )
    }
}
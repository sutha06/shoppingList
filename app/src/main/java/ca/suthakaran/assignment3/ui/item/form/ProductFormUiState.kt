package ca.suthakaran.assignment3.ui.item.form

import ca.suthakaran.assignment3.domain.Product
import ca.suthakaran.assignment3.ui.model.ProductFormModel
import ca.suthakaran.assignment3.ui.model.toProductFormData

/**
 * Represents Ui State for an Item.
 */
data class ProductFormUiState(
    val productFormModel: ProductFormModel = ProductFormModel(),
    val isEntryValid: Boolean = false
)

fun Product.toProductFormUiState(isEntryValid: Boolean = false): ProductFormUiState =
    ProductFormUiState(
        productFormModel = this.toProductFormData(),
        isEntryValid = isEntryValid
    )
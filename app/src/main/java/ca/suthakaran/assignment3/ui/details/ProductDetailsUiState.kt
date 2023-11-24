package ca.suthakaran.assignment3.ui.details

import ca.suthakaran.assignment3.domain.Item
import ca.suthakaran.assignment3.ui.model.ProductDetailsModel
import ca.suthakaran.assignment3.ui.model.toItemDetailsModel


/**
 * UI state for ItemDetailsScreen
 */
data class ProductDetailsUiState(
    val outOfStock: Boolean,
    val item: ProductDetailsModel
){
    constructor(item: Item): this (
        outOfStock = item.quantity <= 0,
        item = item.toItemDetailsModel()
    )

    constructor(): this(Item())
}
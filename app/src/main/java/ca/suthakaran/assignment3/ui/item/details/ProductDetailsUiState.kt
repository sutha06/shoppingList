package ca.suthakaran.assignment3.ui.item.details

import ca.suthakaran.assignment3.domain.Product
import ca.suthakaran.assignment3.ui.model.ProductDetailsModel
import ca.suthakaran.assignment3.ui.model.toItemDetailsModel


/**
 * UI state for ItemDetailsScreen
 */
data class ProductDetailsUiState(
    val outOfStock: Boolean,
    val item: ProductDetailsModel
){
    constructor(product: Product): this (
        outOfStock = product.quantity <= 0,
        item = product.toItemDetailsModel()
    )

    constructor(): this(Product())
}
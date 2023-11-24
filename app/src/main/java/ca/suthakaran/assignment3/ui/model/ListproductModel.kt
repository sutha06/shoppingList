package ca.suthakaran.assignment3.ui.model

import ca.suthakaran.assignment3.domain.Product
import ca.suthakaran.assignment3.ui.common.formatCurrency

data class ListproductModel(
    val id: Int,
    val name: String,
    val price: String,
    val quantity: Int,
    val selected: Boolean
){
    constructor(product: Product): this(
        id = product.id,
        name = product.name,
        price = formatCurrency(product.price),
        quantity = product.quantity,
        selected = product.selected
    )

    constructor(): this(Product())
}

fun Product.toListProductModel() = ListproductModel(this)



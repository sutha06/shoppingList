package ca.suthakaran.assignment3.ui.model

import ca.suthakaran.assignment3.domain.Product
import ca.suthakaran.assignment3.ui.common.formatCurrency

data class ListProductModel(
    val id: Int,
    val name: String,
    val price: String,
    val quantity: Int,
    val selected: Boolean,
    val brandName: String
){


    constructor(product: Product): this(
        id = product.id,
        name = product.name,
        price = formatCurrency(product.price),
        quantity = product.quantity,
        selected = product.selected,
        brandName = product.brandName
    )

    constructor(): this(Product())
}

fun Product.toListProductModel() = ListProductModel(this)



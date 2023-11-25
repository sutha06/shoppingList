package ca.suthakaran.assignment3.ui.model

import ca.suthakaran.assignment3.domain.Product
import ca.suthakaran.assignment3.ui.common.formatCurrency

data class ProductDetailsModel(
    val id: Int,
    val name: String,
    val price: String,
    val quantity: Int,
    val brandName: String
){


    constructor(product: Product): this(
        id = product.id,
        name = product.name,
        price = formatCurrency(product.price),
        quantity = product.quantity,
        brandName = product.brandName
    )

    constructor(): this(Product())
}

fun Product.toItemDetailsModel() = ProductDetailsModel(this)



package ca.suthakaran.assignment3.ui.model

import ca.suthakaran.assignment3.domain.Item
import ca.suthakaran.assignment3.ui.common.currencyformat

data class ProductDetailsModel(
    val id: Int,
    val name: String,
    val price: String,
    val quantity: Int
){
    constructor(item: Item): this(
        id = item.id,
        name = item.name,
        price = currencyformat(item.price),
        quantity = item.quantity
    )

    constructor(): this(Item())
}

fun Item.toItemDetailsModel() = ProductDetailsModel(this)



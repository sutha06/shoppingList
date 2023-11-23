package ca.suthakaran.assignment3.ui.model
import ca.suthakaran.assignment3.domain.Item
data class ItemFormModel(
    val id: Int = 0,
    val name: String = "",
    val price: String = "",
    val quantity: String = "",
) {
    fun isValid(): Boolean =
        name.isNotBlank() && price.isNotBlank() && quantity.isNotBlank()

    fun toItem(): Item = Item(
        id = id,
        name = name,
        price = price.toDoubleOrNull() ?: 0.0,
        quantity = quantity.toIntOrNull() ?: 0
    )
}

fun Item.toItemFormData(): ItemFormModel = ItemFormModel(
    id = id,
    name = name,
    price = price.toString(),
    quantity = quantity.toString()
)

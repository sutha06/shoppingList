

package ca.suthakaran.assignment3.domain

//Entity data class represents a single row in the database.

data class Product(
    val id: Int = 0,
    val name: String = "No Name",
    val price: Double = 0.0,
    val quantity: Int = 0,
    val selected: Boolean = false,
    val brandName: String = "No Brand"
)

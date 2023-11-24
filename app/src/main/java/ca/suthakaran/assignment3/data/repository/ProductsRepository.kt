

package ca.suthakaran.assignment3.data.repository

import ca.suthakaran.assignment3.domain.Product
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    fun getAllProductsStream(): Flow<List<Product>>
    fun getProductByIdStream(id: Int): Flow<Product?>
    suspend fun insertProduct(product: Product)
    suspend fun deleteProduct(product: Product)
    suspend fun deleteProductById(id: Int)
    suspend fun updateProduct(product: Product)
    suspend fun updateProductQuantityById(id: Int, quantity: Int)
    suspend fun updateProductSelectedById(id: Int, selected: Boolean)
}

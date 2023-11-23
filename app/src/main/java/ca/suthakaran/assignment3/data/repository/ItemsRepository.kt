

package ca.suthakaran.assignment3.data.repository

import ca.suthakaran.assignment3.domain.Item
import kotlinx.coroutines.flow.Flow

interface ItemsRepository {
    fun getAllItemsStream(): Flow<List<Item>>
    fun getItemByIdStream(id: Int): Flow<Item?>
    suspend fun insertItem(item: Item)
    suspend fun deleteItem(item: Item)
    suspend fun deleteItemById(id: Int)
    suspend fun updateItem(item: Item)
    suspend fun updateItemQuantityById(id: Int, quantity: Int)
    suspend fun updateItemSelectedById(id: Int, selected: Boolean)
}

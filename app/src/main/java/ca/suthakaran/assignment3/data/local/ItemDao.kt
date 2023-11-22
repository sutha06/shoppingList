/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ca.suthakaran.assignment3.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ca.suthakaran.assignment3.data.local.LocalItem
import kotlinx.coroutines.flow.Flow

/**
 * Database access object to access the Inventory database
 */
@Dao
interface ItemDao {

    @Query("SELECT * from items ORDER BY name ASC")
    fun getAllItemsStream(): Flow<List<LocalItem>>

    @Query("SELECT * from items WHERE id = :id")
    fun getItemByIdStream(id: Int): Flow<LocalItem?>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(item: LocalItem)

    @Update
    suspend fun updateItem(item: LocalItem)

    @Query("UPDATE items SET quantity = :quantity WHERE id = :id")
    suspend fun updateItemQuantityById(id: Int, quantity: Int)

    @Query("UPDATE items SET selected = :selected WHERE id = :id")
    suspend fun updateItemSelectedById(id: Int, selected: Boolean)

    @Delete
    suspend fun deleteItem(item: LocalItem)

    @Query("DELETE FROM items WHERE id = :id")
    suspend fun deleteItemById(id: Int)
}

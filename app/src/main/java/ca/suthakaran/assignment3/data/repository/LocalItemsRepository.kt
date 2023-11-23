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

package ca.suthakaran.assignment3.data.repository

import ca.suthakaran.assignment3.data.local.ItemDao
import ca.suthakaran.assignment3.data.local.LocalItem
import ca.suthakaran.assignment3.domain.Item
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(DelicateCoroutinesApi::class)
class LocalItemsRepository(
    private val itemDao: ItemDao,
    private val externalScope: CoroutineScope,
    private val dispatcher: CoroutineDispatcher
) : ItemsRepository {

    @Inject
    constructor(itemDao: ItemDao) : this(itemDao, GlobalScope, Dispatchers.IO)

    override fun getAllItemsStream(): Flow<List<Item>> =
        itemDao.getAllItemsStream()
            .map{ list -> list.map { localItem ->  localItem.toItem() }}
            .flowOn(dispatcher)

    override fun getItemByIdStream(id: Int): Flow<Item?> =
        itemDao.getItemByIdStream(id)
            .map { localItem -> localItem?.toItem() }
            .flowOn(dispatcher)

    override suspend fun insertItem(item: Item) {
        externalScope.launch(dispatcher) { itemDao.insertItem(item.toLocalItem()) }.join()
    }

    override suspend fun deleteItem(item: Item) {
        externalScope.launch(dispatcher) { itemDao.deleteItem(item.toLocalItem()) }.join()
    }

    override suspend fun deleteItemById(id: Int){
        externalScope.launch(dispatcher) { itemDao.deleteItemById(id) }.join()
    }

    override suspend fun updateItem(item: Item) {
        externalScope.launch(dispatcher) { itemDao.updateItem(item.toLocalItem()) }.join()
    }

    override suspend fun updateItemQuantityById(id: Int, quantity: Int) {
        externalScope.launch(dispatcher) { itemDao.updateItemQuantityById(id, quantity) }.join()
    }

    override suspend fun updateItemSelectedById(id: Int, selected: Boolean) {
        externalScope.launch(dispatcher) { itemDao.updateItemSelectedById(id, selected) }.join()
    }
}

fun LocalItem.toItem(): Item = Item(
    id = this.id,
    name = this.name,
    price = this.price,
    quantity = this.quantity,
    selected = this.selected
)

fun Item.toLocalItem(): LocalItem = LocalItem(
    id = this.id,
    name = this.name,
    price = this.price,
    quantity = this.quantity,
    selected = this.selected
)

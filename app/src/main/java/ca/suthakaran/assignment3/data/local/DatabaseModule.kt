package ca.suthakaran.assignment3.data.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun shoppingDatabase(
        @ApplicationContext context: Context
    ): ShoppingListDatabase = Room.databaseBuilder(context, ShoppingListDatabase::class.java, "product_database")
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun productDao(database: ShoppingListDatabase): ProductDao = database.productDao()
}
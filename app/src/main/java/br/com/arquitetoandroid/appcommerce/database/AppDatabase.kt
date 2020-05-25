package br.com.arquitetoandroid.appcommerce.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.arquitetoandroid.appcommerce.model.*

@Database(entities = [Order::class,
        OrderedProduct::class,
        Product::class,
        ProductCategory::class,
        ProductColor::class,
        ProductImage::class,
        ProductSize::class,
        User::class,
        UserAddress::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao() : UserDao

    abstract fun productDao() : ProductDao

    abstract fun orderDao() : OrderDao

    abstract fun productCategoryDao() : ProductCategoryDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Application) : AppDatabase {
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "appcommerce_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .createFromAsset("appcommerce_database.db")
                    .build()
                INSTANCE = instance

                instance
            }
        }

    }
}
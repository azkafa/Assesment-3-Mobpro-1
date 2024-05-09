package org.d3if0020.assesment2mobpro.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.d3if0020.assesment2mobpro.data.Address

@Database(entities = [Address::class], version = 1, exportSchema = false)
abstract class AddressDb : RoomDatabase() {

    abstract val dao: AddressDao

    companion object {

        @Volatile
        private var INSTANCE: AddressDb? = null

        fun getInstance(context: Context): AddressDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AddressDb::class.java,
                        "address.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
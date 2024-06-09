package org.d3if0020.assesment3mobpro.database

import org.d3if0020.assesment3mobpro.data.Address
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AddressDao{
    @Insert
    suspend fun insert(address: Address)
    @Update
    suspend fun update(address: Address)
    @Query("SELECT * FROM address ORDER BY isMain DESC, id DESC")
    fun getAddress(): Flow<List<Address>>
    @Query("SELECT * FROM address WHERE id = :id")
    suspend fun getAddressById(id: Long): Address?
    @Query("DELETE FROM address WHERE id = :id")
    suspend fun deleteById(id: Long)
    @Query("UPDATE address SET isMain = 0 WHERE id != :id")
    suspend fun updateAllExceptToFalseExcept(id: Long)
    @Query("UPDATE address SET isMain = 0")
    suspend fun updateAllExceptToFalse()
    @Query("SELECT * FROM address WHERE isMain = 1 LIMIT 1")
    suspend fun getMainAddress(): Address?

}
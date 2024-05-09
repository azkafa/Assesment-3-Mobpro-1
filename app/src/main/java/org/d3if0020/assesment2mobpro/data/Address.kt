package org.d3if0020.assesment2mobpro.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address")
data class Address(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val label: String,
    val alamat: String,
    val type: String,
    val isMain: Boolean = false
)

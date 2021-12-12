package com.example.copper15.data.database

import androidx.room.*
import io.reactivex.Single

@Dao
interface OfferDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOffers(vararg offers: OfferEntity)

    @Update
    fun updateOffers(offers: Array<out OfferEntity>)

    @Query("SELECT * FROM offers")
    fun loadAllOffers(): Single<List<OfferEntity>>
}

package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Grupa

@Dao
interface GrupaDAO {

    @Query("SELECT * from grupa")
    fun getAll() : List<Grupa>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(grupa : Grupa)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(grupe: List<Grupa>)

    @Query("DELETE FROM grupa")
    fun deleteAll()
}
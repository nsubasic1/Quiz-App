package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Pitanje

@Dao
interface PitanjeDAO {
    @Query("SELECT * from pitanje")
    fun getAll() : List<Pitanje>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pitanje: Pitanje)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(pitanje: List<Pitanje>)

    @Query("DELETE FROM pitanje")
    fun deleteAll()

    @Query("select max(idB)+1 from pitanje")
    fun getId() : Int

    @Query("select * from pitanje where kvizId = :id")
    fun getPitanja(id: Int): List<Pitanje>
}
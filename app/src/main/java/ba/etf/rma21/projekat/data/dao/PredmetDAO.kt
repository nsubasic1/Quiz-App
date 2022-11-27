package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet

@Dao
interface PredmetDAO {
    @Query("SELECT * from predmet")
    fun getAll() : List<Predmet>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(predmet: Predmet)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(predmet: List<Predmet>)

    @Query("DELETE FROM predmet")
    fun deleteAll()
}
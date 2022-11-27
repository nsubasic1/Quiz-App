package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Pitanje

@Dao
interface KvizDAO {
    @Query("SELECT * from kviz")
    fun getAll() : List<Kviz>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(kviz : Kviz)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(kviz: List<Kviz>)

    @Query("DELETE FROM kviz")
    fun deleteAll()

    @Query("update kviz set predan = :bul where id = :idKviza")
    fun predajKviz(idKviza : Int, bul : Boolean)
}
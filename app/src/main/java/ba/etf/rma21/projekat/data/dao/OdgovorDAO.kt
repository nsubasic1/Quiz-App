package ba.etf.rma21.projekat.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.OdgRet
import ba.etf.rma21.projekat.data.models.Odgovor


@Dao
interface OdgovorDAO {
    @Query("SELECT * from odgovor")
    fun getAll() : List<Odgovor>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(grupa : Odgovor)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(grupe: List<Odgovor>)

    @Query("DELETE FROM odgovor")
    fun deleteAll()

    @Query("select max(id) + 1 from odgovor")
    fun getId() : Int

    @Query("select * from odgovor where KvizId = :idKviza and KvizTakenId = :idKvizTaken")
    fun getOdgovori(idKviza: Int, idKvizTaken: Int): List<Odgovor>

    @Query("select count(*) from odgovor where KvizId = :kvizId and KvizTakenId = :kvizTakenId and PitanjeId = :idP")
    suspend fun provjeri(kvizId : Int, kvizTakenId : Int, idP : Int): Int
}
package ba.etf.rma21.projekat.data.dao

import androidx.room.*
import ba.etf.rma21.projekat.data.models.Account

@Dao
interface AccountDAO {

    @Query("delete from account")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(acc : Account)

    @Query("select * from account where acHash = :hash")
    fun getAcc(hash : String) : Account

    @Query("update account set lastUpdate = :datum where acHash = :hash")
    fun apdejt(datum : String, hash : String)
}
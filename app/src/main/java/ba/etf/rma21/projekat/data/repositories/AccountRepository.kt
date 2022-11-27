package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.Account
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class AccountRepository {

    companion object{

        private lateinit var context: Context
        fun setContext(_context:Context){
            context=_context
            PitanjeKvizRepository.setContext(context)
        }

        var prvi = true;

        var acHash = "9003419d-5e5b-46b3-87bc-b4eb38cbbfb7"
        suspend fun postaviHash(Hash:String):Boolean {
            return withContext(Dispatchers.IO) {
                if(prvi == true || acHash != Hash){
                    prvi = false
                    acHash = Hash
                    val dao = AppDatabase.getInstance(context)
                    dao.accountDao().deleteAll()
                    dao.predmetDao().deleteAll()
                    dao.grupaDao().deleteAll()
                    dao.odgovorDao().deleteAll()
                    dao.kvizDao().deleteAll()
                    dao.pitanjeDao().deleteAll()
                    val format = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")
                    val dat = format.format(Calendar.getInstance().time)
                    dao.accountDao().insert(Account(1, "novi", acHash, dat))
                }
                return@withContext true
            }
        }

        suspend fun getDatum():String {
            return withContext(Dispatchers.IO) {
                val dao = AppDatabase.getInstance(context)
                return@withContext dao.accountDao().getAcc(acHash).lastUpdate
            }
        }

        fun getHash():String{
            return acHash
        }

    }
}
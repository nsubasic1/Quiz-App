package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import ba.etf.rma21.projekat.ApiAdapter
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.Account
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class DBRepository {

    companion object {

        private lateinit var context: Context
        fun setContext(_context: Context) {
            context = _context
        }

        suspend fun updateNow(): Boolean {
            return withContext(Dispatchers.IO) {
                val datum = AccountRepository.getDatum()
                println(datum)
                val rez = ApiAdapter.retrofit.getLastUpdate(AccountRepository.getHash(), datum)
                if (rez.changed) {
                    val dao = AppDatabase.getInstance(context)
                    val format = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")
                    dao.accountDao().apdejt(format.format(Calendar.getInstance().time), AccountRepository.acHash)
                    dao.grupaDao().insertAll(PredmetIGrupaRepository.getUpisaneGrupe())
                    dao.predmetDao().insertAll(PredmetIGrupaRepository.getUpisaniPredmeti())
                    val kvizovi = KvizRepository.getUpisani()
//                    kvizovi.forEach { it.datumKrajB = it.datumKraj.toString(); it.datumPocetkaB = it.datumKraj.toString()}
                    dao.kvizDao().insertAll(kvizovi)
                    dao.pitanjeDao().deleteAll()
                    for(kviz in kvizovi){
                        var pitanja = PitanjeKvizRepository.getPitanja(kviz.id)
                        pitanja.forEach { it.idB = dao.pitanjeDao().getId(); it.opcijeB = it.opcije.joinToString(",") }
                        dao.pitanjeDao().insertAll(pitanja)
                    }
                }
                return@withContext rez.changed
            }
        }

    }
}
package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import android.system.Os.remove
import ba.etf.rma21.projekat.ApiAdapter
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet
import com.google.gson.internal.`$Gson$Preconditions`
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.function.Predicate

class PredmetIGrupaRepository {

    companion object {

        private lateinit var context: Context
        fun setContext(_context:Context){
            context=_context
        }

        suspend fun getPredmeti(): List<Predmet> {
            return withContext(Dispatchers.IO) {
                return@withContext ApiAdapter.retrofit.getPredmeti()
            }
        }

        suspend fun getGrupe(): List<Grupa> {
            return withContext(Dispatchers.IO) {
                return@withContext ApiAdapter.retrofit.getGrupe()
            }
        }

        suspend fun getGrupeZaPredmet(idPredmeta: Int): List<Grupa> {
            return withContext(Dispatchers.IO) {
                return@withContext ApiAdapter.retrofit.getGrupeZaPredmet(idPredmeta)
            }
        }

        suspend fun upisiUGrupu(idGrupa: Int): Boolean {
            return withContext(Dispatchers.IO) {
                try {
                    val mess = ApiAdapter.retrofit.upisiUGrupu(idGrupa, AccountRepository.getHash())
                    if (mess.por.contains("je dodan u grupu")) return@withContext true
                    return@withContext false
                } catch (e: Exception) {
                    return@withContext false
                }
            }
        }

        suspend fun getUpisaneGrupe(): List<Grupa> {
            return withContext(Dispatchers.IO) {
                return@withContext ApiAdapter.retrofit.getUpisaneGrupe(AccountRepository.getHash())
            }
        }

        suspend fun getPredmetById(id: Int): Predmet {
            return withContext(Dispatchers.IO) {
                return@withContext ApiAdapter.retrofit.getPredmetById(id)
            }
        }

        suspend fun getGrupeZaKviz(id: Int): List<Grupa> {
            return withContext(Dispatchers.IO) {
                return@withContext ApiAdapter.retrofit.getGrupeZaKviz(id)
            }
        }

        suspend fun getNeupisanePredmeteNaGodini(godina: Int): List<Predmet> {
            return withContext(Dispatchers.IO) {
                var predmeti = getPredmeti().filter { godina == it.godina }
                val grupe = getUpisaneGrupe()
                for (predmet in predmeti) {
                    var upisan: Boolean = false
                    for (grupa in grupe) {
                        if (grupa.pId == predmet.id) upisan = true
                    }
                    if (upisan == true) predmeti -= predmet
                }
//                predmeti.filter { predmet : Predmet -> grupe.none { it.pId!! == predmet.id } }
                return@withContext predmeti
            }
        }

        suspend fun getUpisaniPredmeti() : List<Predmet>{
            return withContext(Dispatchers.IO){
                var predmeti = getPredmeti()
                val grupe = getUpisaneGrupe()
                for (predmet in predmeti) {
                    var upisan: Boolean = false
                    for (grupa in grupe) {
                        if (grupa.pId == predmet.id) upisan = true
                    }
                    if (upisan == false) predmeti -= predmet
                }
                return@withContext predmeti
            }
        }

//        suspend fun dajPredmet(ime : String) : Predmet {
//            return withContext(Dispatchers.IO){
//                return@withContext ApiAdapter.retrofit.getGrupeZaKviz(id)
//            }
//        }
    }
}
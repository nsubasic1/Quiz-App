package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import ba.etf.rma21.projekat.ApiAdapter
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.StatusKviza
import ba.etf.rma21.projekat.data.*
import ba.etf.rma21.projekat.data.models.Kviz
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class KvizRepository {

    companion object {
        var trenutniSpinner = -1

        suspend fun getUpisani(): List<Kviz> {
            return withContext(Dispatchers.IO){
                val acc = AccountRepository()
                var grupe = ApiAdapter.retrofit.getUpisaneGrupe(AccountRepository.getHash())
                var rezultat = mutableListOf<Kviz>()
                for(grupa in grupe){
                    var pom = ApiAdapter.retrofit.dajKvizoveNaGrupi(grupa.id)
                    val nazivPredmeta = ApiAdapter.retrofit.getPredmetById(grupa.pId!!).naziv
                    pom.stream().forEach { x -> x.nazivGrupe = grupa.naziv;
                        if(x.nazivPredmeta == "")
                            x.nazivPredmeta = nazivPredmeta
                        else x.nazivPredmeta+= "," + nazivPredmeta
                    }
                    rezultat.addAll(pom)
                }
//                var izbacuj = mutableListOf<Int>()
//                var i = 0
//                var j = 0
//                while(i < rezultat.size){
//                    j = i + 1
//                    while(j < rezultat.size){
//                        if(rezultat[i].id == rezultat[j].id){
//                            rezultat[i].nazivPredmeta+= "," + rezultat[j].nazivPredmeta
//                            rezultat.removeAt(j)
//                            izbacuj.add(j)
//                        }
//                        j++
//                    }
//                    i++
//                }
//                for(izbaci in izbacuj)
//                    rezultat.removeAt(izbaci)
                return@withContext rezultat
            }

        }

        private lateinit var context: Context
        fun setContext(_context:Context){
            context=_context
        }

        suspend fun getMyKvizes() : List<Kviz>{
            return withContext(Dispatchers.IO) {
                val dao = AppDatabase.getInstance(context)
                var kvizovi = dao.kvizDao().getAll()
                for (kviz in kvizovi) {
                    val trenutniDatum: Date = Calendar.getInstance().run { time }
                    if (kviz.datumRada != null) {
                        kviz.status = StatusKviza.URAĐEN
                    } else if (kviz.datumKraj == null && kviz.datumPocetka.after(trenutniDatum)) {
                        kviz.status = StatusKviza.BUDUĆI
                    } else if (kviz.datumKraj == null && kviz.datumPocetka.before(trenutniDatum)) {
                        kviz.status = StatusKviza.AKTIVAN
                    } else if (kviz.datumPocetka.before(trenutniDatum) && kviz.datumKraj.after(
                            trenutniDatum
                        )
                    ) {
                        kviz.status = StatusKviza.AKTIVAN
                    } else if (kviz.datumPocetka.after(trenutniDatum)) {
                        kviz.status = StatusKviza.BUDUĆI
                    } else {
                        kviz.status = StatusKviza.NEURAĐEN
                    }
                }
                return@withContext kvizovi
            }
        }

        suspend fun getAll(): List<Kviz> {
            return withContext(Dispatchers.IO) {
                val kvizovi = ApiAdapter.retrofit.getAll()
                var rezultat = mutableListOf<Kviz>()
                for (kviz in kvizovi) {
                    var predmeti = mutableListOf<String>()
                    val grupe = ApiAdapter.retrofit.getGrupeZaKviz(kviz.id)
                    for (grupa in grupe) {
                        val predmet = ApiAdapter.retrofit.getPredmetById(grupa.pId!!).naziv
                        if (!predmeti.contains(predmet)) {
                            predmeti.add(predmet)
                            if (kviz.nazivPredmeta == "") kviz.nazivPredmeta = predmet
                            else kviz.nazivPredmeta += ", " + predmet
                        }
                    }
                    rezultat.add(kviz)
                }
//                var izbacuj = mutableListOf<Int>()
//                var i = 0
//                var j = 0
//                while(i < rezultat.size){
//                    j = i + 1
//                    while(j < rezultat.size){
//                        if(rezultat[i].id == rezultat[j].id){
//                            rezultat[i].nazivPredmeta+= "," + rezultat[j].nazivPredmeta
//                            rezultat.removeAt(j)
//                            izbacuj.add(j)
//                        }
//                        j++
//                    }
//                    i++
//                }
//                for(izbaci in izbacuj)
//                    rezultat.removeAt(izbaci)
                return@withContext rezultat
            }
        }

        suspend fun getDone(): List<Kviz> {
            return withContext(Dispatchers.IO) {
                return@withContext getMyKvizes().filter { it.status == StatusKviza.URAĐEN }
                    .sortedBy { it.datumPocetka }
            }
        }

        suspend fun getFuture(): List<Kviz> {
            return withContext(Dispatchers.IO) {
                return@withContext getMyKvizes().filter { it.status == StatusKviza.BUDUĆI }
                    .sortedBy { it.datumPocetka }
            }
        }

        suspend fun getNotTaken(): List<Kviz> {
            return withContext(Dispatchers.IO) {
                return@withContext getMyKvizes().filter { it.status == StatusKviza.NEURAĐEN }
                    .sortedBy { it.datumPocetka }
            }
        }


        suspend fun dajKvizoveNaGrupi(idGrupa: Int): List<Kviz> {
            return withContext(Dispatchers.IO) {
                return@withContext ApiAdapter.retrofit.dajKvizoveNaGrupi(idGrupa)
            }
        }

        suspend fun getById(id: Int): Kviz {
            return withContext(Dispatchers.IO) {
                return@withContext ApiAdapter.retrofit.getById(id)
            }
        }
    }
}
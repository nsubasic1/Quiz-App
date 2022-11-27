package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import ba.etf.rma21.projekat.ApiAdapter
import ba.etf.rma21.projekat.StatusKviza
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.models.PitanjeKviz
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PitanjeKvizRepository {
    companion object{
        var pitanja = emptyList<Pitanje>()
        private val pitanjeKviz = emptyList<PitanjeKviz>()
        var indeksPitanja : Int = 0
        var kviz : Kviz? = null
        fun getPitanja(nazivKviza : String, nazivPredmeta : String) : List<Pitanje> {
            var lista = emptyList<Pitanje>()
            for(pitanje in pitanja){
                if(pitanjeKviz.any { it.naziv == pitanje.naziv && it.kviz == nazivKviza && it.predmet == nazivPredmeta})
                    lista += pitanje
        }
            return lista
        }

        suspend fun getPitanja(idKviza : Int) : List<Pitanje>{
            return withContext(Dispatchers.IO){
            return@withContext ApiAdapter.retrofit.getPitanja(idKviza)
            }
        }

        private lateinit var context:Context
        fun setContext(_context: Context){
            context=_context
        }

        suspend fun getPitanjaBaza(id: Int): List<Pitanje> {
            return withContext(Dispatchers.IO) {
                val dao = AppDatabase.getInstance(context)
                val pitanja = dao.pitanjeDao().getPitanja(id)
                for (pitanje in pitanja) {
                    pitanje.opcije = pitanje.opcijeB.split(",")
                }
                return@withContext pitanja
            }
        }

//        fun spasiOdgovor(pitanje : String, odgovor : Int){
//            pitanjeKviz.first { it.naziv == pitanje && it.kviz == idKviza}.odgovor = odgovor
//        }
//
//        fun dajOdgovor(pitanje: String) : Int {
//            return pitanjeKviz.first { it.naziv == pitanje && it.kviz == idKviza && it.predmet == predmetKviza }.odgovor
//        }

    }
}
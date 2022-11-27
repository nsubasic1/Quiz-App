package ba.etf.rma21.projekat.data.viewModels

import ba.etf.rma21.projekat.StatusKviza
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import ba.etf.rma21.projekat.data.repositories.PitanjeKvizRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PitanjeKvizViewModel {
    fun dajPitanja(nazivKviza: String, nazivPredmeta: String): List<Pitanje> {
        return PitanjeKvizRepository.getPitanja(nazivKviza, nazivPredmeta)
    }

    fun dajPitanja(idKviza: Int, onSucces: (List<Pitanje>) -> Unit, onError: () -> Unit) {
        GlobalScope.launch {
            val pitanja = PitanjeKvizRepository.getPitanja(idKviza)
            when (pitanja) {
                is List<Pitanje> -> onSucces?.invoke(pitanja)
                else -> onError?.invoke()
            }
        }
    }

//    fun dajKviz() : Kviz {
//        return KvizRepository.getKviz(idKviza, predmetKviza)
//    }
//
//    fun spasiOdgovor(pitanje : String, odgovor : Int){
//        PitanjeKvizRepository.spasiOdgovor(pitanje, odgovor)
//    }
//    fun dajOdgovor(pitanje : String) : Int{
//        return PitanjeKvizRepository.dajOdgovor(pitanje)
//    }

    fun dajStatusTrenutnog() : StatusKviza{
        return PitanjeKvizRepository.kviz!!.status
    }

    fun postaviTrenutno(kviz : Kviz) {
        PitanjeKvizRepository.kviz = kviz
    }

    fun dajTrenutni() : Kviz?{
        return PitanjeKvizRepository.kviz
    }

    fun setPitanje(index : Int){
        PitanjeKvizRepository.indeksPitanja = index
    }

    fun getPitanje() : Int{
        return PitanjeKvizRepository.indeksPitanja
    }

    fun postaviPitanja(lista : List<Pitanje>){
        PitanjeKvizRepository.pitanja = lista   }

    fun dajPitanjaBaza(id: Int,  onSucces: (List<Pitanje>) -> Unit, onError: () -> Unit){
        GlobalScope.launch {
            val pitanja = PitanjeKvizRepository.getPitanjaBaza(id)
            when (pitanja) {
                is List<Pitanje> -> onSucces?.invoke(pitanja)
                else -> onError?.invoke()
            }
        }

    }
}
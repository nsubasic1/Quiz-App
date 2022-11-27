package ba.etf.rma21.projekat.data.viewModels

import ba.etf.rma21.projekat.data.models.OdgRet
import ba.etf.rma21.projekat.data.models.Odgovor
import ba.etf.rma21.projekat.data.repositories.OdgovorRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception

class OdgovorViewModel {
        fun getOdgovori(idKviza : Int, onSucces : (List<OdgRet>) -> Unit, onError : () -> Unit){
            GlobalScope.launch {
                var odgovori = listOf<OdgRet>()
                runBlocking {
                    odgovori = OdgovorRepository.getOdgovori(idKviza)
                }
                when(odgovori){
                    is List<OdgRet>-> onSucces?.invoke(odgovori)
                    else -> onError?.invoke()
                }
            }
        }

    fun getOdgovori(idKviza: Int, idKvizTaken: Int, onSucces : (List<Odgovor>) -> Unit, onError : () -> Unit){
        GlobalScope.launch {
            val odgovori = OdgovorRepository.getOdgovori(idKviza, idKvizTaken)
            when(odgovori){
                is List<Odgovor>-> onSucces?.invoke(odgovori)
                else -> onError?.invoke()
            }
        }

    }

    fun postaviOdgovorKviz(idKvizTaken : Int, idPitanje : Int, odgovor : Int, onSucces : (Int) -> Unit, onError : () -> Unit){
        GlobalScope.launch {
            val odg = OdgovorRepository.postaviOdgovorApi(idKvizTaken, idPitanje, odgovor)
            when(odg){
                is Int-> onSucces?.invoke(odg)
                else -> onError?.invoke()
            }
        }
    }

    fun predajOdgovore(idKviza: Int, onSucces : ()->Unit, onError: () -> Unit){
        GlobalScope.launch {
            OdgovorRepository.predajOdgovore(idKviza)
        }
    }


    fun dodajOdgovorBaza( odg : Int, idP : Int, idKvizTaken: Int, onSucces : (Int)->Unit, onError: () -> Unit){
        GlobalScope.launch {
            try {
                val rez = OdgovorRepository.postaviOdgovorKviz(idKvizTaken, idP, odg)
                onSucces.invoke(rez)
            }catch(e : Exception){
                onError.invoke()
            }
        }
    }

    fun dajOdgovore() : List<Odgovor>{
        return OdgovorRepository.odgovori
    }

    fun setOdgovori(lista : List<Odgovor>){
        OdgovorRepository.odgovori = lista
    }

    fun dodajOdgovor(odgovor: Odgovor) {
        OdgovorRepository.odgovori += odgovor
    }
}
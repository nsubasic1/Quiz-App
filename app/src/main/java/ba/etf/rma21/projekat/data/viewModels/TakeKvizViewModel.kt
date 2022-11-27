package ba.etf.rma21.projekat.data.viewModels

import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.repositories.PitanjeKvizRepository
import ba.etf.rma21.projekat.data.repositories.TakeKvizRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TakeKvizViewModel {
    fun zapocniKviz(idKviza : Int,  onSucces : (KvizTaken) -> Unit, onError : () -> Unit){
        GlobalScope.launch {
            val kvizTaken = TakeKvizRepository.zapocniKviz(idKviza)
            when(kvizTaken){
                is KvizTaken-> onSucces?.invoke(kvizTaken)
                else -> onError?.invoke()
            }
        }
    }

    fun getPocetniKvizovi(onSucces : (List<KvizTaken>?) -> Unit, onError : () -> Unit) {
        GlobalScope.launch {
            val kvizovi = TakeKvizRepository.getPocetiKvizovi()
            when (kvizovi) {
                is List<KvizTaken> -> onSucces?.invoke(kvizovi)
                null -> onSucces?.invoke(kvizovi)
                else -> onError?.invoke()
            }
        }
    }
    fun getKvizTaken() : KvizTaken?{
        return TakeKvizRepository.kvizTaken
    }

    fun setKvizTaken(kt : KvizTaken) {
        TakeKvizRepository.kvizTaken = kt
    }
}
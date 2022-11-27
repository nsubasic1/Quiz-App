package ba.etf.rma21.projekat.data.viewModels

import ba.etf.rma21.projekat.StatusKviza
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.repositories.KvizRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class KvizListViewModel {
    fun spinner():Int{
        return KvizRepository.trenutniSpinner
    }
    fun setSpinner(br : Int){
        KvizRepository.trenutniSpinner = br
    }
    fun dajUrađeneKvizove(onSucces : (List<Kviz>) -> Unit, onError : () -> Unit) {
        GlobalScope.launch {
            val kvizovi = KvizRepository.getDone()
            when(kvizovi){
                is List<Kviz>-> onSucces?.invoke(kvizovi)
                else -> onError?.invoke()
            }
        }

    }

    fun getMyKvizes(onSucces : (List<Kviz>) -> Unit, onError : () -> Unit) {
        GlobalScope.launch {
            val kvizovi =  KvizRepository.getMyKvizes()
            when(kvizovi){
                is List<Kviz>-> onSucces?.invoke(kvizovi)
                else -> onError?.invoke()
            }
        }

    }

    fun dajBuduceKvizove(onSucces : (List<Kviz>) -> Unit, onError : () -> Unit) {
        GlobalScope.launch {
            val kvizovi =  KvizRepository.getFuture()
            when(kvizovi){
                is List<Kviz>-> onSucces?.invoke(kvizovi)
                else -> onError?.invoke()
            }
        }
    }

    fun dajSveKvizove(onSucces : (List<Kviz>) -> Unit, onError : () -> Unit) {
//        return KvizRepository.getAll()
        GlobalScope.launch {
            val kvizovi = KvizRepository.getAll()
            when(kvizovi){
                is List<Kviz>-> onSucces?.invoke(kvizovi)
                else -> onError?.invoke()
            }
        }
    }

    fun dajNeurađeneKvizove(onSucces : (List<Kviz>) -> Unit, onError : () -> Unit) {
        GlobalScope.launch {
            val kvizovi = KvizRepository.getNotTaken()
            when(kvizovi){
                is List<Kviz>-> onSucces?.invoke(kvizovi)
                else -> onError?.invoke()
            }
        }
    }

    fun dajAktivneKvizove(onSucces : (List<Kviz>) -> Unit, onError : () -> Unit) {
        GlobalScope.launch {
            val kvizovi =  KvizRepository.getMyKvizes().filter { it.status == StatusKviza.AKTIVAN }
            when(kvizovi){
                is List<Kviz>-> onSucces?.invoke(kvizovi)
                else -> onError?.invoke()
            }
        }

    }

    fun dajSveMojeKvizove(onSucces: (List<Kviz>) -> Unit, onError: () -> Unit ) {
        GlobalScope.launch {
            val kvizovi = KvizRepository.getUpisani()
            when(kvizovi){
                is List<Kviz>-> onSucces?.invoke(kvizovi)
                else -> onError?.invoke()
            }
        }
    }

    fun onSucces1(grupe : List<Grupa>){
        grupe.forEach {

        }
    }

    fun onError(){

    }

    fun dajKvizoveNaGrupi(idGrupa : Int, onSucces : (List<Kviz>) -> Unit, onError : () -> Unit){
        GlobalScope.launch {
            val kvizovi = KvizRepository.dajKvizoveNaGrupi(idGrupa)
            when(kvizovi){
                is List<Kviz>-> onSucces?.invoke(kvizovi)
                else -> onError?.invoke()
            }
        }
    }

    fun getById(id : Int, onSucces : (Kviz)->Unit, onError: () -> Unit){
        GlobalScope.launch {
            val kvizovi = KvizRepository.getById(id)
            when(kvizovi){
                is Kviz-> onSucces?.invoke(kvizovi)
                else -> onError?.invoke()
            }
        }
    }
}
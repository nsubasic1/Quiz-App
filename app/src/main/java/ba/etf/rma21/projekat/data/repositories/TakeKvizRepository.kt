package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.ApiAdapter
import ba.etf.rma21.projekat.data.models.KvizTaken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TakeKvizRepository {
    companion object{

        var kvizTaken : KvizTaken? = null
        suspend fun zapocniKviz(idKviza : Int): KvizTaken{
            return withContext(Dispatchers.IO){
                return@withContext ApiAdapter.retrofit.zapocniKviz(idKviza, AccountRepository.getHash())
            }
        }
        suspend fun getPocetiKvizovi():List<KvizTaken>?{
            return withContext(Dispatchers.IO){
                var lista = ApiAdapter.retrofit.getPocetiKvizovi(AccountRepository.getHash())
                if (lista.size == 0) return@withContext null
                return@withContext lista
            }
        }
    }
}
package ba.etf.rma21.projekat.data.viewModels

import ba.etf.rma21.projekat.data.repositories.AccountRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AccountViewModel {

    fun upisiHash(hash : String, onSucces : (Boolean)->Unit, onError: ()->Unit){
        GlobalScope.launch {
            val rez = AccountRepository.postaviHash(hash)
            when(rez){
                true -> onSucces.invoke(rez)
                else -> onError.invoke()
            }
        }
    }

}
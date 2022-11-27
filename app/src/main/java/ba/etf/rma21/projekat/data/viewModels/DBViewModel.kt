package ba.etf.rma21.projekat.data.viewModels

import ba.etf.rma21.projekat.data.repositories.DBRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DBViewModel {

    fun updateNow(onSucces : (Boolean) -> Unit, onError: () -> Unit){
        GlobalScope.launch {
            val rez = DBRepository.updateNow()
            when(rez){
                true -> onSucces.invoke(rez)
                false -> onError.invoke()
            }
        }
    }

}
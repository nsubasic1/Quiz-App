package ba.etf.rma21.projekat.data.viewModels

import ba.etf.rma21.projekat.ApiAdapter
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.repositories.PredmetIGrupaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PredmetIGrupaViewModel {
    fun getUpisaneGrupe(onSucces : (List<Grupa>) -> Unit, onError : ()-> Unit){
        GlobalScope.launch {
            val grupe = PredmetIGrupaRepository.getUpisaneGrupe()
            when(grupe){
                is List<Grupa> -> onSucces.invoke(grupe)
                else -> onError.invoke()
            }
        }
    }

    fun getPredmeti(onSucces : (List<Predmet>) -> Unit, onError : ()-> Unit){
        GlobalScope.launch {
            val grupe = PredmetIGrupaRepository.getPredmeti()
            when(grupe){
                is List<Predmet> -> onSucces.invoke(grupe)
                else -> onError.invoke()
            }
        }
    }

    fun getGrupe(onSucces : (List<Grupa>) -> Unit, onError : ()-> Unit){
        GlobalScope.launch {
            val grupe = PredmetIGrupaRepository.getGrupe()
            when(grupe){
                is List<Grupa> -> onSucces.invoke(grupe)
                else -> onError.invoke()
            }
        }
    }

    fun getGrupeZaPredmet(idPredmeta : Int, onSucces : (List<Grupa>) -> Unit, onError : ()-> Unit){
        GlobalScope.launch {
            val grupe = PredmetIGrupaRepository.getGrupeZaPredmet(idPredmeta)
            when(grupe){
                is List<Grupa> -> onSucces.invoke(grupe)
                else -> onError.invoke()
            }
        }
    }

    fun upisiUGrupu(id : Int, onSucces : (Boolean) -> Unit, onError : ()-> Unit){
        GlobalScope.launch {
            val grupe = PredmetIGrupaRepository.upisiUGrupu(id)
            when(grupe){
                true -> onSucces.invoke(grupe)
                else -> onError.invoke()
            }
        }
    }

    fun getPredmetById(id : Int, onSucces : (Predmet) -> Unit, onError : ()-> Unit){
        GlobalScope.launch {
            val grupe = PredmetIGrupaRepository.getPredmetById(id)
            when(grupe){
                is Predmet -> onSucces.invoke(grupe)
                else -> onError.invoke()
            }
        }
    }

    fun getGrupeZaKviz(id : Int,onSucces : (List<Grupa>) -> Unit, onError : ()-> Unit) {
        GlobalScope.launch {
            val grupe = PredmetIGrupaRepository.getGrupeZaKviz(id)
            when (grupe) {
                is List<Grupa> -> onSucces.invoke(grupe)
                else -> onError.invoke()
            }
        }
    }

    fun getNeupisanePredmeteNaGodini(godina : Int, onSucces : (List<Predmet>) -> Unit, onError : ()-> Unit) {
        GlobalScope.launch {
            val grupe = PredmetIGrupaRepository.getNeupisanePredmeteNaGodini(godina)
            when (grupe) {
                is List<Predmet> -> onSucces.invoke(grupe)
                else -> onError.invoke()
            }
        }
    }
}
package ba.etf.rma21.projekat.data.viewModels

import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.repositories.PredmetRepository

class PredmetListViewModel() {
    fun dajNeupisanePredmeteNaGodini(god : Int) : List<Predmet> {
        return PredmetRepository.getAll().filter { it.upisan == false && it.godina == god}
    }

    fun dajSvePredmete() : List<Predmet> {
        return PredmetRepository.getAll()
    }

    fun dajPredmeteNaGodini(god : Int) : List<Predmet> {
        return PredmetRepository.getPredmetsByGodina(god)
    }

    fun dajPredmet(ime : String) : Predmet {
        return PredmetRepository.dajPredmet(ime)
    }

}
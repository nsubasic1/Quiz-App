package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Predmet

class PredmetRepository {
    companion object {
            var godina = 0
            var predmet = -1
            var grupa = -1

        var predmeti = emptyList<Predmet>()
        fun getUpisani(): List<Predmet> {
            return predmeti.filter { it.upisan == true }
        }

        fun getAll(): List<Predmet> {
            return predmeti
        }

        fun getPredmetsByGodina(godina:Int) : List<Predmet> {
            return predmeti.filter { it.godina == godina }
        }

        fun dajPredmet(ime : String) : Predmet {
            return predmeti.first{it.naziv == ime}
        }
        // TODO: Implementirati i ostale potrebne metode
    }

}
package ba.etf.rma21.projekat.data.viewModels

import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.repositories.GrupaRepository

class GrupaListViewmodel {
    fun dajGrupeNaPredmetu(nazivPredmeta: String): List<Grupa> {
        return GrupaRepository.getGroupsByPredmet(nazivPredmeta)
    }

    fun dajGrupu(ime : String, predmet : String) : Grupa {
        return GrupaRepository.getGroup(ime, predmet)
    }
}
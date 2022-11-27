package ba.etf.rma21.projekat.data.repositories

import ba.etf.rma21.projekat.data.models.Grupa

class GrupaRepository {

    companion object {
        var grupe = emptyList<Grupa>()
        init {
            // TODO: Implementirati
        }

        fun getGroupsByPredmet(nazivPredmeta: String): List<Grupa> {
            return grupe
        }

        fun getGroup(ime: String, predmet: String): Grupa {
            return getGroupsByPredmet(predmet).first {it.naziv == ime }
        }
    }
}
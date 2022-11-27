//package ba.etf.rma21.projekat
//
//import ba.etf.rma21.projekat.data.repositories.PredmetRepository
//import ba.etf.rma21.projekat.data.viewModels.GrupaListViewmodel
//import org.junit.Assert.*
//import org.junit.Test
//
//class GrupaTest {
//    private val grupaListViewmodel : GrupaListViewmodel = GrupaListViewmodel()
//
//    @Test
//    fun grupeNaPredmetuTest(){
//        for( p in PredmetRepository.getAll().map { it.naziv })
//            grupaListViewmodel.dajGrupeNaPredmetu(p).forEach { assertEquals(it.nazivPredmeta, p) }
//    }
//
//    @Test (expected = NoSuchElementException::class)
//    fun dajGrupu1() {
//        grupaListViewmodel.dajGrupu("fgerg", "fg")
//    }
//
//    @Test
//    fun dajGrupu2() {
//        assertEquals(grupaListViewmodel.dajGrupu("grupa2", "SP").naziv, "grupa2")
//        assertEquals(grupaListViewmodel.dajGrupu("grupa2", "SP").nazivPredmeta, "SP")
//    }
//}
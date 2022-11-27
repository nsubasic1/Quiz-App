//package ba.etf.rma21.projekat
//
//import ba.etf.rma21.projekat.data.models.Predmet
//import ba.etf.rma21.projekat.data.viewModels.PredmetListViewModel
//import org.junit.Test
//import org.junit.Assert.*
//
//class PredmetTest {
//    private val predmetListViewModel : PredmetListViewModel = PredmetListViewModel()
//
//    @Test
//    fun godinaTest(){
//        for(i in 1..5)
//        predmetListViewModel.dajPredmeteNaGodini(i).forEach { assertEquals(it.godina, i)}
//    }
//
//    @Test
//    fun sviPredmetiTest() {
//        var test = listOf<Predmet>()
//        for(i in 1..5)
//            test += predmetListViewModel.dajPredmeteNaGodini(i)
//        assertEquals(test.sortedBy { it.naziv }, predmetListViewModel.dajSvePredmete().sortedBy { it.naziv })
//    }
//
//    @Test
//    fun neupisaniNaGodiniTest(){
//        for(i in 1..5)
//            predmetListViewModel.dajNeupisanePredmeteNaGodini(i).forEach { assertEquals(it.godina, i); assertFalse(it.upisan) }
//    }
//
//    @Test(expected = NoSuchElementException::class)
//    fun dajPredmetTest1() {
//        predmetListViewModel.dajPredmet("dfherdg")
//    }
//
//    @Test
//    fun dajPredmetTest2() {
//        assertEquals(predmetListViewModel.dajPredmet("TP").naziv, "TP")
//        assertEquals(predmetListViewModel.dajPredmet("TP").godina, 4)
//    }
//
//}
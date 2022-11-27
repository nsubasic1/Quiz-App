//package ba.etf.rma21.projekat
//
//import ba.etf.rma21.projekat.data.viewModels.KvizListViewModel
//import org.junit.Test
//
//import org.junit.Assert.*
//
//class KvizTest {
//    private val kvizListViewModel : KvizListViewModel = KvizListViewModel()
//    @Test
//    fun sviMojiKvizoviTest() {
//        val test = kvizListViewModel.dajAktivneKvizove() + kvizListViewModel.dajBuduceKvizove() + kvizListViewModel.dajNeurađeneKvizove() + kvizListViewModel.dajUrađeneKvizove()
//        val sviMoji = kvizListViewModel.dajSveMojeKvizove()
//        assertEquals(test.sortedBy { it.datumPocetka }, sviMoji)
//    }
//
//    @Test
//    fun aktivniTest(){
//        val test = kvizListViewModel.dajAktivneKvizove()
//        test.forEach{assertEquals(it.status, StatusKviza.AKTIVAN)}
//        assertEquals(test.size, 2)
//        assertEquals(test.get(1).nazivPredmeta, "TP")
//        assertEquals(test.get(1).nazivGrupe, "grrgt4")
//    }
//
//    @Test
//    fun urađeniTest(){
//        val test = kvizListViewModel.dajUrađeneKvizove()
//        test.forEach{assertEquals(it.status, StatusKviza.URAĐEN)}
//        assertEquals(0, test.size)
////        assertEquals(test.get(0).nazivPredmeta, "SP")
////        assertEquals(test.get(0).nazivGrupe, "grupa2")
//    }
//
//    @Test
//    fun neurađeniTest(){
//        val test = kvizListViewModel.dajNeurađeneKvizove()
//        test.forEach{assertEquals(it.status, StatusKviza.NEURAĐEN)}
//        assertEquals(test.size, 1)
//        assertEquals(test.get(0).nazivPredmeta, "OOAD")
//        assertEquals(test.get(0).nazivGrupe, "gr1")
//    }
//
//    @Test
//    fun buduciTest(){
//        val test = kvizListViewModel.dajBuduceKvizove()
//        test.forEach{assertEquals(it.status, StatusKviza.BUDUĆI)}
//        assertEquals(test.size, 1)
//        assertEquals(test.get(0).nazivPredmeta, "XYZ")
//        assertEquals(test.get(0).nazivGrupe, "gr2")
//    }
//
//}
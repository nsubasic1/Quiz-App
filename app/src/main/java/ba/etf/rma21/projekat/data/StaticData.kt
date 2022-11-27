//package ba.etf.rma21.projekat.data
//
//import ba.etf.rma21.projekat.data.models.*
//import java.time.LocalDate
//import java.util.*
//
//fun sviKvizovi() : List<Kviz> {
//    return listOf(
//            Kviz("kvisko1", "RMA", getDate(2021, 2, 20), getDate(2021, 2, 25), null,
//                    3, "gr1", null),
//            Kviz("kvisko2", "OS", getDate(2021, 4, 20), getDate(2021, 4, 21), null,
//                    3, "gr4", null),
//            Kviz("kvisko3", "SP", getDate(2021, 1, 20), getDate(2021, 10, 23), null,
//                    3, "grupa2", 2.5f),
//            Kviz("kvisko12", "SP", getDate(2021, 4, 20), getDate(2021, 4, 23), getDate(2021, 4, 21),
//                    3, "gr4", null),
//            Kviz("kvisko4", "OOAD", getDate(2021, 2, 20), getDate(2021, 2, 23),null,
//                3, "gr1", null),
//            Kviz("kvisko8", "OOAD", getDate(2021, 2, 20), getDate(2021, 2, 23),null,
//                    3, "gr21", null),
//            Kviz("kvisko5", "XYZ", getDate(2022, 2, 4), getDate(2022, 2, 23), null,
//                3, "gr2", null),
//            Kviz("kvisko10", "XYZ", getDate(2021, 8, 4), getDate(2021, 8, 23), null,
//                    3, "grrr1", null),
//            Kviz("kvisko6", "RMA", getDate(2021, 4, 2), getDate(2021, 4, 23), null,
//                3, "gr5", null),
//        Kviz("kvisko11", "OS", getDate(2021, 1, 20), getDate(2021, 1, 23), null,
//            3, "gr2", null),
//            Kviz("kvisko7", "LD", getDate(2021, 2, 4), getDate(2021, 2, 23), getDate(2021, 2, 21),
//                    3, "grbvger4", 2.5f),
//            Kviz("kvisko20", "LD", getDate(2021, 2, 4), getDate(2021, 2, 23), getDate(2021, 2, 21),
//                    3, "g4", 2.5f),
//            Kviz("kvisko13", "ORM", getDate(2021, 2, 4), getDate(2021, 2, 23), null,
//                    3, "gasefswg", null),
//            Kviz("kvisko14", "ORM", getDate(2020, 4, 1), getDate(2020, 4, 23), null,
//                    3, "grupa", null),
//            Kviz("kvisko13", "TP", getDate(2021, 4, 4), getDate(2021, 8, 23), null,
//                    3, "grrgt4", null),
//            Kviz("kvisko13", "TP", getDate(2021, 4, 2), getDate(2021, 4, 20), null,
//                    3, "gr4", null)
//    )
//}
//
//fun predmeti() : List<Predmet> {
//    val lista = listOf(Predmet("OOAD", 2), Predmet("RMA", 2), Predmet("XYZ", 5),
//        Predmet("TP", 4), Predmet("SP", 1),
//        Predmet("OS", 1), Predmet("ORM", 4), Predmet("LD", 5), Predmet("IM1", 5)
//    )
//    lista.get(0).upisan = true;
//    lista.get(3).upisan = true;
//    lista.get(4).upisan = true;
//    lista.get(2).upisan = true;
//    return lista
//}
//
//fun grupe() : List<Grupa> {
//    val lista = listOf(
//        Grupa("gr1", "OOAD"),
//            Grupa("gr21", "OOAD"),
//        Grupa("gr1", "RMA"),
//        Grupa("grupa2", "SP"),
//        Grupa("gr4", "OS"),
//        Grupa("gr5", "RMA"),
//        Grupa("gr2", "XYZ"),
//        Grupa("gr3", "OS"),
//        Grupa("gr4", "SP"),
//        Grupa("grrr1", "XYZ"),
//        Grupa("gr2", "OS"),
//        Grupa("grrgt4", "TP"),
//            Grupa("gr4", "TP"),
//        Grupa("grbvger4", "LD"),
//        Grupa("gasefswg", "ORM"),
//            Grupa("g4", "LD"),
//            Grupa("grupa", "ORM"),
//        Grupa("grupa23213", "IM1"),
//        Grupa("grupar3qwer", "IM1")
//    )
//    lista.get(0).upisan = true;
//    lista.get(11).upisan = true;
//    lista.get(3).upisan = true;
//    lista.get(6).upisan = true;
//    return lista
//}
//
//fun getDate(god : Int, mj : Int, dan : Int) : Date{
//    var cal = GregorianCalendar.getInstance()
//    cal.clear()
//    cal.set(god, mj - 1, dan)
//    return cal.time
//}
//
//fun getPitanja() : List<Pitanje>{
//    return listOf<Pitanje>(Pitanje("pitanje1", "Koji je glavni grad Australije?", listOf("Melbourne", "Canberra", "Sydney"), 1 ),
//    Pitanje("pitanje2", "Koliko je 12 na kvadrat?", listOf("124", "128", "132", "144"), 3), Pitanje("pitanje3", "Koliko je sin(0)?", listOf("0", "1"), 0), Pitanje("pitanje4", "Koliko je 2+2*2?", listOf("8", "6"), 1 ))
//}
//
//fun getPitanjeKviz() : List<PitanjeKviz> {
//    var lista = listOf(PitanjeKviz("pitanje1", "kvisko13", "TP"),
//            PitanjeKviz("pitanje2", "kvisko13", "TP"),
//            PitanjeKviz("pitanje3", "kvisko13", "TP"),
//            PitanjeKviz("pitanje3", "kvisko4", "OOAD"),
//            PitanjeKviz("pitanje4", "kvisko4", "OOAD"),
//            PitanjeKviz("pitanje1", "kvisko3", "SP"),
//            PitanjeKviz("pitanje3", "kvisko3", "SP"),
//            PitanjeKviz("pitanje4", "kvisko3", "SP"),
//            PitanjeKviz("pitanje 4", "kvisko14", "ORM"),
//            PitanjeKviz("pitanje2", "kvisko14", "ORM"),
//            PitanjeKviz("pitanje4", "kvisko1", "RMA"),
//            PitanjeKviz("pitanje2", "kvisko1", "RMA"),
//    PitanjeKviz("pitanje1", "kvisko7", "LD"), //10
//            PitanjeKviz("pitanje4", "kvisko20", "LD"),
//    PitanjeKviz("pitanje2", "kvisko14", "ORM"),
//    PitanjeKviz("pitanje3", "kvisko11", "OS"))
//    lista.get(12).odgovor = 0
//    lista.get(13).odgovor = 0
//    return lista
//}
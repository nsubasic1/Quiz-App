package ba.etf.rma21.projekat.data.repositories

import android.content.Context
import ba.etf.rma21.projekat.ApiAdapter
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.OdgPom
import ba.etf.rma21.projekat.data.models.OdgRet
import ba.etf.rma21.projekat.data.models.Odgovor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.*

class OdgovorRepository {
    companion object {
        var odgovori = listOf<Odgovor>()

        private lateinit var context: Context
        fun setContext(_context: Context) {
            context = _context
        }

        suspend fun postaviOdgovorApi(idKvizTaken: Int, idPitanje: Int, odgovor: Int): Int {
            return withContext(Dispatchers.IO) {
                try {
                    var tacnih = 0
                    var kt = TakeKvizRepository.getPocetiKvizovi()!!.find { it.id == idKvizTaken }
                    var pitanj = PitanjeKvizRepository.getPitanja(kt!!.KvizId)
                    var odgovori = OdgovorRepository.getOdgovoriKviz(kt.KvizId)
                    for (i in 0..pitanj.size - 1) {
                        if (pitanj.get(i).id == idPitanje) {
                            if (odgovor == pitanj.get(i).tacan) tacnih++
                        }
                        for (odg in odgovori) {
                            if (odg.idR == i && odg.odgovoreno == pitanj.get(i).tacan)
                                tacnih++
                        }
                    };
                    var rez = ((tacnih.toFloat() / pitanj.size.toFloat()) * 100f).toInt()
                    println(rez)
                    ApiAdapter.retrofit.postaviOdgovorKviz(
                        AccountRepository.getHash(),
                        idKvizTaken,
                        OdgPom(odgovor, idPitanje, rez)
                    )
                    return@withContext rez
                } catch (e: Exception) {
                    return@withContext -1
                }
            }
        }

        suspend fun postaviOdgovorKviz(idKvizTaken: Int, idPitanje: Int, odgovor: Int): Int {
            return withContext(Dispatchers.IO) {
                var kt = TakeKvizRepository.getPocetiKvizovi()!!.find { it.id == idKvizTaken }
                val dao = AppDatabase.getInstance(context)
                if(dao.odgovorDao().provjeri(kt!!.KvizId, kt!!.id, idPitanje) != 0) return@withContext 50
                var odg = Odgovor(
                    -1,
                    idPitanje,
                    odgovor,
                    idPitanje,
                    kt!!.KvizId,
                    idKvizTaken
                )

                odg.id = dao.odgovorDao().getId()
                dao.odgovorDao().insert(odg)
                var pitanj = PitanjeKvizRepository.getPitanjaBaza(kt.KvizId)
                var tacnih = 0
                var odgovori = getOdgovori(kt.KvizId, kt.id)
                for (i in 0..pitanj.size - 1) {
                    for (odg in odgovori) {
                        if (odg.idP == pitanj.get(i).id && odg.odgovoreno == pitanj.get(i).tacan)
                            tacnih++
                    }
                };
                var rez = ((tacnih.toFloat() / pitanj.size.toFloat()) * 100f).toInt()
                return@withContext (100f/2).toInt()
            }
        }

        suspend fun predajOdgovore(idKviz: Int) {
            return withContext(Dispatchers.IO) {
                val dao = AppDatabase.getInstance(context)
                val odgovori =
                    dao.odgovorDao().getOdgovori(idKviz, TakeKvizRepository.kvizTaken!!.id)
                odgovori.forEach {
                    postaviOdgovorApi(
                        TakeKvizRepository.kvizTaken!!.id,
                        it.idP,
                        it.odgovoreno
                    )
                }
                dao.kvizDao().predajKviz(idKviz, true)
            }
        }

        suspend fun getOdgovori(idKviza: Int, idKvizTaken: Int): List<Odgovor> {
            return withContext(Dispatchers.IO) {
                val dao = AppDatabase.getInstance(context)
                return@withContext dao.odgovorDao().getOdgovori(idKviza, idKvizTaken)
            }
        }

        suspend fun getOdgovori(idKviza: Int): List<OdgRet> {
            return withContext(Dispatchers.IO) {
                var kvizt = TakeKvizRepository.getPocetiKvizovi()
                var kt = kvizt!!.find { it.KvizId == idKviza }
                return@withContext ApiAdapter.retrofit.getOdgovori(
                    AccountRepository.getHash(),
                    kt!!.id
                )
            }
        }

        suspend fun getOdgovoriKviz(idKviza: Int): List<Odgovor> {
            return withContext(Dispatchers.IO) {
                var kvizt = TakeKvizRepository.getPocetiKvizovi()
                var kt = kvizt!!.find { it.KvizId == idKviza }
                return@withContext ApiAdapter.retrofit.getOdgovoriKviz(
                    AccountRepository.getHash(),
                    kt!!.id
                )
            }
        }
    }
}
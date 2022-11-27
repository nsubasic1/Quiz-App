package ba.etf.rma21.projekat.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.MainActivity.Companion.flippajBottomNav
import ba.etf.rma21.projekat.StatusKviza
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.*
import ba.etf.rma21.projekat.data.repositories.DBRepository
import ba.etf.rma21.projekat.data.repositories.GrupaRepository
import ba.etf.rma21.projekat.data.repositories.PredmetIGrupaRepository
import ba.etf.rma21.projekat.data.viewModels.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class FragmentKvizovi : Fragment() {
    private lateinit var spinner: Spinner
    private lateinit var kvizovi: RecyclerView
    private lateinit var kvizoviAdapter: KvizAdapter
    private var kvizListViewModel = KvizListViewModel()
    private var odgovorViewModel = OdgovorViewModel()
    private var pitanjeKvizViewModel = PitanjeKvizViewModel()
    private var takeKvizViewModel = TakeKvizViewModel()
    private val dbViewModel = DBViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_kvizovi, container, false)
        spinner = view.findViewById(R.id.filterKvizova)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.za_spinner,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        kvizovi = view.findViewById(R.id.listaKvizova)
        kvizovi.layoutManager = GridLayoutManager(view.context, 2)
        kvizoviAdapter = KvizAdapter(emptyList()) { kviz -> otvoriKviz(kviz) }
        kvizovi.adapter = kvizoviAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                popuniSpinner()
                kvizListViewModel.setSpinner(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        spinner.setSelection(1)
        popuniSpinner()
        return view
    }

    fun postaviKvizoveSucces(lista: List<Kviz>) {
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                kvizoviAdapter.updateList(lista)
            }
        }
    }

    private fun postaviStanjeKviza(kviz: Kviz) {
        val trenutniDatum: Date = Calendar.getInstance().run { time }
        if (kviz.datumRada != null) {
            kviz.status = StatusKviza.URAĐEN
        } else if (kviz.datumKraj == null && kviz.datumPocetka.after(trenutniDatum)) {
            kviz.status = StatusKviza.BUDUĆI
        } else if (kviz.datumKraj == null && kviz.datumPocetka.before(trenutniDatum)) {
            kviz.status = StatusKviza.AKTIVAN
        } else if (kviz.datumPocetka.before(trenutniDatum) && kviz.datumKraj.after(trenutniDatum)) {
            kviz.status = StatusKviza.AKTIVAN
        } else if (kviz.datumPocetka.after(trenutniDatum)) {
            kviz.status = StatusKviza.BUDUĆI
        } else {
            kviz.status = StatusKviza.NEURAĐEN
        }
    }

    fun apdejt(rez: Boolean) {
        GlobalScope.launch { }
    }

    private fun popuniSpinner() {
        kvizovi.isEnabled = true
        if (spinner.selectedItem.toString() == "Svi moji kvizovi") {
            dbViewModel.updateNow(onSucces = ::apdejt, onError = ::onError)
            kvizListViewModel.dajSveMojeKvizove(
                onSucces = ::postaviKvizoveSucces,
                onError = ::onError
            )
        } else if (spinner.selectedItem.toString() == "Svi kvizovi") {
            kvizListViewModel.dajSveKvizove(onSucces = ::postaviKvizoveSucces, onError = ::onError)
            kvizovi.isEnabled = false
        } else if (spinner.selectedItem.toString() == "Urađeni kvizovi") kvizListViewModel.dajUrađeneKvizove(
            onSucces = ::postaviKvizoveSucces,
            onError = ::onError
        )
        else if (spinner.selectedItem.toString() == "Budući kvizovi") kvizListViewModel.dajBuduceKvizove(
            onSucces = ::postaviKvizoveSucces,
            onError = ::onError
        )
        else kvizListViewModel.dajNeurađeneKvizove(
            onSucces = ::postaviKvizoveSucces,
            onError = ::onError
        )
    }

    private fun otvoriKviz(kviz: Kviz) {
        if (kvizListViewModel.spinner() != 1) {
            dbViewModel.updateNow(onSucces = ::apdejt, onError = ::onError)
            postaviStanjeKviza(kviz)
            pitanjeKvizViewModel.postaviTrenutno(kviz)
            takeKvizViewModel.getPocetniKvizovi(onSucces = ::onSuccesPokusaji, onError = ::onError1)
            pitanjeKvizViewModel.dajPitanja(kviz.id, onSucces = ::onSucces, onError = ::onError1)
        }
    }

    fun onSucces(pitanja: List<Pitanje>) {
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                val transaction = fragmentManager?.beginTransaction()
                transaction?.replace(R.id.container, FragmentPokusaj.newInstance(pitanja))
                transaction?.commit()
                flippajBottomNav(false)
            }
        }
    }

    fun onSuccesOdg(lista: List<Odgovor>) {
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                odgovorViewModel.setOdgovori(lista)
            }
        }
    }

    fun onSuccesPokusaji(lista: List<KvizTaken>?) {
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                println("postavi se1")
                var ima = false;
                if (lista != null) {
                    for (kt in lista)
                        if (kt.KvizId == pitanjeKvizViewModel.dajTrenutni()!!.id) {
                            takeKvizViewModel.setKvizTaken(kt)
                            odgovorViewModel.getOdgovori(
                                pitanjeKvizViewModel.dajTrenutni()!!.id,
                                kt.id,
                                onSucces = ::onSuccesOdg,
                                onError = ::onError2
                            )
                            ima = true
                        }
                }
                if (lista == null || ima == false) {
                    odgovorViewModel.setOdgovori(emptyList())
                    takeKvizViewModel.zapocniKviz(
                        pitanjeKvizViewModel.dajTrenutni()!!.id,
                        onSucces = ::onSuccesKt,
                        onError = ::onError1
                    )
                }
            }
        }
    }

    fun onSuccesKt(kt: KvizTaken) {
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                println("postavi se")
                takeKvizViewModel.setKvizTaken(kt)
            }
        }
    }


    companion object {
        fun newInstance(): FragmentKvizovi = FragmentKvizovi()
    }

    override fun onResume() {
        super.onResume()
        spinner.setSelection(1)
        popuniSpinner()
    }

    fun onError() {
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                println("nesto ne valja0")
            }
        }
    }

    fun onError1() {
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                println("nesto ne valja11")
            }
        }
    }

    fun onError2() {
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                println("nesto ne valja22")
            }
        }
    }

}

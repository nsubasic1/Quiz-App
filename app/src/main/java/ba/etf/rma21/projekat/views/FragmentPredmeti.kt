package ba.etf.rma21.projekat.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.repositories.PredmetRepository.Companion.godina
import ba.etf.rma21.projekat.data.repositories.PredmetRepository.Companion.grupa
import ba.etf.rma21.projekat.data.repositories.PredmetRepository.Companion.predmet
import ba.etf.rma21.projekat.data.viewModels.GrupaListViewmodel
import ba.etf.rma21.projekat.data.viewModels.PredmetIGrupaViewModel
import ba.etf.rma21.projekat.data.viewModels.PredmetListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentPredmeti : Fragment() {
    private lateinit var spinnerGodina: Spinner
    private lateinit var spinnerPredmet: Spinner
    private lateinit var spinnerGrupa: Spinner
    private lateinit var upis: Button
    private val predmetIGrupaViewModel: PredmetIGrupaViewModel = PredmetIGrupaViewModel()
    private var grupaPrvi = true
    private var predmetPrvi = true
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_predmeti, container, false)
        spinnerGodina = view.findViewById(R.id.odabirGodina)
        spinnerPredmet = view.findViewById(R.id.odabirPredmet)
        spinnerGrupa = view.findViewById(R.id.odabirGrupa)
        upis = view.findViewById(R.id.dodajPredmetDugme)
        upis.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (spinnerPredmet.selectedItem != null && spinnerGrupa.selectedItem != null) {
                    predmetIGrupaViewModel.upisiUGrupu((spinnerGrupa.selectedItem as Grupa).id, onSucces = ::onSucces, onError = ::onError)
                    val transaction = fragmentManager?.beginTransaction()
                    transaction?.replace(
                        R.id.container,
                        FragmentPoruka.newInstance("Uspješno ste upisani u grupu " + spinnerGrupa.selectedItem.toString() + " predmeta " + spinnerPredmet.selectedItem.toString() + "!")
                    )
                    transaction?.commit()
                }
            }
        })

        var adapter = ArrayAdapter.createFromResource(
            context!!,
            R.array.godine,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerGodina.adapter = adapter


        spinnerPredmet.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if (spinnerPredmet.selectedItem != null) {
                    popuniGrupe(spinnerPredmet.selectedItem as Predmet)
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        spinnerGodina.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                popuniPredmete(spinnerGodina.selectedItem.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        spinnerGrupa.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        spinnerGodina.setSelection(godina, false)
        return view
    }

    private fun onSucces(b: Boolean) {
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
            }
        }
    }

    fun onSuccesPredmeti(lista: List<Predmet>) {
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                var adapter = ArrayAdapter<Predmet>(
                    context!!,
                    android.R.layout.simple_spinner_item,
                    lista
                )
                adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
                spinnerPredmet.adapter = adapter
                if (lista.size == 0) popuniGrupe(null)
                if (predmetPrvi) {
                    spinnerPredmet.setSelection(predmet)
                    predmetPrvi = false
                }
            }
        }
    }

    fun onError() {
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                println("nesto nije ok")
            }
        }
    }

    private fun popuniPredmete(ime: String) {
        predmetIGrupaViewModel.getNeupisanePredmeteNaGodini(
            ime.toInt(),
            onSucces = ::onSuccesPredmeti,
            onError = ::onError
        )
    }

    fun onSuccesGrupe(lista: List<Grupa>) {
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                val adapter = ArrayAdapter<Grupa>(
                    context!!,
                    android.R.layout.simple_spinner_item,
                    lista
                )

                adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
                spinnerGrupa.adapter = adapter
                if (grupaPrvi) {
                    spinnerGrupa.setSelection(grupa)
                    grupaPrvi = false
                }
            }
        }
    }

    private fun popuniGrupe(selectedItem: Predmet?) {
        if (selectedItem != null) predmetIGrupaViewModel.getGrupeZaPredmet(selectedItem.id, onSucces = ::onSuccesGrupe, onError = ::onError)
        else{
            val adapter = ArrayAdapter<Grupa>(
                context!!,
                android.R.layout.simple_spinner_item,
                emptyList()
            )

            adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
            spinnerGrupa.adapter = adapter
            if (grupaPrvi) {
                spinnerGrupa.setSelection(grupa)
                grupaPrvi = false
            }
        }
    }

    override fun onPause() {
        super.onPause()
        godina = spinnerGodina.selectedItemPosition
        predmet = -1
        if (spinnerPredmet.selectedItem != null) predmet = spinnerPredmet.selectedItemPosition
        grupa = -1
        if (spinnerGrupa.selectedItem != null) grupa = spinnerGrupa.selectedItemPosition
    }

    companion object {
        fun newInstance(): FragmentPredmeti = FragmentPredmeti()
    }
}
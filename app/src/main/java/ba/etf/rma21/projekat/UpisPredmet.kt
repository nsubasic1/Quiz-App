package ba.etf.rma21.projekat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import ba.etf.rma21.projekat.data.viewModels.GrupaListViewmodel
import ba.etf.rma21.projekat.data.viewModels.PredmetListViewModel
import ba.etf.rma21.projekat.data.models.Grupa

class UpisPredmet : AppCompatActivity() {
    private lateinit var spinnerGodina : Spinner
    private lateinit var spinnerPredmet : Spinner
    private lateinit var spinnerGrupa : Spinner
    private lateinit var upis :Button
    private val grupaListViewmodel : GrupaListViewmodel =
        GrupaListViewmodel()
    private val predmetListViewModel : PredmetListViewModel =
        PredmetListViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upis_predmet)
        spinnerGodina = findViewById(R.id.odabirGodina)
        spinnerPredmet = findViewById(R.id.odabirPredmet)
        spinnerGrupa = findViewById(R.id.odabirGrupa)
        upis = findViewById(R.id.dodajPredmetDugme)
        upis.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if(spinnerPredmet.selectedItem != null && spinnerGrupa.selectedItem != null) {
                    predmetListViewModel.dajPredmet(spinnerPredmet.selectedItem.toString()).upisan = true
                    grupaListViewmodel.dajGrupu(spinnerGrupa.selectedItem.toString(), spinnerPredmet.selectedItem.toString()).upisan = true
                    finish()
                }
            }
        })
        ArrayAdapter.createFromResource(
            this,
            R.array.godine,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerGodina.adapter = adapter
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


    }

    private fun popuniPredmete(ime: String) {
        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            predmetListViewModel.dajNeupisanePredmeteNaGodini(ime.toInt()).map{it.naziv}
        )
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spinnerPredmet.adapter = adapter
        spinnerPredmet.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if(spinnerPredmet.selectedItem != null)
                popuniGrupe(spinnerPredmet.selectedItem.toString())
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        if(predmetListViewModel.dajNeupisanePredmeteNaGodini(ime.toInt()).size == 0) popuniGrupe("")
    }

    private fun popuniGrupe(selectedItem: String) {
        var lista = emptyList<String>()
        if(selectedItem != "") lista = grupaListViewmodel.dajGrupeNaPredmetu(selectedItem).map { it.naziv }
        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            lista
        )
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spinnerGrupa.adapter = adapter
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
    }
}
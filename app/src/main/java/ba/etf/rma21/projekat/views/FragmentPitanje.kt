package ba.etf.rma21.projekat.views

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.StatusKviza
import ba.etf.rma21.projekat.data.models.OdgRet
import ba.etf.rma21.projekat.data.models.Odgovor
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.viewModels.OdgovorViewModel
import ba.etf.rma21.projekat.data.viewModels.PitanjeKvizViewModel
import ba.etf.rma21.projekat.data.viewModels.TakeKvizViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FragmentPitanje(val pitanje : Pitanje) : Fragment(){
    private lateinit var tekst : TextView
    private lateinit var odgovori : ListView
    private val odgovorViewModel = OdgovorViewModel()
    private val pitanjeKvizViewModel = PitanjeKvizViewModel()
    private val takeKvizViewModel = TakeKvizViewModel()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view =  inflater.inflate(R.layout.fragment_pitanje, container, false)
        tekst = view.findViewById(R.id.tekstPitanja)
        odgovori = view.findViewById(R.id.odgovori)
        tekst.setText(pitanje.tekstPitanja)
        odgovori.adapter =  ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                pitanje.opcije
        )
        odgovori.setOnItemClickListener { parent, view, position, id ->
            klikni(position)
            println("upise se odgovor")
            odgovorViewModel.dodajOdgovorBaza(position, pitanje.id, takeKvizViewModel.getKvizTaken()!!.id, onSucces = ::dodajLokalno, onError = ::onError)
            odgovorViewModel.dodajOdgovor(Odgovor(pitanje.id, pitanje.id, position, pitanje.id, takeKvizViewModel.getKvizTaken()!!.KvizId, takeKvizViewModel.getKvizTaken()!!.id))
        }
        Handler().postDelayed(
                {
                    println("odgovor je " + dajOdgovor())
                    if(dajOdgovor() != -1 && dajOdgovor() != -2){
                    klikni(dajOdgovor())
        }
                else if(dajOdgovor() == -2)
                    klikni(pitanje.tacan)
                }, 1)
        return  view
    }

    fun dodajLokalno(i: Int) {
        GlobalScope.launch {  }
    }

    fun onError() {
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                println("ne valja u pitanju")
            }
        }
    }

    fun onSucces(int : Int) {
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
            }
        }
    }


    fun dajOdgovor() : Int{
            for(odg in odgovorViewModel.dajOdgovore()){
                if(odg.idP == pitanje.id){
                    if(odg.odgovoreno == pitanje.opcije.size) return -2
                    return odg.odgovoreno
                }
            }
            return -1
    }

    private fun klikni(position : Int){
        if ( position == pitanje.tacan) {
            odgovori.getChildAt(position).setBackgroundColor(Color.parseColor("#3DDC84"))
        } else{
            odgovori.getChildAt(pitanje.tacan).setBackgroundColor(Color.parseColor("#3DDC84"))
            odgovori.getChildAt(position).setBackgroundColor(Color.parseColor("#DB4F3D"))
        }
        for (i in 0..pitanje.opcije.size - 1) {
            odgovori.getChildAt(i).isEnabled = false
            odgovori.getChildAt(i).setOnClickListener(null)
        }
    }

    companion object {
        fun newInstance(pitanje : Pitanje): FragmentPitanje = FragmentPitanje(pitanje)
    }
}
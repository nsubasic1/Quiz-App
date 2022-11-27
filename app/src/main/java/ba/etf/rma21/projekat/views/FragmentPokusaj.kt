package ba.etf.rma21.projekat.views

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.*
import android.widget.FrameLayout
import androidx.core.view.get
import androidx.core.view.iterator
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.StatusKviza
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.data.viewModels.PitanjeKvizViewModel
import ba.etf.rma21.projekat.MainActivity.Companion.bottomNav
import ba.etf.rma21.projekat.MainActivity.Companion.flippajBottomNav
import ba.etf.rma21.projekat.data.models.OdgRet
import ba.etf.rma21.projekat.data.models.Odgovor
import ba.etf.rma21.projekat.data.viewModels.OdgovorViewModel
import ba.etf.rma21.projekat.data.viewModels.TakeKvizViewModel
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.*
import java.util.*


class FragmentPokusaj(val pitanja : List<Pitanje>) : Fragment() {
    private lateinit var navPitanja : NavigationView
    private lateinit var framePitanja : FrameLayout
    private var trenutno = "1"
    private val pitanjeKvizViewModel = PitanjeKvizViewModel()
    private val takeKvizViewModel = TakeKvizViewModel()
    private val odgovorViewModel = OdgovorViewModel()
    private var zavrsen = false
    private var odgovori = emptyList<Odgovor>()
    private val mOnNavigationItemReselectedListener = NavigationView.OnNavigationItemSelectedListener { item ->
        if(item.toString() != "Rezultat") {
            if(zavrsen == false && zavrsenKviz()){
                odgovorViewModel.predajOdgovore(pitanjeKvizViewModel.dajTrenutni()!!.id, onSucces = ::onSucces, onError = ::onError)
                zavrsen = true
            }
        pitanjeKvizViewModel.setPitanje(pitanja.get(item.toString().toInt() - 1).id)
        if (trenutno != "" && dajOdgovor(trenutno.toInt() - 1) != -1) {
                if (dajOdgovor(trenutno.toInt() - 1) == pitanja.get(trenutno.toInt() - 1).tacan){
                    val spanString = SpannableString(navPitanja.menu.getItem(trenutno.toInt() - 1) .getTitle().toString())
                        spanString.setSpan(ForegroundColorSpan(Color.parseColor("#3DDC84")), 0, spanString.length, 0)
                    navPitanja.menu.getItem(trenutno.toInt() - 1) .setTitle(spanString)
                }
                else {
                    val spanString = SpannableString(navPitanja.menu.getItem(trenutno.toInt() - 1) .getTitle().toString())
                    spanString.setSpan(ForegroundColorSpan(Color.parseColor("#DB4F3D")), 0, spanString.length, 0)
                    navPitanja.menu.getItem(trenutno.toInt() - 1) .setTitle(spanString)
                }
            }
            flippajBottomNav(false)
            val pit = pitanja.get(item.toString().toInt() - 1)
            trenutno = item.toString()
            val transaction = childFragmentManager?.beginTransaction()
            transaction?.replace(R.id.framePitanje, FragmentPitanje.newInstance(pit))
            transaction?.commit()
        }
        false
    }

    fun onSucces(int : Int) {
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
            }
        }
    }

    fun onSucces() {
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view =  inflater.inflate(R.layout.fragment_pokusaj, container, false)
        navPitanja = view.findViewById(R.id.navigacijaPitanja)
        framePitanja = view.findViewById(R.id.framePitanje)
        if(pitanja.size > 0){
            for(i in 1 .. pitanja.size) navPitanja.menu.add(123456, i - 1, i - 1, i.toString())
            navPitanja.menu.get(0).setChecked(true)
            val transaction = childFragmentManager?.beginTransaction()
            transaction?.replace(R.id.framePitanje, FragmentPitanje.newInstance(pitanja.get(0)))
            transaction?.commit()
            trenutno = "1"
        }

        zavrsen = false
        navPitanja.setNavigationItemSelectedListener(mOnNavigationItemReselectedListener)

        pitanjeKvizViewModel.setPitanje(pitanja.get(0).id)


        bottomNav.menu.getItem(2).setOnMenuItemClickListener { item ->
            if(pitanjeKvizViewModel.dajStatusTrenutnog() == StatusKviza.AKTIVAN){
                dodajRezultat()
            }
            println("oboji se dodatno")
            for(item in navPitanja.menu){
                if(item.toString() != "Rezultat"){
                    val spanString = SpannableString(item.getTitle().toString())
                    if(dajOdgovor(item.toString().toInt() - 1) == -1 || dajOdgovor(item.toString().toInt() - 1) != pitanja.get(item.toString().toInt() - 1).tacan) {
                        spanString.setSpan(
                            ForegroundColorSpan(Color.parseColor("#DB4F3D")),
                            0,
                            spanString.length,
                            0
                        )
                        println("oboji se dodatno")
                        if (dajOdgovor(item.toString().toInt() - 1) == -1) {
                            odgovorViewModel.dodajOdgovor(
                                Odgovor(
                                    pitanja.get(item.toString().toInt() - 1).id,
                                    pitanja.get(item.toString().toInt() - 1).id,
                                    pitanja.get(item.toString().toInt() - 1).opcije.size,
                                pitanja.get(item.toString().toInt() - 1).id,
                                    takeKvizViewModel.getKvizTaken()!!.KvizId,
                                    takeKvizViewModel.getKvizTaken()!!.id
                            )
                            )
                            odgovorViewModel.dodajOdgovorBaza(pitanja.get(item.toString().toInt() - 1).opcije.size, pitanja.get(item.toString().toInt() - 1).id, takeKvizViewModel.getKvizTaken()!!.id, onSucces = ::dodajLokalno, onError = ::onError)
                        }
                    }
                    else
                        spanString.setSpan(ForegroundColorSpan(Color.parseColor("#3DDC84")), 0, spanString.length, 0)
                    item.setTitle(spanString)
                }
            }
            odgovorViewModel.predajOdgovore(pitanjeKvizViewModel.dajTrenutni()!!.id, onSucces = ::onSucces, onError = ::onError)
            zavrsen = true
            val transaction = childFragmentManager?.beginTransaction()
            transaction?.replace(R.id.framePitanje, FragmentPoruka.newInstance("Završili ste kviz " + pitanjeKvizViewModel.dajTrenutni()!!.naziv + " sa tačnosti " + izracunajProcenat() + "%"))
            transaction?.commit()
            flippajBottomNav(true)
            false
        }


        if(pitanjeKvizViewModel.dajStatusTrenutnog() != StatusKviza.AKTIVAN)
            bottomNav.menu.getItem(2).isEnabled = false
        else
            bottomNav.menu.getItem(2).isEnabled = true

        bottomNav.menu.getItem(3).setOnMenuItemClickListener { item ->
            if(zavrsen == false && zavrsenKviz()){
                val odgovori = odgovorViewModel.dajOdgovore()
                for(odgovor in odgovori)
                    odgovorViewModel.postaviOdgovorKviz(
                        takeKvizViewModel.getKvizTaken()!!.id,
                        odgovor.idP,
                        odgovor.odgovoreno,
                        onSucces = ::onSucces,
                        onError = ::onError
                    )
            }
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.container, FragmentKvizovi.newInstance())
            flippajBottomNav(true)
            transaction?.commit()
            false
        }

            for(item in navPitanja.menu){
                println("boji se")
                val spanString = SpannableString(item.getTitle().toString())
                if(dajOdgovor(item.toString().toInt() - 1) == -1) continue
                else if(dajOdgovor(item.toString().toInt() - 1) != pitanja.get(item.toString().toInt() - 1).tacan || dajOdgovor(item.toString().toInt() - 1) == -2)
                    spanString.setSpan(ForegroundColorSpan(Color.parseColor("#DB4F3D")), 0, spanString.length, 0)
                else
                    spanString.setSpan(ForegroundColorSpan(Color.parseColor("#3DDC84")), 0, spanString.length, 0)
                item.setTitle(spanString)
            }
            if(pitanjeKvizViewModel.dajStatusTrenutnog() != StatusKviza.AKTIVAN){
            dodajRezultat()
        }
        return  view
    }


    fun dajOdgovor(indeks : Int) : Int{
        val odgovori = odgovorViewModel.dajOdgovore()
        for(odg in odgovori){
            if(odg.idP == pitanja.get(indeks).id){
                if(odg.odgovoreno == pitanja.get(indeks).opcije.size) return -2
                return odg.odgovoreno
            }
        }
        return -1
    }

    fun onError() {
        GlobalScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                println("ne valja u pokusaju")
            }
        }
    }

    private fun dodajRezultat(){
        navPitanja.menu.add(123456, pitanja.size, pitanja.size, "Rezultat")
        navPitanja.menu.getItem(pitanja.size).setOnMenuItemClickListener { item ->
            val transaction = childFragmentManager?.beginTransaction()
            transaction?.replace(R.id.framePitanje, FragmentPoruka.newInstance("Završili ste kviz " + pitanjeKvizViewModel.dajTrenutni()!!.naziv + " sa tačnosti " + izracunajProcenat() + "%"))
            transaction?.commit()
            flippajBottomNav(true)
            false
        }
    }

    private fun zavrsenKviz(): Boolean{
        for(i in 0..pitanja.size - 1)
            if(dajOdgovor(i) == -1) return false
        return true
    }
    fun dodajLokalno(i: Int) {
        GlobalScope.launch {  }
    }
    private fun izracunajProcenat() : Float{
        var tacnih = 0
        for(i in 0..pitanja.size-1) if(pitanja.get(i).tacan == dajOdgovor(i)) tacnih++;
        return (tacnih.toFloat() / pitanja.size.toFloat()) * 100f
    }

    companion object {
        fun newInstance(pitanja : List<Pitanje>): FragmentPokusaj = FragmentPokusaj(pitanja)
    }
}



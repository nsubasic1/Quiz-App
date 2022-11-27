package ba.etf.rma21.projekat

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.Account
import ba.etf.rma21.projekat.data.models.Odgovor
import ba.etf.rma21.projekat.data.repositories.*
import ba.etf.rma21.projekat.data.viewModels.AccountViewModel
import ba.etf.rma21.projekat.views.FragmentKvizovi
import ba.etf.rma21.projekat.views.FragmentPredmeti
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    companion object{
        lateinit var bottomNav : BottomNavigationView
        fun flippajBottomNav(pocetno : Boolean){
            if(pocetno){
                bottomNav.menu.findItem(R.id.predajKviz).setVisible(false)
                bottomNav.menu.findItem(R.id.zaustaviKviz).setVisible(false)
                bottomNav.menu.findItem(R.id.kvizovi).setVisible(true)
                bottomNav.menu.findItem(R.id.predmeti).setVisible(true)
            }
            else{
                bottomNav.menu.findItem(R.id.predajKviz).setVisible(true)
                bottomNav.menu.findItem(R.id.zaustaviKviz).setVisible(true)
                bottomNav.menu.findItem(R.id.kvizovi).setVisible(false)
                bottomNav.menu.findItem(R.id.predmeti).setVisible(false)
            }
        }
    }

    private lateinit var container : FrameLayout
    private val mOnNavigationItemReselectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.kvizovi -> {
                openFragment(FragmentKvizovi.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.predmeti -> {
                println("predmeti ")
                openFragment(FragmentPredmeti.newInstance())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNav = findViewById(R.id.bottomNav)
        bottomNav.setOnNavigationItemSelectedListener(mOnNavigationItemReselectedListener)
        bottomNav.selectedItemId = R.id.kvizovi
        bottomNav.menu.findItem(R.id.predajKviz).setVisible(false)
        bottomNav.menu.findItem(R.id.zaustaviKviz).setVisible(false)

        container = findViewById(R.id.container)
        AccountRepository.setContext(applicationContext)
        OdgovorRepository.setContext(applicationContext)
        KvizRepository.setContext(applicationContext)
        PitanjeKvizRepository.setContext(applicationContext)
        DBRepository.setContext(applicationContext)
        openFragment(FragmentKvizovi.newInstance())
        val payload = intent?.getStringExtra("payload")
        val accountViewModel = AccountViewModel()
        println("uspjelo zapocinjanje")
        if(payload == null){
            accountViewModel.upisiHash("9003419d-5e5b-46b3-87bc-b4eb38cbbfb7", onSucces = ::onSucces, onError = ::onError)
        }

        else
        accountViewModel.upisiHash(payload!!, onSucces = ::onSucces, onError = ::onError)
    }


    fun onSucces(rez : Boolean){
        GlobalScope.launch {
            println("uspjelo zapocinjanje")
            }
    }

    fun onError(){
        GlobalScope.launch {
            println("ne valja pocetak")
        }
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
        flippajBottomNav(true)
    }


    override fun onBackPressed() {
        openFragment(FragmentKvizovi.newInstance())
//        super.onBackPressed()
    }
}


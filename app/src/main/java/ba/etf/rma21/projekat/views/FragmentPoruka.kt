package ba.etf.rma21.projekat.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.R

class FragmentPoruka : Fragment() {
    private lateinit var poruka : TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_poruka, container, false)
        poruka = view.findViewById(R.id.tvPoruka)
        arguments?.getString("por")?.let{
            poruka.setText(it)
        }
        return  view
    }
    companion object {
        fun newInstance(por:String): FragmentPoruka = FragmentPoruka()
            .apply {
                arguments = Bundle().apply {
                    putString("por", por)
                }
            }
    }
}
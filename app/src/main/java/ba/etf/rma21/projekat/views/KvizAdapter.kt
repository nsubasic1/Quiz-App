package ba.etf.rma21.projekat.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.StatusKviza
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.repositories.PredmetRepository
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class KvizAdapter(
        private var kvizovi : List<Kviz>,
        private val onItemClicked : (kviz: Kviz) -> Unit
) : RecyclerView.Adapter<KvizAdapter.KvizViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KvizViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_kviz, parent, false)
        return KvizViewHolder(view)
    }
    override fun getItemCount() : Int = kvizovi.size

    var status : StatusKviza? = null
    override fun onBindViewHolder(holder: KvizViewHolder, position: Int) {
        holder.nazivKviza.text = kvizovi[position].nazivPredmeta;
        holder.nazivPredmeta.text = kvizovi[position].naziv
        holder.trajanjeKviza.text  = kvizovi[position].trajanje.toString() + " min"
        holder.bodoviKviza.text = ""
        postaviDatum(holder, kvizovi[position])
        postaviStanjeKviza(holder, kvizovi[position])

        holder.itemView.setOnClickListener { onItemClicked(kvizovi[position]) }
        if(status == StatusKviza.BUDUĆI) holder.itemView.isClickable = false
    }

    fun updateList(lista : List<Kviz>){
        this.kvizovi = lista
        notifyDataSetChanged()
    }

    fun dajFormatirani(datum : Date) : String {
        var format = SimpleDateFormat("dd.MM.yyyy")
        return format.format(datum)
    }

    private fun postaviDatum(holder:KvizViewHolder, kviz: Kviz){
        val format = SimpleDateFormat("dd.MM.yyyy")
        val current =   Calendar.getInstance().run { time }
        if( kviz.datumKraj==null) holder.datumKviza.text = format.format(kviz.datumPocetka)
        else if(kviz.datumRada!=null) holder.datumKviza.text = format.format(kviz.datumRada)
        else if(kviz.datumPocetka.before(current) && kviz.datumKraj.after(current))  holder.datumKviza.text = format.format(kviz.datumKraj)
        else if(kviz.datumPocetka.after(current)) holder.datumKviza.text = format.format(kviz.datumPocetka)
        else if(kviz.datumKraj.before(current)) holder.datumKviza.text = format.format(kviz.datumKraj)
    }

    private fun postaviStanjeKviza(holder: KvizViewHolder,kviz : Kviz){
        val trenutniDatum : Date = Calendar.getInstance().run { time }
        if(kviz.datumRada!=null)  {
            holder.sliba.setImageResource(R.drawable.plava)
            kviz.status = StatusKviza.URAĐEN
        }
        else if(kviz.datumKraj==null && kviz.datumPocetka.after(trenutniDatum))  {
            holder.sliba.setImageResource(R.drawable.zuta)
            kviz.status = StatusKviza.BUDUĆI
        }
        else if(kviz.datumKraj==null && kviz.datumPocetka.before(trenutniDatum))  {
            kviz.status = StatusKviza.AKTIVAN
            holder.sliba.setImageResource(R.drawable.zelena)
        }
        else if(kviz.datumPocetka.before(trenutniDatum) && kviz.datumKraj.after(trenutniDatum)) {
            kviz.status = StatusKviza.AKTIVAN
            holder.sliba.setImageResource(R.drawable.zelena)
        }
        else if(kviz.datumPocetka.after(trenutniDatum)) {
            kviz.status = StatusKviza.BUDUĆI
            holder.sliba.setImageResource(R.drawable.zuta)
        }
        else {
            kviz.status = StatusKviza.NEURAĐEN
            holder.sliba.setImageResource(R.drawable.crvena)
        }
    }


    inner class KvizViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val sliba : ImageView = itemView.findViewById(R.id.sliba)
        val nazivPredmeta : TextView = itemView.findViewById(R.id.nazivKviza)
        val nazivKviza : TextView = itemView.findViewById(R.id.nazivPredmeta)
        val datumKviza : TextView = itemView.findViewById(R.id.datum)
        val trajanjeKviza : TextView = itemView.findViewById(R.id.trajanje)
        val bodoviKviza : TextView = itemView.findViewById(R.id.bodovi)
    }
}
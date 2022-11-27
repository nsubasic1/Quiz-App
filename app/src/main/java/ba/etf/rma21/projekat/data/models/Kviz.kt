package ba.etf.rma21.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import ba.etf.rma21.projekat.StatusKviza
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity
class Kviz(@PrimaryKey @SerializedName("id") var id: Int,
           @ColumnInfo(name = "naziv") @SerializedName("naziv") var naziv: String,
           @ColumnInfo(name = "datumPocetka") var datumPocetkaB : String,
           @Ignore @SerializedName("datumPocetak") var datumPocetka: Date,
           @ColumnInfo(name = "datumKraj") var datumKrajB : String,
           @Ignore @SerializedName("datumKraj") var datumKraj: Date,
           @ColumnInfo(name = "trajanje") @SerializedName("trajanje") var trajanje: Int,
           @ColumnInfo(name = "predan") var predan : Boolean,
           @ColumnInfo(name = "datumRada") var datumRada : String
) {
    var nazivPredmeta = ""
    var nazivGrupe = ""
    var status = StatusKviza.AKTIVAN
    constructor(): this(
        id = -1, naziv = "", datumPocetkaB = "", datumPocetka = Calendar.getInstance().time, datumKrajB = "", datumKraj = Calendar.getInstance().time, trajanje = -1, predan = false, datumRada = ""
    )
//    init {
//        if(datumRada != null && osvojeniBodovi != null)  status = StatusKviza.URAĐEN
//        else if(datumPocetka.after(Calendar.getInstance().time)) status = StatusKviza.BUDUĆI
//        else if(datumKraj.before(Calendar.getInstance().time) && datumRada == null && osvojeniBodovi == null) status = StatusKviza.NEURAĐEN
//        else status = StatusKviza.AKTIVAN
//    }
//
//    fun updateStatus(procenat : Float, datum : Date){
//        osvojeniBodovi = procenat
//        datumRada = datum
//        if(datumRada != null && osvojeniBodovi != null)  status = StatusKviza.URAĐEN
//        else if(datumPocetka.after(Calendar.getInstance().time)) status = StatusKviza.BUDUĆI
//        else if(datumKraj.before(Calendar.getInstance().time) && datumRada == null && osvojeniBodovi == null) status = StatusKviza.NEURAĐEN
//        else status = StatusKviza.AKTIVAN
//    }
}
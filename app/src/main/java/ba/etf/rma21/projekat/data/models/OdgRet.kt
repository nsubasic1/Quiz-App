package ba.etf.rma21.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

class OdgRet(@SerializedName("id") val id : Int,
             @SerializedName("odgovoreno") var odgovoreno : Int,
             @SerializedName("PitanjeId") var idP : Int) {
}
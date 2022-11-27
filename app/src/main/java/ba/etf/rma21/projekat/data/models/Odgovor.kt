package ba.etf.rma21.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
class Odgovor(
    @PrimaryKey var id : Int,
    @Ignore @SerializedName("id") val idR : Int,
    @ColumnInfo(name = "odgovoreno") @SerializedName("odgovoreno") var odgovoreno : Int,
    @ColumnInfo(name = "PitanjeId") @SerializedName("PitanjeId") var idP : Int,
    @ColumnInfo(name = "KvizId") var kvizId : Int,
    @ColumnInfo(name = "KvizTakenId") var kvizTakenId : Int
) {
    constructor()  : this(
        id = -1, idR = -1, odgovoreno = -1, idP = -1, kvizId = -1, kvizTakenId = -1
    )
}
package ba.etf.rma21.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
class Pitanje(
    @PrimaryKey var idB : Int,
    @SerializedName("id") var id : Int,
    @ColumnInfo(name = "naziv") @SerializedName("naziv") var naziv : String,
    @ColumnInfo(name = "tekstPitanja") @SerializedName("tekstPitanja") var tekstPitanja : String,
    @Ignore @SerializedName("opcije") var opcije : List<String>,
    @ColumnInfo(name = "opcije") var opcijeB : String,
    @ColumnInfo(name = "tacan") @SerializedName("tacan") var tacan : Int,
    @ColumnInfo(name = "kvizId") var kvizId : Int) {

    constructor() : this(
        idB = -1,
        id = -1,
        naziv = "", tekstPitanja = "",
        opcije = emptyList<String>(),
        opcijeB = "",
        tacan = -1,
        kvizId = -1
    )
}

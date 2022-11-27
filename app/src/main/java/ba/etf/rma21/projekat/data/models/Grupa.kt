package ba.etf.rma21.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
class Grupa(
    @PrimaryKey @SerializedName("id") val id : Int,
    @ColumnInfo(name = "naziv") @SerializedName("naziv") val naziv: String,
    @ColumnInfo(name = "PredmetId") @SerializedName("PredmetId") var pId : Int? = null
) {
    var upisan : Boolean = false
    override fun toString(): String {
        return naziv
    }

    override fun equals(other: Any?): Boolean {
        return id.equals((other as Grupa).id)
    }
}
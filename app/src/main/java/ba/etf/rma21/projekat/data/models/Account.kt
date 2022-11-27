package ba.etf.rma21.projekat.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity
class Account(
    @PrimaryKey @SerializedName("id") val id : Int,
    @ColumnInfo(name = "student") @SerializedName("student") val student : String,
    @ColumnInfo(name = "acHash") @SerializedName("acHash") val hash : String,
    @ColumnInfo(name = "lastUpdate") val lastUpdate : String
) {

}
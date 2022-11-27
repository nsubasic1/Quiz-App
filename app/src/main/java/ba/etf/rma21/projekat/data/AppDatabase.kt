package ba.etf.rma21.projekat.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ba.etf.rma21.projekat.data.dao.*
import ba.etf.rma21.projekat.data.models.*

@Database(entities = arrayOf(Account::class, Grupa::class, Predmet::class, Kviz::class, Pitanje::class, Odgovor::class), version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun accountDao() : AccountDAO
    abstract fun pitanjeDao() : PitanjeDAO
    abstract fun predmetDao() : PredmetDAO
    abstract fun grupaDao() : GrupaDAO
    abstract fun kvizDao() : KvizDAO
    abstract fun odgovorDao() : OdgovorDAO
    companion object{
        
        private var instance : AppDatabase? = null

        fun setInstance(appdb:AppDatabase):Unit{
            instance=appdb
        }

        fun getInstance(context : Context) : AppDatabase{
            if(instance == null){
                synchronized(AppDatabase::class){
                    instance = buildRoomDB(context)
                }
            }
            return instance!!
        }

        private fun buildRoomDB(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "RMA21DB"
            ).build()
        }
}
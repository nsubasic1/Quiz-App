package ba.etf.rma21.projekat

import android.os.Message
import ba.etf.rma21.projekat.data.models.*
import retrofit2.http.*
import java.util.*

interface Api {
    @GET("kviz/{id}/pitanja")
    suspend fun getPitanja(@Path("id") idKviza : Int) : List<Pitanje>

    @POST("/student/{id}/kviz/{kid}")
    suspend fun zapocniKviz(@Path("kid") idKviza : Int, @Path("id") id : String): KvizTaken

    @GET("/student/{id}/kviztaken")
    suspend fun getPocetiKvizovi(@Path("id") id : String):List<KvizTaken>

    @GET("/student/{id}/kviztaken/{ktid}/odgovori")
    suspend fun getOdgovori(@Path("id") id : String, @Path("ktid") idKvizTaken:Int):List<OdgRet>

    @GET("/student/{id}/kviztaken/{ktid}/odgovori")
    suspend fun getOdgovoriKviz(@Path("id") id : String, @Path("ktid") idKvizTaken:Int):List<Odgovor>

    //vidjeti poslije
    @POST("/student/{id}/kviztaken/{ktid}/odgovor")
    suspend fun postaviOdgovorKviz(@Path("id") id : String, @Path("ktid") idKvizTaken:Int, @Body odg : OdgPom):Odgovor

    @GET("/kviz")
    suspend fun getAll():List<Kviz>

    @GET("kviz/{id}")
    suspend fun getById(@Path("id") id:Int):Kviz

    @GET("/grupa/{id}/kvizovi")
    suspend fun dajKvizoveNaGrupi(@Path("id") idGrupa : Int) : List<Kviz>

    @GET("/student/{id}/grupa")
    suspend fun getUpisaneGrupe(@Path("id") id : String):List<Grupa>

    @GET ("/predmet")
    suspend fun getPredmeti():List<Predmet>

    @GET("/grupa")
    suspend fun getGrupe():List<Grupa>

    @GET("/predmet/{id}/grupa")
    suspend fun getGrupeZaPredmet(@Path("id") idPredmeta:Int):List<Grupa>

    @POST("/grupa/{gid}/student/{id}")
    suspend fun upisiUGrupu(@Path("gid") idGrupa:Int, @Path("id") id : String):Mesedz

    @GET("/predmet/{id}")
    suspend fun getPredmetById(@Path("id") id : Int):Predmet

    @GET("/kviz/{id}/grupa")
    suspend fun getGrupeZaKviz(@Path("id") id : Int) : List<Grupa>

    @GET("/account/{id}/lastUpdate?date=")
    suspend fun getLastUpdate(@Path("id") id : String, @Query("date") datum : String) : Promjena
}
package ba.etf.rma21.projekat.data.repositories

class ApiConfig {
    fun getUrl(): String {
        return baseURL
    }
    fun postaviBaseURL(basUrl:String):Unit{
        baseURL = basUrl
    }
    companion object{
        var baseURL = "https://rma21-etf.herokuapp.com"
    }

}
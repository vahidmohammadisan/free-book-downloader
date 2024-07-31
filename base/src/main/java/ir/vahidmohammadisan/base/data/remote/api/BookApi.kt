package ir.vahidmohammadisan.base.data.remote.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookApi {

    @GET("search.php")
    suspend fun getBooks(
        @Query("req") keyword: String,
        @Query("page") page: Int,
        @Query("phrase") phrase: Int = 1,
        @Query("view") view: String = "simple",
        @Query("column") column: String = "def",
        @Query("sort") sort: String = "def",
        @Query("sortmode") sortmode: String = "ASC"
    ): Response<ResponseBody>

}

package ir.vahidmohammadisan.base.data.remote.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookDetailsApi {

    @GET("/main/{md5}")
    suspend fun getBookDetails(
        @Path("md5") md5: String,
    ): Response<ResponseBody>

}

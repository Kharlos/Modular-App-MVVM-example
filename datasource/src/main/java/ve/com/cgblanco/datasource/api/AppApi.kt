package ve.com.cgblanco.datasource.api

import retrofit2.Call
import retrofit2.http.GET
import ve.com.cgblanco.datasource.data.model.user.User

interface AppApi {

    @GET("bins/tws00")
    fun signIn(): Call<User>
}
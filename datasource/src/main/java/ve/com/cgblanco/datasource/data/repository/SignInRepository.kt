package ve.com.cgblanco.datasource.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.app.network.NetworkClient
import com.app.network.cache.Listener
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ve.com.cgblanco.datasource.api.AppApi
import ve.com.cgblanco.datasource.data.model.user.User
import ve.com.cgblanco.datasource.data.model.user.UserPersist
import javax.inject.Inject

class SignInRepository@Inject constructor(
    private val networkClient: NetworkClient<AppApi>,
    val userPersist: UserPersist
) {

    fun signIn (listener: Listener<User>): LiveData<User> {

        networkClient.getRetrofitService(AppApi::class.java).signIn().enqueue(object :
            Callback<User> {
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("response_posts", t.message)
                listener.onError()
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                response.body()?.let {
                    listener.onResponse(response = it)
                    GlobalScope.launch {
                        userPersist.saveItem(it)
                    }
                }
            }
        })

        return userPersist.load()

    }
}
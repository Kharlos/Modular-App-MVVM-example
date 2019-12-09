package ve.com.cgblanco.featuresignin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import android.view.View
import com.app.network.cache.Listener
import ve.com.cgblanco.datasource.data.model.user.User
import ve.com.cgblanco.datasource.data.repository.SignInRepository
import ve.com.cgblanco.featuresignin.model.LoginUser
import javax.inject.Inject


class SignInViewModel @Inject constructor(val userRepository: SignInRepository) : ViewModel() {
    var EmailAddress = MutableLiveData<String>()
    var Password = MutableLiveData<String>()

    private var userMutableLiveData: MutableLiveData<LoginUser>? = null

    private var user: MutableLiveData<User> = MutableLiveData<User>()

    fun validateUser(): MutableLiveData<LoginUser> {

        if (userMutableLiveData == null) {
            userMutableLiveData = MutableLiveData<LoginUser>()
        }
        return userMutableLiveData as MutableLiveData<LoginUser>

    }

    fun getUser()  = user

    fun onClick(view: View) {

        val loginUser = LoginUser(EmailAddress.value, Password.value)

        userMutableLiveData!!.setValue(loginUser)

    }

    fun makeRequest(){

        userRepository.signIn(object : Listener<User>{
            override fun onError() {
                user.value = null
            }

            override fun onResponse(response: User) {
                user.value = response
            }
        })
    }
}

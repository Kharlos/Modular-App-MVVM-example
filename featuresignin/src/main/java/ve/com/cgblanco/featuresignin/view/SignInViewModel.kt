package ve.com.cgblanco.featuresignin.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import android.view.View
import ve.com.cgblanco.featuresignin.model.LoginUser


class SignInViewModel : ViewModel() {
    var EmailAddress = MutableLiveData<String>()
    var Password = MutableLiveData<String>()

    private var userMutableLiveData: MutableLiveData<LoginUser>? = null

    fun getUser(): MutableLiveData<LoginUser> {

        if (userMutableLiveData == null) {
            userMutableLiveData = MutableLiveData<LoginUser>()
        }
        return userMutableLiveData as MutableLiveData<LoginUser>

    }

    fun onClick(view: View) {

        val loginUser = LoginUser(EmailAddress.value, Password.value)

        userMutableLiveData!!.setValue(loginUser)

    }
}

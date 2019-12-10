package ve.com.cgblanco.featuresignin.model

import android.util.Patterns
import ve.com.cgblanco.datasource.data.model.user.User


class LoginUser(val strEmailAddress: String? = "", val strPassword: String? = "") {

    val isEmailValid: Boolean
        get() = Patterns.EMAIL_ADDRESS.matcher(strEmailAddress).matches()


    val isPasswordLengthGreaterThan5: Boolean
        get() = if (strPassword != null) strPassword?.length > 5 else false

}
package ve.com.cgblanco.datasource.data.repository

import com.app.network.NetworkClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ve.com.cgblanco.datasource.api.AppApi
import ve.com.cgblanco.datasource.data.model.user.UserPersist
import javax.inject.Inject

class UserRepository@Inject constructor(
    val userPersist: UserPersist
)  {

    fun updateUserData(id:Int, name: String, email:String){
        GlobalScope.launch {
            userPersist.userDao.updateUserData(id, name, email)
        }
    }
}
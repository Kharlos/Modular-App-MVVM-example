package ve.com.cgblanco.editprofile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ve.com.cgblanco.datasource.data.model.user.User
import ve.com.cgblanco.datasource.data.repository.UserRepository
import javax.inject.Inject

class DynamicFeatureViewModel @Inject constructor(val userRepository: UserRepository) : ViewModel()  {

    var userLiveData : LiveData<User> = userRepository.userPersist.userDao.loadUser()

    fun updateUser(id:Int, name: String, email:String){
        userRepository.updateUserData(id, name, email)
    }
}
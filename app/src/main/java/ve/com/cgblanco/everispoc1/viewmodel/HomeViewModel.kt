package ve.com.cgblanco.everispoc1.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ve.com.cgblanco.datasource.data.model.user.User
import ve.com.cgblanco.datasource.data.repository.SignInRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(val userRepository: SignInRepository) : ViewModel() {

    var user = MutableLiveData<User>()

    fun getUser() = userRepository.userPersist.userDao.loadUser()

}
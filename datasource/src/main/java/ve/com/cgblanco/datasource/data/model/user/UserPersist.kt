package ve.com.cgblanco.datasource.data.model.user

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.app.network.cache.CacheInterface
import com.app.network.cache.MainDatabase
import javax.inject.Inject

class UserPersist @Inject constructor(val context: Context) :
    CacheInterface<User> {

    var userDao: UserDao = MainDatabase.getDatabase(context).userDao()


    override suspend fun saveItem(item: User) {
        userDao.save(item)
    }

    override suspend fun saveList(items: List<User>) {
    }

    override fun loadList(): LiveData<List<User>>? {
        return null
    }

    override fun load(): LiveData<User> {
        return userDao.load()
    }


}
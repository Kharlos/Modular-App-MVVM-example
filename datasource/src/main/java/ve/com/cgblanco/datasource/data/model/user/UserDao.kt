package ve.com.cgblanco.datasource.data.model.user

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(posts: User)

    @Query("SELECT * FROM user")
    fun load(): LiveData<User>
}
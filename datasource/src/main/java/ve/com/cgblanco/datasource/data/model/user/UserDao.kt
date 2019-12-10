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

    @Query("SELECT * FROM user limit 1")
    fun load(): LiveData<User>


    @Query("SELECT * FROM user WHERE user.id = 1")
    fun loadUser(): LiveData<User>

    @Query("UPDATE user SET name = :nameParam, email = :emailParam WHERE id = :id")
    fun updateUserData(id: Int, nameParam: String, emailParam:String): Int
}
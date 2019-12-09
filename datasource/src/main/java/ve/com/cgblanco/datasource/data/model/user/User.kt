package ve.com.cgblanco.datasource.data.model.user

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class User ( @PrimaryKey
                  @NonNull
                  @SerializedName("id")
                  @Expose
                  val id: Int,


                  @SerializedName("name")
                  @Expose
                  val name: String? = "",


                  @SerializedName("email")
                  @Expose
                  val email: String ? = ""
)
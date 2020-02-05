package ve.com.cgblanco.editprofile.view.activities

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import ve.com.cgblanco.editprofile.R

import kotlinx.android.synthetic.main.activity_dynamic_feature.*

class DynamicFeatureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic_feature)
        setSupportActionBar(toolbar)

    }

}

package com.bobmechanic.activitytransition.view.permission

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.bobmechanic.activitytransition.R
import com.bobmechanic.activitytransition.databinding.ActivityPermissionBinding
import java.util.*

class PermissionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPermissionBinding

    private val TAG = "PermissionRationalActivity"

    /* Id to identify Activity Recognition permission request. */
    private val PERMISSION_REQUEST_ACTIVITY_RECOGNITION = 45


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // If permissions granted, we start the main activity (shut this activity down).
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            finish()
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_permission)

        binding.approvePermissionRequest.setOnClickListener {
            onClickApprovePermissionRequest(it)
        }

        binding.denyPermissionRequest.setOnClickListener {
            onClickDenyPermissionRequest(it)
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>, grantResults: IntArray
    ) {
        val permissionResult = "Request code: " + requestCode + ", Permissions: " +
                Arrays.toString(permissions) + ", Results: " + Arrays.toString(grantResults)
        Log.d(
            TAG,
            "onRequestPermissionsResult(): $permissionResult"
        )
        if (requestCode == PERMISSION_REQUEST_ACTIVITY_RECOGNITION) {
            // Close activity regardless of user's decision (decision picked up in main activity).
            finish()
        }
    }

    fun onClickApprovePermissionRequest(view: View?) {
        Log.d(
            TAG,
            "onClickApprovePermissionRequest()"
        )

        // TODO: Review permission request for activity recognition.
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
            PERMISSION_REQUEST_ACTIVITY_RECOGNITION
        )
    }

    fun onClickDenyPermissionRequest(view: View?) {
        Log.d(
            TAG,
            "onClickDenyPermissionRequest()"
        )
        finish()
    }
}
package com.bobmechanic.activitytransition.view.transitionapi

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.bobmechanic.activitytransition.BuildConfig
import com.bobmechanic.activitytransition.R
import com.bobmechanic.activitytransition.data.ActivityTransitionSingleton.notificationChannel
import com.bobmechanic.activitytransition.databinding.ActivityTransitionApiBinding
import com.bobmechanic.activitytransition.view.permission.PermissionActivity
import com.google.android.gms.location.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ActivityTransitionApiActivity : AppCompatActivity() {

    companion object {
        val TAG = "ActivityTransitionTAG"

        // Action fired when transitions are triggered.
        val TRANSITIONS_RECEIVER_ACTION: String =
            BuildConfig.APPLICATION_ID.toString() + "TRANSITIONS_RECEIVER_ACTION"

        private fun toActivityString(activity: Int): String? {
            return when (activity) {
                DetectedActivity.STILL -> "STILL"
                DetectedActivity.WALKING -> "WALKING"
                else -> "UNKNOWN"
            }
        }


        private fun toTransitionType(transitionType: Int): String? {
            return when (transitionType) {
                ActivityTransition.ACTIVITY_TRANSITION_ENTER -> "ENTER"
                ActivityTransition.ACTIVITY_TRANSITION_EXIT -> "EXIT"
                else -> "UNKNOWN"
            }
        }
    }

    // TODO: Review check for devices with Android 10 (29+).
    private val runningQOrLater = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

    private var activityTrackingEnabled = false

    private lateinit var activityTransitionList: ArrayList<ActivityTransition>

    private var mActivityTransitionsPendingIntent: PendingIntent? = null
    private var mTransitionsReceiver: TransitionsReceiver? =
        null

    private lateinit var binding: ActivityTransitionApiBinding

    private var textTv:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_transition_api)
        setupUI()
    }

    private fun setupUI() {
        activityTrackingEnabled = true

        // List of activity transitions to track.
        activityTransitionList = ArrayList()

        // TODO: Add activity transitions to track.
        activityTransitionList.add(
            ActivityTransition.Builder()
                .setActivityType(DetectedActivity.WALKING)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                .build()
        )
        activityTransitionList.add(
            ActivityTransition.Builder()
                .setActivityType(DetectedActivity.WALKING)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                .build()
        )
        activityTransitionList.add(
            ActivityTransition.Builder()
                .setActivityType(DetectedActivity.STILL)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                .build()
        )
        activityTransitionList.add(
            ActivityTransition.Builder()
                .setActivityType(DetectedActivity.STILL)
                .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                .build()
        )

        // TODO: Initialize PendingIntent that will be triggered when a activity transition occurs.
        val intent = Intent(TRANSITIONS_RECEIVER_ACTION)
        mActivityTransitionsPendingIntent =
            PendingIntent.getBroadcast(this@ActivityTransitionApiActivity, 0, intent, 0)

        // TODO: Create a BroadcastReceiver to listen for activity transitions.
        // The receiver listens for the PendingIntent above that is triggered by the system when an
        // activity transition occurs.
        mTransitionsReceiver = TransitionsReceiver()

        if (activityRecognitionPermissionApproved()) {
            enableActivityTransitions()
        } else {
            // Request permission and start activity for result. If the permission is approved, we
            // want to make sure we start activity recognition tracking.
            val startIntent = Intent(
                this,
                PermissionActivity::class.java
            )
            startActivityForResult(startIntent, 0)
        }


        printToScreen("App iniciada.")

        launchChannelCollet()
    }

    fun printToScreen(text: String) {
        textTv += "$text\n"
        binding.tvTrack.text = textTv
    }

    fun launchChannelCollet() {
        MainScope().launch {
            notificationChannel.asFlow().collect {
                textTv += "$it\n"
                binding.tvTrack.text = textTv

            }
        }
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(mTransitionsReceiver, IntentFilter(TRANSITIONS_RECEIVER_ACTION))
        if (!activityTrackingEnabled) {
            enableActivityTransitions()
        }
    }

    override fun onPause() {
        if (activityTrackingEnabled) {
            disableActivityTransitions()
        }
        super.onPause()
    }


    override fun onStop() {

        // TODO: Unregister activity transition receiver when user leaves the app.
        unregisterReceiver(mTransitionsReceiver)
        super.onStop()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Start activity recognition if the permission was approved.
        if (activityRecognitionPermissionApproved() && !activityTrackingEnabled) {
            enableActivityTransitions()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * Registers callbacks for [ActivityTransition] events via a custom
     * [BroadcastReceiver]
     */
    private fun enableActivityTransitions() {
        Log.d(TAG, "enableActivityTransitions()")

        // TODO: Create request and listen for activity changes.
        val request = ActivityTransitionRequest(activityTransitionList)

        // Register for Transitions Updates.
        val task = ActivityRecognition.getClient(this)
            .requestActivityTransitionUpdates(request, mActivityTransitionsPendingIntent)
        task.addOnSuccessListener {
            activityTrackingEnabled = true
            printToScreen("Transitions Api was successfully registered.")
        }
        task.addOnFailureListener { e ->
            printToScreen("Transitions Api could NOT be registered: $e")
            Log.e(TAG, "Transitions Api could NOT be registered: $e")
        }
    }


    /**
     * Unregisters callbacks for [ActivityTransition] events via a custom
     * [BroadcastReceiver]
     */
    private fun disableActivityTransitions() {
        Log.d(TAG, "disableActivityTransitions()")


        // TODO: Stop listening for activity changes.
        ActivityRecognition.getClient(this)
            .removeActivityTransitionUpdates(mActivityTransitionsPendingIntent)
            .addOnSuccessListener {
                activityTrackingEnabled = false
                printToScreen("Transitions successfully unregistered.")
            }
            .addOnFailureListener { e ->
                printToScreen("Transitions could not be unregistered: $e")
                Log.e(TAG, "Transitions could not be unregistered: $e")
            }
    }

    /**
     * On devices Android 10 and beyond (29+), you need to ask for the ACTIVITY_RECOGNITION via the
     * run-time permissions.
     */
    private fun activityRecognitionPermissionApproved(): Boolean {

        // TODO: Review permission check for 29+.
        return if (runningQOrLater) {
            PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACTIVITY_RECOGNITION
            )
        } else {
            true
        }
    }

    /**
     * Handles intents from from the Transitions API.
     */
    class TransitionsReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (!TextUtils.equals(TRANSITIONS_RECEIVER_ACTION, intent.action)) {
                GlobalScope.launch {
                    notificationChannel.send(
                        "Received an unsupported action in TransitionsReceiver: action = " + intent.action
                    )
                }
                return
            }

            // TODO: Extract activity transition information from listener.
            if (ActivityTransitionResult.hasResult(intent)) {
                val result = ActivityTransitionResult.extractResult(intent)
                for (event in result!!.transitionEvents) {
                    val info = "Transition: " + toActivityString(event.activityType) +
                            " (" + toTransitionType(event.transitionType) + ")" + "   " +
                            SimpleDateFormat("HH:mm:ss", Locale.US).format(Date())
                    GlobalScope.launch {
                        notificationChannel.send(info)
                    }
                }
            }
        }
    }
}
package com.bobmechanic.activitytransition.data

import kotlinx.coroutines.channels.BroadcastChannel

object ActivityTransitionSingleton {

    var notificationChannel: BroadcastChannel<String> = BroadcastChannel(1)

}
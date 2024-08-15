package io.github.ginvavilon.slimevr.tracker.data.slimevr.protocol;

import kotlin.jvm.JvmStatic

enum class TrackerStatus(val id: Byte, val sendData: Boolean, val reset: Boolean) {

    DISCONNECTED(0, false, true),
    OK(1, true, false),
    BUSY(2, true, false),
    ERROR(3, false, true),
    OCCLUDED(4, false, false),
    TIMED_OUT(5, false, false),
    ;

    companion object {

        private val byId = entries.associateBy { it.id }

        @JvmStatic
        fun getById(id: Byte): TrackerStatus? = byId[id]
    }
}
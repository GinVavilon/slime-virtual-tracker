package io.github.ginvavilon.slimevr.tracker.data.slimevr.protocol

enum class UserActionType(
    val bits: Byte
) {

    RESET_FULL(2),
    RESET_YAW(3),
    RESET_MOUNTING(4),
    PAUSE_TRACKING(5)
}
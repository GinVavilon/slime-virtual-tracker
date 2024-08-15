package io.github.ginvavilon.slimevr.tracker.data.slimevr.protocol

enum class DataType(
    val bits: Byte
) {

    NORMAL(1),
    CORRECTION(2),
}
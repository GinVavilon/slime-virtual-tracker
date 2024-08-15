package io.github.ginvavilon.slimevr.tracker.data.slimevr.protocol

import kotlin.experimental.and

data class SensorTap(val tapBits: Byte) {
	val doubleTap = tapBits and  0x40 > 0

	enum class TapAxis {
		X,
		Y,
		Z,
	}
}
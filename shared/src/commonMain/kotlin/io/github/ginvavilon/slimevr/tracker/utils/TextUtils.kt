package io.github.ginvavilon.slimevr.tracker.utils


val ipv4Regex by lazy { """^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$""".toRegex() }

fun String.isInt() = this.toIntOrNull() != null

@OptIn(ExperimentalStdlibApi::class)
fun ByteArray.toHex(separator: String = " "): String {
    return joinToString(separator) { it.toHexString() }
}

fun String.isIpV4Address() = ipv4Regex.matches(this)
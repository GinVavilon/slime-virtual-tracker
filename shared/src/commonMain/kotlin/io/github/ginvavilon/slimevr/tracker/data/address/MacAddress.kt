package io.github.ginvavilon.slimevr.tracker.data.address

@OptIn(ExperimentalStdlibApi::class)
data class MacAddress(
    val byteArray: ByteArray
) {


    constructor(a0: Byte, a1: Byte, a2: Byte, a3: Byte, a4: Byte, a5: Byte)
            : this(byteArrayOf(a0, a1, a2, a3, a4, a5))

    constructor(macAddress: String) : this(parseMacAddress(macAddress))

    init {
        check(byteArray.size == 6) { "Mac Address must be 6 byte" }
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as MacAddress

        return byteArray.contentEquals(other.byteArray)
    }

    override fun hashCode(): Int {
        return byteArray.contentHashCode()
    }

    override fun toString(): String {
        return "MacAddress(" + byteArray.joinToString(":") { it.toHexString() } + ")"
    }

    companion object {
        fun parseMacAddress(macAddress: String): ByteArray {
            return macAddress.split(":")
                .map { it.toInt(16).toByte() }
                .toByteArray()
        }
    }

}
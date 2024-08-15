package io.github.ginvavilon.slimevr.tracker.utils

import kotlin.experimental.and

class ByteBuffer(private val byteArray: ByteArray) {

    private var position = 0

    fun putInt(value: Int) {
        byteArray[position++] = ((value shr 24) and 0xFF).toByte()
        byteArray[position++] = ((value shr 16) and 0xFF).toByte()
        byteArray[position++] = ((value shr 8) and 0xFF).toByte()
        byteArray[position++] = (value and 0xFF).toByte()
    }
    fun putInt(value: UInt) {
        byteArray[position++] = ((value shr 24) and 0xFFu).toByte()
        byteArray[position++] = ((value shr 16) and 0xFFu).toByte()
        byteArray[position++] = ((value shr 8) and 0xFFu).toByte()
        byteArray[position++] = (value and 0xFFu).toByte()
    }

    fun putLong(value: Long) {
        byteArray[position++] = ((value shr 56) and 0xFF).toByte()
        byteArray[position++] = ((value shr 48) and 0xFF).toByte()
        byteArray[position++] = ((value shr 40) and 0xFF).toByte()
        byteArray[position++] = ((value shr 32) and 0xFF).toByte()
        byteArray[position++] = ((value shr 24) and 0xFF).toByte()
        byteArray[position++] = ((value shr 16) and 0xFF).toByte()
        byteArray[position++] = ((value shr 8) and 0xFF).toByte()
        byteArray[position++] = (value and 0xFF).toByte()
    }

    fun putByte(value: Byte) {
        byteArray[position++] = value
    }

    fun putChar(value: Char) {
        byteArray[position++] = ((value.code shr 8) and 0xFF).toByte()
        byteArray[position++] = (value.code and 0xFF).toByte()
    }
    fun putShort(value: Int) {
        byteArray[position++] = ((value shr 8) and 0xFF).toByte()
        byteArray[position++] = (value and 0xFF).toByte()
    }

    fun putShort(value: Short) {
        putShort(value.toInt())
    }

    fun putFloat(value: Float) {
        putInt(value.toRawBits())
    }

    fun putDouble(value: Double) {
        putLong(value.toRawBits())
    }

    fun put(byteArray: ByteArray) {
        for (byte in byteArray) {
            this.byteArray[position++] = byte
        }
    }

    fun put(byte: Byte) {
        this.byteArray[position++] = byte
    }

    fun array(): ByteArray {
        return byteArray
    }

    companion object {
        fun allocate(capacity: Int) = ByteBuffer(ByteArray(capacity))
    }
}

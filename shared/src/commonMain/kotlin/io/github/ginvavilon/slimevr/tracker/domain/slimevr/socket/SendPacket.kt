package io.github.ginvavilon.slimevr.tracker.domain.slimevr.socket

import io.github.ginvavilon.slimevr.tracker.data.address.MacAddress
import io.github.ginvavilon.slimevr.tracker.data.slimevr.protocol.BoardType
import io.github.ginvavilon.slimevr.tracker.data.slimevr.protocol.DataType
import io.github.ginvavilon.slimevr.tracker.data.slimevr.protocol.IMUType
import io.github.ginvavilon.slimevr.tracker.data.slimevr.protocol.MCUType
import io.github.ginvavilon.slimevr.tracker.data.math.Quaternion
import io.github.ginvavilon.slimevr.tracker.data.slimevr.protocol.SensorTap
import io.github.ginvavilon.slimevr.tracker.data.slimevr.protocol.TrackerStatus
import io.github.ginvavilon.slimevr.tracker.data.slimevr.protocol.UserActionType
import io.github.ginvavilon.slimevr.tracker.data.math.Vector3
import io.github.ginvavilon.slimevr.tracker.utils.ByteBuffer
import io.ktor.utils.io.charsets.Charsets
import io.ktor.utils.io.core.toByteArray

/*
 * https://github.com/SlimeVR/SlimeVR-Server/blob/main/server/core/src/main/java/dev/slimevr/tracking/trackers/udp/UDPPacket.kt
 */
sealed interface SendPacket {

    val byteArray: ByteArray

    data object Heartbeat : SendPacket {
        override val byteArray = ByteBuffer.allocate(28).apply {
            putInt(PACKET_HEARTBEAT)
        }.array()
    }

    data class Handshake(
        val packetId: Long,
        val fwString: String,
        val firmwareBuild: Int,
        val macAddress: MacAddress,
        val imuType: IMUType,
        val boardType: BoardType,
        val mcuType: MCUType,
    ) : SendPacket {
        override val byteArray = ByteBuffer.allocate(128).apply {
            putInt(PACKET_HANDSHAKE)                    // packet 3 header
            putLong(packetId)                           // packet counter
            putInt(boardType.id)                        // Board type
            putInt(imuType.id)                          // IMU type
            putInt(mcuType.id)                          // MCU type
            repeat(3) { putInt(0) }         // IMU info (unused)
            putInt(firmwareBuild)                       // Firmware build
            put(fwString.length.toByte())               // Length of fw string
            put(fwString.toByteArray(Charsets.UTF_8))   // fw string

            put(macAddress.byteArray)                   // MAC address
        }.array()
    }

    data class SensorInfo(
        val packetId: Long,
        val trackerId: Byte,
        val trackerStatus: TrackerStatus,
        val imuType: IMUType,
    ) : SendPacket {
        override val byteArray = ByteBuffer.allocate(128).apply {
            putInt(PACKET_SENSOR_INFO)   // packet 15 header
            putLong(packetId)            // packet counter
            put(trackerId)               // tracker id (shown as IMU Tracker #x in SlimeVR)
            put(trackerStatus.id)        // sensor status
            put(imuType.id.toByte())     // imu type
        }.array()
    }

    data class Ping(
        val packetId: Long,
        val pingId: Int,
    ) : SendPacket {
        override val byteArray = ByteBuffer.allocate(128).apply {
            putInt(PACKET_PING_PONG)
            putLong(packetId)
            putInt(pingId)
        }.array()
    }

    data class Serial(
        val packetId: Long,
        val serial: String,
    ) : SendPacket {
        override val byteArray = ByteBuffer.allocate(128).apply {
            putInt(PACKET_SERIAL)
            putLong(packetId)
            putInt(serial.length)
            put(serial.toByteArray(Charsets.UTF_8))
        }.array()
    }

    data class BatteryLevel(
        val packetId: Long,
        var voltage: Float,
        var level: Float,
    ) : SendPacket {
        override val byteArray = ByteBuffer.allocate(128).apply {
            putInt(PACKET_BATTERY_LEVEL)
            putLong(packetId)
            putFloat(voltage)
            putFloat(level)
        }.array()
    }


    data class Tap(
        val packetId: Long,
        val trackerId: Byte,
        val sensorTap: SensorTap,
    ) : SendPacket {
        override val byteArray = ByteBuffer.allocate(128).apply {
            putInt(PACKET_TAP)   // packet 13 header
            putLong(packetId)            // packet counter
            put(trackerId)               // tracker id (shown as IMU Tracker #x in SlimeVR)
            put(sensorTap.tapBits)        // sensor tap
        }.array()
    }
    data class SignalStrength(
        val packetId: Long,
        val trackerId: Byte,
        val signalStrength: Byte,
    ) : SendPacket {
        override val byteArray = ByteBuffer.allocate(128).apply {
            putInt(PACKET_SIGNAL_STRENGTH)   // packet 19 header
            putLong(packetId)            // packet counter
            put(trackerId)               // tracker id (shown as IMU Tracker #x in SlimeVR)
            put(signalStrength)
        }.array()
    }

    data class MagnetometerAccuracy(
        val packetId: Long,
        val trackerId: Byte,
        var accuracyInfo: Float,
    ) : SendPacket {
        override val byteArray = ByteBuffer.allocate(128).apply {
            putInt(PACKET_MAGNETOMETER_ACCURACY)   // packet 18 header
            putLong(packetId)            // packet counter
            put(trackerId)               // tracker id (shown as IMU Tracker #x in SlimeVR)
            putFloat(accuracyInfo)
        }.array()
    }

    data class Temperature(
        val packetId: Long,
        val trackerId: Byte,
        var temperature: Float,
    ) : SendPacket {
        override val byteArray = ByteBuffer.allocate(128).apply {
            putInt(PACKET_TEMPERATURE)   // packet 20 header
            putLong(packetId)            // packet counter
            put(trackerId)               // tracker id (shown as IMU Tracker #x in SlimeVR)
            putFloat(temperature)
        }.array()
    }

    data class UserAction(
        val packetId: Long,
        val trackerId: Byte,
        var userActionType: UserActionType,
    ) : SendPacket {
        override val byteArray = ByteBuffer.allocate(128).apply {
            putInt(PACKET_USER_ACTION)   // packet 18 header
            putLong(packetId)            // packet counter
            put(trackerId)               // tracker id (shown as IMU Tracker #x in SlimeVR)
            put(userActionType.bits)
        }.array()
    }

    data class Rotation(
        val packetId: Long,
        val trackerId: Byte,
        val dataType: DataType,
        val rotation: Quaternion
    ) : SendPacket {
        override val byteArray = ByteBuffer.allocate(128).apply {
            putInt(PACKET_ROTATION_DATA)                           // packet 17 header
            putLong(packetId) // packet counter
            put(trackerId)                 // tracker id (shown as IMU Tracker #x in SlimeVR)
            put(dataType.bits)                  // data type
            putQuaternion(rotation)
            put(0.toByte())                     // Calibration info
        }.array()
    }
    data class RotationAndAcceleration(
        val packetId: Long,
        val trackerId: Byte,
        val rotation: Quaternion,
        var acceleration: Vector3,
    ) : SendPacket {

        override val byteArray = ByteBuffer.allocate(128).apply {
            putInt(PACKET_ROTATION_AND_ACCELERATION)                           // packet 17 header
            putLong(packetId) // packet counter
            put(trackerId)                 // tracker id (shown as IMU Tracker #x in SlimeVR)
            val scaleR = (1 shl 15).toFloat() // Q15: 1 is represented as 0x7FFF and -1 as 0x8000
            putShort((scaleR * rotation.x).toInt())
            putShort((scaleR * rotation.y).toInt())
            putShort((scaleR * rotation.z).toInt())
            putShort((scaleR * rotation.w).toInt())
            val scaleA = (1 shl 7).toFloat() // The same as the HID sc
            putShort((scaleA * acceleration.x).toInt())
            putShort((scaleA * acceleration.y).toInt())
            putShort((scaleA * acceleration.z).toInt())
        }.array()
    }

    companion object {
        const val PACKET_HEARTBEAT = 0
        const val PACKET_ROTATION = 1 // Deprecated

        // public static final int PACKET_GYRO = 2; // Deprecated
        const val PACKET_HANDSHAKE = 3
        const val PACKET_ACCEL = 4

        // public static final int PACKET_MAG = 5; // Deprecated
        // public static final int PACKET_RAW_CALIBRATION_DATA = 6; // Not parsed by
        // server
        // public static final int PACKET_CALIBRATION_FINISHED = 7; // Not parsed by
        // server
        // public static final int PACKET_CONFIG = 8; // Not parsed by server
        // public static final int PACKET_RAW_MAGNETOMETER = 9 // Deprecated
        const val PACKET_PING_PONG = 10
        const val PACKET_SERIAL = 11
        const val PACKET_BATTERY_LEVEL = 12
        const val PACKET_TAP = 13
        const val PACKET_ERROR = 14
        const val PACKET_SENSOR_INFO = 15

        //  const val PACKET_ROTATION_2 = 16 // Deprecated
        const val PACKET_ROTATION_DATA = 17
        const val PACKET_MAGNETOMETER_ACCURACY = 18
        const val PACKET_SIGNAL_STRENGTH = 19
        const val PACKET_TEMPERATURE = 20
        const val PACKET_USER_ACTION = 21
        const val PACKET_FEATURE_FLAGS = 22
        const val PACKET_ROTATION_AND_ACCELERATION = 23
        const val PACKET_BUNDLE = 100
        const val PACKET_BUNDLE_COMPACT = 101
        const val PACKET_PROTOCOL_CHANGE = 200
    }

}

private fun ByteBuffer.putQuaternion(quaternion: Quaternion) {
    putFloat(quaternion.x)                // Quaternion x
    putFloat(quaternion.y)                // Quaternion y
    putFloat(quaternion.z)                // Quaternion z
    putFloat(quaternion.w)                // Quaternion w
}

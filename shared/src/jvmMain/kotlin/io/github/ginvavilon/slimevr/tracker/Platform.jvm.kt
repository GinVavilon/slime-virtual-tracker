package io.github.ginvavilon.slimevr.tracker

actual fun getPlatform(): Platform  = object : Platform {
    override val name: String = "jvm"

}
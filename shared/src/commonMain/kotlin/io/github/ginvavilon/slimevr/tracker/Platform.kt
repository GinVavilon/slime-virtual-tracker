package io.github.ginvavilon.slimevr.tracker

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
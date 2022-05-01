package fr.twah2em.bossbars.entity

import fr.twah2em.mcreflection.invokeMethod
import fr.twah2em.mcreflection.method

class DataWatcher(private val nmsDataWatcher: Any) {
    fun <T : Any> set(index: Int, value: T) {
        invokeMethod<Any>(
            method(
                nmsDataWatcher.javaClass,
                "watch",
                Int::class.java,
                value::class.java
            )!!,
            nmsDataWatcher,
            index,
            value
        )
    }
}
package fr.twah2em.bossbars.entity

import fr.twah2em.bossbars.witherLocation
import fr.twah2em.mcreflection.*
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Player

class EntityWither(
    val receiver: Player,
    world: World,
    displayName: String,
    health: Double,
) {
    private val nmsEntityWitherConstructor =
        constructor(NMSClass("EntityWither")!!, arrayOf(handle(world)!!.javaClass))!!

    val nmsEntityWither = invokeConstructor(nmsEntityWitherConstructor)!!
    val dataWatcher = DataWatcher(invokeMethod(method(nmsEntityWither.javaClass, "getDataWatcher"), nmsEntityWither))
    val id = invokeMethod(method(nmsEntityWither.javaClass, "getId"), nmsEntityWither) as Int

    var location = receiver.location!!
        set(value) {
            field = value
            val witherLocation: Location = witherLocation(value)
            invokeMethod<Any>(
                method(
                    nmsEntityWither.javaClass,
                    "setLocation",
                    Double::class.java,
                    Double::class.java,
                    Double::class.java,
                    Float::class.java,
                    Float::class.java
                ),
                nmsEntityWither,
                witherLocation.x,
                witherLocation.y,
                witherLocation.z,
                witherLocation.yaw,
                witherLocation.pitch
            )

            sendPacket(receiver, getPacket("PacketPlayOutEntityTeleport", nmsEntityWither))
        }

    var displayName = displayName
        set(value) {
            field = value
            invokeMethod<Any>(
                method(
                    nmsEntityWither.javaClass,
                    "setCustomName",
                    String::class.java
                ),
                nmsEntityWither,
                value
            )

            invokeMethod<Any>(
                method(
                    nmsEntityWither.javaClass,
                    "setCustomNameVisible",
                    Boolean::class.java
                ),
                nmsEntityWither,
                true
            )

            sendPacket(receiver, getPacket("PacketPlayOutEntityMetadata", nmsEntityWither))
        }

    var health = health
        set(value) {
            field = value
            invokeMethod<Any>(
                method(
                    nmsEntityWither.javaClass,
                    "setHealth",
                    Double::class.java
                ),
                nmsEntityWither,
                value
            )

            sendPacket(receiver, getPacket("PacketPlayOutEntityMetadata", nmsEntityWither))
        }

    var maxHealth = invokeMethod<Double>(method(nmsEntityWither.javaClass, "getMaxHealth"), nmsEntityWither)

    var isInvisible = true
        set(value) {
            field = value
            invokeMethod<Any>(
                method(
                    nmsEntityWither.javaClass,
                    "setInvisible",
                    Boolean::class.java
                ),
                nmsEntityWither,
                value
            )

            sendPacket(receiver, getPacket("PacketPlayOutEntityMetadata", nmsEntityWither))
        }
}
package com.clauscode.serialization

import com.clauscode.serialization.exception.WorldNotFoundException
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import org.bukkit.Bukkit
import org.bukkit.Location

object LocationSerializer: KSerializer<Location> {
    override val descriptor = buildClassSerialDescriptor("org.bukkit.Location") {
        element<String>("world")
        element<Double>("x")
        element<Double>("y")
        element<Double>("z")
        element<Float>("yaw")
        element<Float>("pitch")
    }

    override fun serialize(encoder: Encoder, value: Location) =
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.world.name)
            encodeStringElement(descriptor, 1, String.format("%.2f", value.x))
            encodeStringElement(descriptor, 2, String.format("%.2f", value.y))
            encodeStringElement(descriptor, 3, String.format("%.2f", value.z))
            encodeStringElement(descriptor, 4, String.format("%.2f", value.yaw))
            encodeStringElement(descriptor, 5, String.format("%.2f", value.pitch))
        }

    override fun deserialize(decoder: Decoder): Location =
        decoder.decodeStructure(descriptor) {
            var worldName = "world"
            var x = 0.0
            var y = 0.0
            var z = 0.0
            var yaw = 0f
            var pitch = 0f

            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> worldName = decodeStringElement(descriptor, 0)
                    1 -> x = decodeDoubleElement(descriptor, 1)
                    2 -> y = decodeDoubleElement(descriptor, 2)
                    3 -> z = decodeDoubleElement(descriptor, 3)
                    4 -> yaw = decodeFloatElement(descriptor, 4)
                    5 -> pitch = decodeFloatElement(descriptor, 5)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
            val world = Bukkit.getWorld(worldName) ?: throw WorldNotFoundException(worldName)
            Location(world, x, y, z, yaw, pitch)
        }
}
package com.clauscode.serialization

import com.clauscode.serialization.exception.WorldNotFoundException
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import org.bukkit.Bukkit
import org.bukkit.block.Block

object BlockSerializer: KSerializer<Block> {
    override val descriptor = buildClassSerialDescriptor("org.bukkit.block.Block") {
        element<String>("world")
        element<Int>("x")
        element<Int>("y")
        element<Int>("z")
    }

    override fun serialize(encoder: Encoder, value: Block) =
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.world.name)
            encodeIntElement(descriptor, 1, value.x)
            encodeIntElement(descriptor, 2, value.y)
            encodeIntElement(descriptor, 3, value.z)
        }

    override fun deserialize(decoder: Decoder): Block =
        decoder.decodeStructure(descriptor) {
            var worldName = "world"
            var x = 0
            var y = 0
            var z = 0

            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> worldName = decodeStringElement(descriptor, 0)
                    1 -> x = decodeIntElement(descriptor, 1)
                    2 -> y = decodeIntElement(descriptor, 2)
                    3 -> z = decodeIntElement(descriptor, 3)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
            val world = Bukkit.getWorld(worldName) ?: throw WorldNotFoundException(worldName)
            world.getBlockAt(x, y, z)
        }
}
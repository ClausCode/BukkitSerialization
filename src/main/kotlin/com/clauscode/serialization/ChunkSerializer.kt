package com.clauscode.serialization

import com.clauscode.serialization.exception.WorldNotFoundException
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import org.bukkit.Bukkit
import org.bukkit.Chunk

object ChunkSerializer: KSerializer<Chunk> {
    override val descriptor = buildClassSerialDescriptor("org.bukkit.Chunk") {
        element<String>("world")
        element<Int>("x")
        element<Int>("z")
    }

    override fun serialize(encoder: Encoder, value: Chunk) =
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.world.name)
            encodeIntElement(descriptor, 1, value.x)
            encodeIntElement(descriptor, 2, value.z)
        }

    override fun deserialize(decoder: Decoder): Chunk =
        decoder.decodeStructure(descriptor) {
            var worldName = "world"
            var x = 0
            var z = 0

            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> worldName = decodeStringElement(descriptor, 0)
                    1 -> x = decodeIntElement(descriptor, 1)
                    2 -> z = decodeIntElement(descriptor, 2)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
            val world = Bukkit.getWorld(worldName) ?: throw WorldNotFoundException(worldName)
            world.getChunkAt(x, z)
        }
}
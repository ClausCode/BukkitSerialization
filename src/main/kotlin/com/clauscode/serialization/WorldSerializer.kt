package com.clauscode.serialization

import com.clauscode.serialization.exception.WorldNotFoundException
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.Bukkit
import org.bukkit.World

object WorldSerializer : KSerializer<World> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("org.bukkit.World", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: World) {
        encoder.encodeString(value.name)
    }

    override fun deserialize(decoder: Decoder): World {
        val worldName = decoder.decodeString()

        return Bukkit.getWorld(worldName) ?: throw WorldNotFoundException(worldName)
    }
}
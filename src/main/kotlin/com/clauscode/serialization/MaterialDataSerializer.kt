package com.clauscode.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.*
import org.bukkit.Material
import org.bukkit.material.MaterialData

object MaterialDataSerializer: KSerializer<MaterialData> {
    override val descriptor = buildClassSerialDescriptor("org.bukkit.material.MaterialData") {
        element<String>("material")
        element<Byte>("data")
    }

    override fun serialize(encoder: Encoder, value: MaterialData) =
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.itemType.name)
            encodeIntElement(descriptor, 1, value.data.toInt())
        }

    override fun deserialize(decoder: Decoder): MaterialData =
        decoder.decodeStructure(descriptor) {
            var materialName = "world"
            var data = 0

            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> materialName = decodeStringElement(descriptor, 0)
                    1 -> data = decodeIntElement(descriptor, 1)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
            MaterialData(
                Material.getMaterial(materialName),
                data.toByte()
            )
        }
}
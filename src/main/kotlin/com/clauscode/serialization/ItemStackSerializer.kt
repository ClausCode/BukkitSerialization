package com.clauscode.serialization

import com.clauscode.serialization.model.ItemStackModel
import kotlinx.serialization.KSerializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.inventory.ItemStack

object ItemStackSerializer: KSerializer<ItemStack> {
    override val descriptor = ItemStackModel.serializer().descriptor

    override fun serialize(encoder: Encoder, value: ItemStack) {
        val model = ItemStackModel()
        model.copyStack(value)
        encoder.encodeSerializableValue(ItemStackModel.serializer(), model)
    }

    override fun deserialize(decoder: Decoder): ItemStack {
        val model = decoder.decodeSerializableValue(ItemStackModel.serializer())
        return model.buildStack()
    }
}
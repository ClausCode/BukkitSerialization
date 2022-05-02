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
        model.materialData = value.data
        model.amount = value.amount
        model.displayName = value.itemMeta.displayName
        model.lore = value.itemMeta.lore
        model.enchantments = value.enchantments
        model.flags = value.itemMeta.itemFlags

        encoder.encodeSerializableValue(ItemStackModel.serializer(), model)
    }

    override fun deserialize(decoder: Decoder): ItemStack {
        val model = decoder.decodeSerializableValue(ItemStackModel.serializer())
        return model.buildStack()
    }
}
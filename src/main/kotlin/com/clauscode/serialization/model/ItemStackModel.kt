package com.clauscode.serialization.model

import com.clauscode.serialization.EnchantmentSerializer
import com.clauscode.serialization.MaterialDataSerializer
import com.clauscode.serialization.extensions.translateColors
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.material.MaterialData
import java.util.*
import kotlin.collections.HashSet

@Serializable
@SerialName("org.bukkit.inventory.ItemStack")
class ItemStackModel {
    var displayName: String = ""
    @Serializable(with = MaterialDataSerializer::class)
    var materialData: MaterialData = MaterialData(Material.AIR)
    var amount: Int = 1
    var lore: List<String> = listOf()
    var enchantments: Map<@Serializable(with = EnchantmentSerializer::class) Enchantment, Int> = HashMap()
    var flags: Set<ItemFlag> = HashSet()

    fun buildStack(): ItemStack {
        val stack = ItemStack(materialData.itemType, amount, materialData.data.toShort())
        val meta = stack.itemMeta
        if(displayName.isNotEmpty()) meta.displayName = displayName.translateColors()
        meta.lore = lore.map { it.translateColors() }
        stack.itemMeta = meta
        stack.addEnchantments(enchantments)

        return stack
    }
}
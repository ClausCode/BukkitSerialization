package com.clauscode.serialization.extensions

import org.bukkit.ChatColor

fun String.translateColors() =
    replace("&", ChatColor.COLOR_CHAR.toString())
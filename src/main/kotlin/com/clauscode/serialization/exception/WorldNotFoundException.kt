package com.clauscode.serialization.exception

class WorldNotFoundException(worldName: String)
    : RuntimeException("World with name `$worldName` is not found!")
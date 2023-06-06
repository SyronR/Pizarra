package com.galvezssr.pizarra.kernel.objects

class Table(
    val name: String
) {
    constructor(): this("")

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        val table = other as Table
        return name == table.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
    override fun toString(): String {
        return name
    }
}




package com.galvezssr.pizarra.kernel.objects

class Task(
    val name: String,
    val description: String,
    val priority: String,
    val date: String
) {
    constructor(): this("", "", "", "")

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        val task = other as Task
        return name == task.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}

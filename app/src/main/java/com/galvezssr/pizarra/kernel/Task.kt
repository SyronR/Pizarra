package com.galvezssr.pizarra.kernel

class Task(
    val name: String,
    val description: String,
    val priority: String,
    val date: String
) {
    constructor(): this("", "", "", "")
}

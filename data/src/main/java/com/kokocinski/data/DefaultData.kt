package com.kokocinski.data

private val hours = 1000 * 60 * 60L

val defaultTimers = listOf(
        Timer(name = "Market", duration = 3 * hours),
        Timer(name = "Campaign", duration = 7 * hours),
        Timer(name = "Guild Raid", duration = 16 * hours),
        Timer(name = "Guild Mill", duration = 12 * hours),
        Timer(name = "Basic Summon", duration = 8 * hours),
        Timer(name = "Heroic Summon", duration = 48 * hours),
        Timer(name = "Celestial Islands", duration = 6 * hours),
        Timer(name = "Celestial Islands Boss", duration = 8 * hours),
        Timer(name = "Scout", duration = 8 * hours),
        Timer(name = "Midas", duration = 8 * hours)
)
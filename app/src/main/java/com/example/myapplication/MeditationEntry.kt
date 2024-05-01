package com.example.myapplication

data class MeditationEntry(
    val id: Int,
    val date: String,
    val time: String,
    val duration: Int
)
{
    override fun toString(): String {
        return "Data:$date - Time:$time Duration:-$duration"
    }
}

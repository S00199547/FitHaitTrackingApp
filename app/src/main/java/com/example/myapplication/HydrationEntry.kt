package com.example.myapplication

data class HydrationEntry (
    val id: Int,
    val date: String,
    val time: String,
    val amount: Int
)
{
    override fun toString(): String {
        return "Data:$date - Time:$time Amount:-$amount"
    }
}
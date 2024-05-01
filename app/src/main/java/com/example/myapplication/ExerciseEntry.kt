package com.example.myapplication

data class ExerciseEntry(
    val id: Int,
    val date: String,
    val time: String,
    val duration: Int,
    val exerciseType: String
)
{
    override fun toString(): String {
        return  " ExerciseType:$exerciseType Data:$date - Time:$time Duration:$duration"
    }
}
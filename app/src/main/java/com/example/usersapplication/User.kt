package com.example.usersapplication


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    val id: String,
    val name: String,
    val lastName: String,
    val email: String
) : Parcelable
package com.example.greenwichregistr

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Client (
    val first_name: String,
    val phone_number: String
    ): Parcelable
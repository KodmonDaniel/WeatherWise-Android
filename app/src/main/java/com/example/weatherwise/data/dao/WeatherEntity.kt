package com.example.weatherwise.data.dao

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
@Entity
data class WeatherEntity(
    @PrimaryKey val uuid: UUID,
    @ColumnInfo(name = "city") val city: String?,
    @ColumnInfo(name = "desc") val desc: String?,
    @ColumnInfo(name = "icon") val icon: String?,
    @ColumnInfo(name = "temp") val temp: Double?,
    @ColumnInfo(name = "humidity") val humidity: Int?,
    @ColumnInfo(name = "wind") val wind: Double?,
    @ColumnInfo(name = "clouds") val clouds: Int?,
    @ColumnInfo(name = "date") val date: Long?,
) : Parcelable

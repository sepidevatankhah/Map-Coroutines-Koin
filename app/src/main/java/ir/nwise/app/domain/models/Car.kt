package ir.nwise.app.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Car(
    val id: String?,
    val modelIdentifier: String?,
    val modelName: String?,
    val name: String?,
    val make: String?,
    val group: String?,
    val color: String?,
    val series: String?,
    val fuelType: String?,
    val fuelLevel: Float?,
    val transmission: String?,
    val licensePlate: String?,
    val latitude: Double,
    val longitude: Double,
    val innerCleanliness: String?,
    val carImageUrl: String?
) : Parcelable


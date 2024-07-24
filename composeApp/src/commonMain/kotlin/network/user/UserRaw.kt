package services.network.inventory

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.plus
import kotlinx.serialization.Serializable

@Serializable
data class UserRaw(
    var userId: String = "",
    var firstName: String = "N/A",
    var lastName: String = "N/A",
    var isAnonymous: Boolean = false,
    var annualInformation: List<AnnualInformationRaw> = emptyList()
)
@Serializable
data class StayRaw(
    var startDate: LocalDateTime,
    var endDate: LocalDateTime,
    var durationOfStay: Int = endDate.date.toEpochDays() - startDate.date.toEpochDays(),
    var numberOfGuest: Int,
    var familyPartNumber: Int,
    var priceOfStay: PriceOfStayRaw


){
    fun getAllDays(): List<LocalDate> {
        val allDays = mutableListOf<LocalDate>()
        var currentDay = startDate.date
        while (currentDay <= endDate.date) {
            allDays.add(currentDay)
            currentDay = currentDay.plus(1, DateTimeUnit.DAY)
        }
        return allDays
    }
}
@Serializable
data class PriceOfStayRaw(
    var pricesForGuest:Double,
    var pricesForUser:Double,
    var totalPrice: Double = pricesForGuest + pricesForUser
)

@Serializable
data class AnnualInformationRaw(
    var year: Int,
    var totalPricesForGuest:Double,
    var totalPricesForUser:Double,
    var stay: List<StayRaw> = emptyList(),
)
package repositories.user

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.plus
import services.network.inventory.AnnualInformationRaw
import services.network.inventory.PriceOfStayRaw
import services.network.inventory.StayRaw
import services.network.inventory.UserRaw

data class UserEntity(
    var userId: String = "",
    var firstName: String = "N/A",
    var lastName: String = "N/A",
    var isAnonymous: Boolean = false,
    var annualInformation: List<AnnualInformationEntity> = emptyList()
)

data class StayEntity(
    var startDate: LocalDateTime,
    var endDate: LocalDateTime,
    var durationOfStay: Int = endDate.date.toEpochDays() - startDate.date.toEpochDays(),
    var numberOfGuest: Int,
    var familyPartNumber: Int,
    var priceOfStay: PriceOfStayEntity


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

data class PriceOfStayEntity(
    var pricesForGuest:Double,
    var pricesForUser:Double,
    var totalPrice: Double = pricesForGuest + pricesForUser
)


data class AnnualInformationEntity(
    var year: Int,
    var totalPricesForGuest:Double,
    var totalPricesForUser:Double,
    var stay: List<StayEntity> = emptyList(),
)

fun UserRaw.toEntity(): UserEntity {
    return UserEntity(
       userId = this.userId,
     firstName = this.firstName,
     lastName = this.lastName,
    isAnonymous= this.isAnonymous,
    annualInformation = this.annualInformation.map { it.toEntity() }
    )
}

fun AnnualInformationRaw.toEntity(): AnnualInformationEntity {
    return AnnualInformationEntity(
        year = this.year,
     totalPricesForGuest = this.totalPricesForGuest,
        totalPricesForUser = this.totalPricesForUser,
     stay = this.stay.map { it.toEntity() },

    )
}

fun StayRaw.toEntity(): StayEntity {
    return StayEntity(
        startDate = this.startDate ,
        endDate = this.endDate,
    durationOfStay = this.endDate.date.toEpochDays() - this.startDate.date.toEpochDays(),
    numberOfGuest = this.numberOfGuest,
    familyPartNumber =this.familyPartNumber,
    priceOfStay = this.priceOfStay.toEntity()
        )
}

fun PriceOfStayRaw.toEntity(): PriceOfStayEntity {
    return PriceOfStayEntity(
       pricesForGuest = this.pricesForGuest,
     pricesForUser = this.pricesForUser,
     totalPrice  = this.pricesForGuest + this.pricesForUser
    )
}
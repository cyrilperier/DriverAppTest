package utils

import kotlinx.serialization.Serializable

@Serializable
data class Endpoints(
    val endPoint: List<Api> = emptyList()
)

@Serializable
data class Api(
    val name: String,
    val code: String,
    val value: String
)
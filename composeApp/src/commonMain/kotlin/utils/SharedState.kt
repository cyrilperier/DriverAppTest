package utils

import kotlinx.serialization.json.Json


object SharedState {
    var userId: String = ""
    var endpoints: Endpoints = Endpoints(listOf(Api("Alpha","alpha","api.alpha.wa-labs.com"),Api("White Arrow","wa","api.wa.archer-app.com")))
}

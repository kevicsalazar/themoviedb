package domain.errors

sealed interface DataError : Error {
    enum class Network : DataError {
        UNAUTHORIZED,
        NO_INTERNET,
        SERVER_ERROR,
        SERIALIZATION,
        UNKNOWN,
    }
}

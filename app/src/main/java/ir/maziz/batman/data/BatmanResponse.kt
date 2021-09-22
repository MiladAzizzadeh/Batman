package ir.maziz.batman.data

data class BatmanResponse(
    val Response: String,
    val Search: List<Search>,
    val totalResults: String
)
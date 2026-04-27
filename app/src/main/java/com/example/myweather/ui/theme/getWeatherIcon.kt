

fun getWeatherIcon(code: Int): String {
    return when (code) {
        0 -> "☀"
        1, 2 -> "🌤"
        3 -> "☁"
        in 45..48 -> "🌫"
        in 51..67 -> "🌧"
        in 71..77 -> "❄"
        in 80..82 -> "🌦"
        else -> "❓"
    }
}

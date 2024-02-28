package android.kotlin.foodclub.domain.enums

enum class QuantityUnit(val longName: String, val short: String) {
    GRAMS("Grams", "g"),
    KILOGRAMS("Kilograms", "kg"),
    MILLILITERS("Milliliters", "ml"),
    LITERS("Liters", "l");

    companion object {
        fun parseUnit(text: String): QuantityUnit {
            return if(text.equals("Milliliter", ignoreCase = true)){
                MILLILITERS
            } else {
                GRAMS
            }
        }
    }
}
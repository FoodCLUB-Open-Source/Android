package android.kotlin.foodclub.domain.enums

enum class QuantityUnit {
    GRAMS, KILOGRAMS, MILLILITERS, LITERS;

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
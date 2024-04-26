package live.foodclub.domain.enums

enum class QuantityUnit(val longName: String, val short: String) {
    GRAM("Gram", "g"),
    KILOGRAM("Kilogram", "kg"),
    MILLILITER("Milliliter", "ml"),
    LITER("Liter", "l"),
    OUNCE("Ounce", "oz"),
    POUND("Pound", "lb"),
    PINCH("Pinch", "pinch"),
    GALLON("Gallon", "gal"),
    PINT("Pint", "pt"),
    QUART("Quart", "qt"),
    DROP("Drop", "drops"),
    CUP("Cup", "cups"),
    TABLESPOON("Tablespoon", "tbsp"),
    TEASPOON("Teaspoon", "tsp");

    companion object {
        fun parseUnit(text: String): QuantityUnit {
            return when (text) {
                "Gram" -> GRAM
                "Kilogram" -> KILOGRAM
                "Milliliter" -> MILLILITER
                "Liter" -> LITER
                "Ounce" -> OUNCE
                "Pound" -> POUND
                "Pinch" -> PINCH
                "Gallon" -> GALLON
                "Pint" -> PINT
                "Quart" -> QUART
                "Drop" -> DROP
                "Cup" -> CUP
                "Tablespoon" -> TABLESPOON
                "Teaspoon" -> TEASPOON
                else -> GRAM
            }
        }
    }
}
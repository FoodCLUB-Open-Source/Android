package android.kotlin.foodclub.utils.helpers

class ValueParser {
    companion object {
        fun numberToThousands(number: Long): String {
            if(number < 999) {
                return number.toString()
            }
            return String.format("%.1f",number.toDouble()/1000) + "K"
        }
    }
}
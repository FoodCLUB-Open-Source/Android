package android.kotlin.foodclub.domain.enums

import android.kotlin.foodclub.R

enum class Reactions(val drawable:Int) {
    ALL(0),
    YUMMY(R.drawable.yum),
    CREATIVE(R.drawable.creative),
    LETSGOTOGETHER(R.drawable.letsgotogether),
    STAYHEALTHY(R.drawable.stayhealthy),
    ENJOY(R.drawable.enjoy)
}
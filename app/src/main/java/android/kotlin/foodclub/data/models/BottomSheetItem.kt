package android.kotlin.foodclub.data.models

data class BottomSheetItem(
    val id: Int,
    val title: String,
    val resourceId: Int,
    val onClick: () -> Unit
)

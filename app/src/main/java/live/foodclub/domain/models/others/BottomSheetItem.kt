package live.foodclub.domain.models.others

data class BottomSheetItem(
    val id: Int?,
    val title: String,
    val resourceId: Int?,
    val onClick: () -> Unit
)

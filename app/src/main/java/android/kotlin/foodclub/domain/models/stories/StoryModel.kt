package android.kotlin.foodclub.domain.models.stories

import androidx.compose.ui.graphics.painter.Painter

data class StoryModel(
    val authorPhotoPainter: Painter,
    val timeCreated: Long,
    val authorName: String,
    val storyPhoto: Painter
) {}

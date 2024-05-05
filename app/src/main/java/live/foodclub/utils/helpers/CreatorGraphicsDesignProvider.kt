package live.foodclub.utils.helpers

import live.foodclub.R
import live.foodclub.utils.helpers.CreatorGraphicsConstants.CLIMATE
import live.foodclub.utils.helpers.CreatorGraphicsConstants.HEALTH
import live.foodclub.utils.helpers.CreatorGraphicsConstants.INSTRUCTIONS
import live.foodclub.utils.helpers.CreatorGraphicsConstants.LOCAL
import live.foodclub.utils.helpers.CreatorGraphicsConstants.NUTRIENTS
import live.foodclub.utils.helpers.CreatorGraphicsConstants.STYLE
import live.foodclub.utils.helpers.CreatorGraphicsConstants.TITLE
import live.foodclub.utils.helpers.CreatorGraphicsConstants.WORLD

class CreatorGraphicsDesignProvider {
    private val tabResourcesMap = mapOf(
        TITLE to TabDrawableResources(
            titleSection = TitleSectionDesigns(
                strings = listOf(R.drawable.receipe_title)
            )
        ),
        INSTRUCTIONS to TabDrawableResources(
            instructionSection = InstructionsSectionDesigns(
                instructions = listOf(
                    R.drawable.roast_time,
                    R.drawable.time_left,
                    R.drawable.time_took,
                    R.drawable.bake_time,
                    R.drawable.receipe_time
                )
            )
        ),
        NUTRIENTS to TabDrawableResources(
            nutrientsSection = NutrientsSectionDesigns(
                badges = listOf(
                    R.drawable.vitamin_c,
                    R.drawable.vitamin_a,
                    R.drawable.vitamin_b,
                    R.drawable.vitamin_b12,
                    R.drawable.vitamin_b6,
                    R.drawable.vitamin_d,
                    R.drawable.proteins,
                    R.drawable.high_vitamins,
                    R.drawable.vitamin_e,
                ),
                vitamins = listOf(R.drawable.vitamins)
            )
        ),
        CLIMATE to TabDrawableResources(
            climateSection = ClimateSectionDesigns(
                designs = listOf(
                    R.drawable.oil_waste,
                    R.drawable.water_waste,
                    R.drawable.cheese_waste,
                    R.drawable.water_waste_2
                )

            )
        ),
        WORLD to TabDrawableResources(
            worldSectionDesigns = WorldSectionDesigns(
                world = listOf(
                    R.drawable.made_in_england,
                    R.drawable.made_in_europe,
                    R.drawable.made_in_malaysia,
                    R.drawable.made_in_india,
                    R.drawable.flag_uk,
                    R.drawable.flag_guinea
                    ),
                locallyProducedBadges = listOf(
                    R.drawable.badge1,
                    R.drawable.badge2,
                    R.drawable.badge3,
                    R.drawable.badge4,
                    R.drawable.badge5,
                    R.drawable.badge6
                    )
                )
        ),
        STYLE to TabDrawableResources(
            styleSectionDesigns = StyleSectionDesigns(
                style = listOf(
                    R.drawable.street_food,
                    R.drawable.festive,
                    R.drawable.gourmet,
                    R.drawable.noodles,
                    R.drawable.picnic,
                    R.drawable.bbq_and_grill,
                    R.drawable.christmas,
                    R.drawable.thanksgiving,
                )
            )
        ),
        HEALTH to TabDrawableResources(
            healthSectionDesigns = HealthSectionDesigns(
                hotMeter = listOf(
                    R.drawable.hot1,
                    R.drawable.hot2,
                    R.drawable.hot3,
                    R.drawable.hot4,
                    R.drawable.hot5,
                ),
                dietaryBadges = listOf(
                    R.drawable.egg_free,
                    R.drawable.gluten_free,
                    R.drawable.gmo_free,
                    R.drawable.nut_free,
                    R.drawable.dairy_free,
                    R.drawable.transfat_free,
                ),
                organic = listOf(
                    R.drawable.organic_badge_1,
                    R.drawable.organic_badge_2,
                    R.drawable.organic_badge_3,
                    R.drawable.organic_badge_4,
                    R.drawable.organic_badge_5,
                    R.drawable.organic_badge_6,
                )
            )
        )
    )

    fun getDrawableResources(tabName: String): List<TabDrawableResources?> {
        return when (tabName) {
            TITLE -> listOf(
                tabResourcesMap[TITLE]
            )
            INSTRUCTIONS -> listOf(
                tabResourcesMap[INSTRUCTIONS]
            )
            NUTRIENTS -> listOf(
                tabResourcesMap[NUTRIENTS]
            )
            CLIMATE -> listOf(
                tabResourcesMap[CLIMATE]
            )
            WORLD -> listOf(
                tabResourcesMap[WORLD],
                tabResourcesMap[LOCAL]
            )
            STYLE -> listOf(
                tabResourcesMap[STYLE]
            )
            HEALTH -> listOf(
                tabResourcesMap[HEALTH]
            )
            else -> emptyList()
        }
    }
}

data class TabDrawableResources(
    val titleSection: TitleSectionDesigns? = null,
    val instructionSection: InstructionsSectionDesigns? = null,
    val nutrientsSection: NutrientsSectionDesigns? = null,
    val climateSection: ClimateSectionDesigns? = null,
    val worldSectionDesigns: WorldSectionDesigns? = null,
    val styleSectionDesigns: StyleSectionDesigns? = null,
    val healthSectionDesigns: HealthSectionDesigns? = null
)

data class TitleSectionDesigns(
    val strings: List<Int>
)

data class InstructionsSectionDesigns(
    val instructions: List<Int>
)

data class NutrientsSectionDesigns(
    val badges: List<Int>,
    val vitamins: List<Int>
)

data class ClimateSectionDesigns(
    val designs: List<Int>
)

data class WorldSectionDesigns(
    val world: List<Int>,
    val locallyProducedBadges: List<Int>
)

data class StyleSectionDesigns(
    val style: List<Int>
)

data class HealthSectionDesigns(
    val hotMeter: List<Int>,
    val dietaryBadges: List<Int>,
    val organic: List<Int>
)

object CreatorGraphicsConstants {
    const val TITLE = "Title"
    const val INSTRUCTIONS = "Instructions"
    const val NUTRIENTS = "Nutrients"
    const val TIPS = "Tips"
    const val CLIMATE = "Climate"
    const val WORLD = "World"
    const val LOCAL = "Local"
    const val STYLE = "Style"
    const val HEALTH = "Health"
    const val BUSINESS = "Business"
    const val GRAPHICS = "Graphics"
    const val MOST_POPULAR = "Most Popular"
    const val LOCALLY_PRODUCED = "Locally produced"
    const val SPECIAL_DIETS = "Special Diets:"
    const val ORGANIC = "Organic"
    const val SEE_ALL = "See All"
}

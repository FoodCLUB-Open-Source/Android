package live.foodclub.domain.models.others

import live.foodclub.R

class OnBoardingItems(
    val image: Int,
    val title: String,
    val descr: String
) {
    companion object{
        fun getData(): List<OnBoardingItems>{
            return listOf(
                OnBoardingItems(
                    image = R.drawable.onboarding_discover,
                    title = "Discover",
                    descr = "Explore and discover what others are cooking. Bring out you inner masterchef."
                ),
                OnBoardingItems(
                    image = R.drawable.onboarding_learn,
                    title = "Learn",
                    descr = "Explore and discover what others are cooking. Bring out you inner masterchef."
                ),
                OnBoardingItems(
                    image = R.drawable.onboarding_shop,
                    title = "Shop",
                    descr = "Explore and discover what others are cooking. Bring out you inner masterchef."
                )
            )
        }
    }
}

package live.foodclub.views.home.profile

import android.content.res.Configuration
import live.foodclub.R
import live.foodclub.config.ui.FoodClubTheme
import live.foodclub.config.ui.Montserrat
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun HelpAndSupportView(
    navController: NavController
) {
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            HelpAndSupportTopBar(navController = navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(dimensionResource(id = R.dimen.dim_4)),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = stringResource(R.string.faq),
                fontFamily = Montserrat,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.dim_8))
            )
            Text(
                text = createHelpAndSupportText(), modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f)
                    .padding(dimensionResource(id = R.dimen.dim_8))
                    .verticalScroll(scrollState),
                lineHeight = dimensionResource(id = R.dimen.fon_18).value.sp
            )
           ContactUsFooter(modifier = Modifier.weight(0.2f))
        }
    }
}

@Composable
private fun createHelpAndSupportText(): AnnotatedString {
    return buildAnnotatedString {
        val extraBoldStyle = SpanStyle(fontWeight = FontWeight.ExtraBold, fontFamily = Montserrat)
        val semiBoldStyle = SpanStyle(fontWeight = FontWeight.SemiBold, fontFamily = Montserrat)
        val normalStyle = SpanStyle(fontWeight = FontWeight.Normal, fontFamily = Montserrat)
        withStyle(style = extraBoldStyle) {
            appendLine(stringResource(R.string.video_editing_and_customisation))
        }
        withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
            appendLine(stringResource(R.string.video_editing_and_customisation_question_1))
        }
        withStyle(style = normalStyle) {
            appendLine(stringResource(R.string.video_editing_and_customisation_answer_1))
        }
        withStyle(style = semiBoldStyle) {
            appendLine(stringResource(R.string.video_editing_and_customisation_question_2))
        }
        withStyle(style = normalStyle) {
            appendLine(stringResource(R.string.video_editing_and_customisation_answer_2))
        }
        withStyle(style = extraBoldStyle) {
            appendLine(stringResource(R.string.ai_food_detection_and_ingredient_substitution))
        }
        withStyle(style = semiBoldStyle) {
            appendLine(stringResource(R.string.ai_food_detection_and_ingredient_substitution_question_1))
        }
        withStyle(style = normalStyle) {
            appendLine(stringResource(R.string.ai_food_detection_and_ingredient_substitution_answer_1))
        }
        withStyle(style = semiBoldStyle) {
            appendLine(stringResource(R.string.ai_food_detection_and_ingredient_substitution_question_2))
        }
        withStyle(style = normalStyle) {
            appendLine(stringResource(R.string.ai_food_detection_and_ingredient_substitution_answer_2))
        }
        withStyle(style = extraBoldStyle) {
            appendLine(stringResource(R.string.macro_calculation_and_nutritional_information))
        }
        withStyle(style = semiBoldStyle) {
            appendLine(stringResource(R.string.macro_calculation_and_nutritional_information_question_1))
        }
        withStyle(style = normalStyle) {
            appendLine(stringResource(R.string.macro_calculation_and_nutritional_information_answer_1))
        }
        withStyle(style = semiBoldStyle) {
            appendLine(stringResource(R.string.macro_calculation_and_nutritional_information_question_2))
        }
        withStyle(style = normalStyle) {
            appendLine(stringResource(R.string.macro_calculation_and_nutritional_information_answer_2))
        }
        withStyle(style = extraBoldStyle) {
            appendLine(stringResource(R.string.community_interaction_on_foodsnaps))
        }
        withStyle(style = semiBoldStyle) {
            appendLine(stringResource(R.string.community_interaction_on_foodsnaps_question_1))
        }
        withStyle(style = normalStyle) {
            appendLine(stringResource(R.string.community_interaction_on_foodsnaps_answer_1))
        }
        withStyle(style = semiBoldStyle) {
            appendLine(stringResource(R.string.community_interaction_on_foodsnaps_question_2))
        }
        withStyle(style = normalStyle) {
            appendLine(stringResource(R.string.community_interaction_on_foodsnaps_answer_2))
        }
        withStyle(style = extraBoldStyle) {
            appendLine(stringResource(R.string.environmental_and_health_footprint_tracking))
        }
        withStyle(style = semiBoldStyle) {
            appendLine(stringResource(R.string.environmental_and_health_footprint_tracking_question_1))
        }
        withStyle(style = normalStyle) {
            appendLine(stringResource(R.string.environmental_and_health_footprint_tracking_answer_1))
        }
        withStyle(style = semiBoldStyle) {
            appendLine(stringResource(R.string.environmental_and_health_footprint_tracking_question_2))
        }
        withStyle(style = normalStyle) {
            appendLine(stringResource(R.string.environmental_and_health_footprint_tracking_answer_2))
        }
        withStyle(style = extraBoldStyle) {
            appendLine(stringResource(R.string.foodmaps_and_social_events))
        }
        withStyle(style = semiBoldStyle) {
            appendLine(stringResource(R.string.foodmaps_and_social_events_question_1))
        }
        withStyle(style = normalStyle) {
            appendLine(stringResource(R.string.foodmaps_and_social_events_answer_1))
        }
        withStyle(style = semiBoldStyle) {
            appendLine(stringResource(R.string.foodmaps_and_social_events_question_2))
        }
        withStyle(style = normalStyle) {
            appendLine(stringResource(R.string.foodmaps_and_social_events_answer_2))
        }
        withStyle(style = extraBoldStyle) {
            appendLine(stringResource(R.string.food_pitches_and_collaborations))
        }
        withStyle(style = semiBoldStyle) {
            appendLine(stringResource(R.string.food_pitches_and_collaborations_question_1))
        }
        withStyle(style = normalStyle) {
            appendLine(stringResource(R.string.food_pitches_and_collaborations_answer_1))
        }
        withStyle(style = semiBoldStyle) {
            appendLine(stringResource(R.string.food_pitches_and_collaborations_question_2))
        }
        withStyle(style = normalStyle) {
            appendLine(stringResource(R.string.food_pitches_and_collaborations_answer_2))
        }
        withStyle(style = extraBoldStyle) {
            appendLine(stringResource(R.string.alerting_neighbors_and_community_support))
        }
        withStyle(style = semiBoldStyle) {
            appendLine(stringResource(R.string.alerting_neighbors_and_community_support_question_1))
        }
        withStyle(style = normalStyle) {
            appendLine(stringResource(R.string.alerting_neighbors_and_community_support_answer_1))
        }
        withStyle(style = semiBoldStyle) {
            appendLine(stringResource(R.string.alerting_neighbors_and_community_support_question_2))
        }
        withStyle(style = normalStyle) {
            appendLine(stringResource(R.string.alerting_neighbors_and_community_support_answer_2))
        }
        withStyle(style = extraBoldStyle) {
            appendLine(stringResource(R.string.digital_basket_integration_and_shopping))
        }
        withStyle(style = semiBoldStyle) {
            appendLine(stringResource(R.string.digital_basket_integration_and_shopping_question_1))
        }
        withStyle(style = normalStyle) {
            appendLine(stringResource(R.string.digital_basket_integration_and_shopping_answer_1))
        }
        withStyle(style = semiBoldStyle) {
            appendLine(stringResource(R.string.digital_basket_integration_and_shopping_question_2))
        }
        withStyle(style = normalStyle) {
            appendLine(stringResource(R.string.digital_basket_integration_and_shopping_answer_2))
        }
        withStyle(style = extraBoldStyle) {
            appendLine(stringResource(R.string.user_engagement_and_rewards))
        }
        withStyle(style = semiBoldStyle) {
            appendLine(stringResource(R.string.user_engagement_and_rewards_question_1))
        }
        withStyle(style = normalStyle) {
            appendLine(stringResource(R.string.user_engagement_and_rewards_answer_1))
        }
        withStyle(style = semiBoldStyle) {
            appendLine(stringResource(R.string.user_engagement_and_rewards_question_2))
        }
        withStyle(style = normalStyle) {
            appendLine(stringResource(R.string.user_engagement_and_rewards_answer_2))
        }
    }
}

@Preview(name = "Dark mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(name = "Light mode", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun HelpAndSupportViewPreview() {
    FoodClubTheme {
        HelpAndSupportView(
            rememberNavController()
        )
    }
}
package android.kotlin.foodclub.views.authentication

import android.annotation.SuppressLint
import android.kotlin.foodclub.R
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.disabledContainerColor
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.navigation.auth.AuthScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@SuppressLint("UnrememberedMutableInteractionSource", "StateFlowValueCalledInComposition")
@Composable
fun TermsAndConditions(navController: NavHostController) {
    Box(
        modifier = Modifier
            .width(dimensionResource(id = R.dimen.dim_428))
            .height(dimensionResource(id = R.dimen.dim_926))
            .background(Color.White)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding
                    (
                    top = dimensionResource(id = R.dimen.dim_38),
                    start = dimensionResource(id = R.dimen.dim_40),
                    end = dimensionResource(id = R.dimen.dim_30),
                    )

        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_arrow_left),
                contentDescription = null,
                modifier = Modifier
                    .width(dimensionResource(id = R.dimen.dim_36))
                    .height(dimensionResource(id = R.dimen.dim_36))

            )
            Text(
                text = stringResource(id = R.string.terms_condition_title),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = dimensionResource(id = R.dimen.dim_43), top = dimensionResource(id = R.dimen.dim_7))
                    .width(dimensionResource(id = R.dimen.dim_168))
                    .height(dimensionResource(id = R.dimen.dim_22)),

                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF000000),

                    )
            )


        }

        Column(
            Modifier
                .fillMaxSize()
                .padding(
                    dimensionResource(id = R.dimen.dim_50),
                    top = dimensionResource(id = R.dimen.dim_95),
                    end = dimensionResource(id = R.dimen.dim_50)
                )

        ) {
            Column(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.dim_677))
                    .padding(
                        bottom = dimensionResource(id = R.dimen.dim_20)
                    )
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                val annotatedString = buildAnnotatedString {

                    val boldStyle = SpanStyle(fontWeight = FontWeight.Bold)

                    withStyle(style = boldStyle) {
                        append(stringResource(id = R.string.contracting_with_bold))
                    }
                    append(stringResource(id = R.string.contracting_with_description))

                    withStyle(style = boldStyle) {
                        append(stringResource(id = R.string.services_covered_bold))
                    }
                    append(stringResource(id = R.string.services_covered_description))

                    withStyle(style = boldStyle) {
                        append(stringResource(id = R.string.additional_terms_bold))
                    }
                    append(stringResource(id = R.string.additional_terms_description))
                    append(stringResource(id = R.string.intellectual_property_policy))
                    append(stringResource(id = R.string.buyer_terms))

                    withStyle(style = boldStyle) {
                        append(stringResource(id = R.string.using_platform_bold))
                    }

                    withStyle(style = boldStyle) {
                        append(stringResource(id = R.string.platform_and_business_model_bold))
                    }
                    append(stringResource(id = R.string.platform_description_1))
                    append(stringResource(id = R.string.platform_description_2))
                    append(stringResource(id = R.string.platform_description_3))
                    append(stringResource(id = R.string.platform_description_4))
                    append(stringResource(id = R.string.platform_description_5))
                    append(stringResource(id = R.string.short_summary_using_platform))

                    withStyle(style = boldStyle) {
                        append(stringResource(id = R.string.minimum_age))
                    }
                    append(stringResource(id = R.string.minimum_age_description))
                    append(stringResource(id = R.string.minimum_age_summary))

                    withStyle(style = boldStyle) {
                        append(stringResource(id = R.string.what_you_can_do_bold))
                    }
                    append(stringResource(id = R.string.what_you_can_do_description))

                    append(stringResource(id = R.string.permission_we_give))
                    append(stringResource(id = R.string.permission_we_give_description))
                    append(stringResource(id = R.string.access_dependent_on_age))
                    append(stringResource(id = R.string.short_summary_what_you_can_do))

                    withStyle(style = boldStyle) {
                        append(stringResource(id = R.string.what_you_cant_do_bold))
                    }
                    append(stringResource(id = R.string.community_guidelines_description))
                    append(stringResource(id = R.string.prohibited_actions))
                    append(stringResource(id = R.string.prohibited_actions_description))
                    append(stringResource(id = R.string.content_restrictions))
                    append(stringResource(id = R.string.content_restrictions_description))
                    append(stringResource(id = R.string.branded_content_policy))
                    append(stringResource(id = R.string.additional_guidelines))
                    append(stringResource(id = R.string.additional_guidelines_description))
                    append(stringResource(id = R.string.respect_copyright))
                    append(stringResource(id = R.string.report_copyright))
                    append(stringResource(id = R.string.report_trademark))
                    append(stringResource(id = R.string.report_illegal_content))
                    append(stringResource(id = R.string.short_summary_cant_do))

                    withStyle(style = boldStyle) {
                        append(stringResource(id = R.string.your_content_bold))
                    }
                    append(stringResource(id = R.string.your_content_description))
                    append(stringResource(id = R.string.content_responsibility))
                    append(stringResource(id = R.string.automatic_posting))
                    append(stringResource(id = R.string.remove_or_restrict))
                    append(stringResource(id = R.string.notification_remove))
                    append(stringResource(id = R.string.content_usage))
                    append(stringResource(id = R.string.restrict_interaction))

                    withStyle(style = boldStyle) {
                        append(stringResource(id = R.string.ownership_and_licences_bold))
                    }
                    append(stringResource(id = R.string.ownership_and_licences_description))
                    append(stringResource(id = R.string.non_exclusive))
                    append(stringResource(id = R.string.royalty_free))
                    append(stringResource(id = R.string.transferable))
                    append(stringResource(id = R.string.sub_licensable))
                    append(stringResource(id = R.string.worldwide_licence))
                    append(stringResource(id = R.string.licence_to_affiliates))
                    append(stringResource(id = R.string.user_licence))
                    append(stringResource(id = R.string.licence_end))
                    append(stringResource(id = R.string.licence_continuation))
                    append(stringResource(id = R.string.legal_obligations))
                    append(stringResource(id = R.string.comments_licence))
                    append(stringResource(id = R.string.short_summary_licences))


                    withStyle(style = boldStyle) {
                        append(stringResource(id = R.string.promise_to_you_bold))
                    }
                    append(stringResource(id = R.string.platform_promise_description))
                    append(stringResource(id = R.string.user_generated_content_disclaimer))
                    append(stringResource(id = R.string.user_content_disclaimer_1))
                    append(stringResource(id = R.string.user_content_disclaimer_2))
                    append(stringResource(id = R.string.user_content_disclaimer_3))
                    append(stringResource(id = R.string.user_content_disclaimer_4))
                    append(stringResource(id = R.string.user_content_disclaimer_summary))
                    append(stringResource(id = R.string.third_party_links_disclaimer))
                    append(stringResource(id = R.string.limitation_of_responsibility))
                    append(stringResource(id = R.string.limitation_of_responsibility_1))
                    append(stringResource(id = R.string.limitation_of_responsibility_2))
                    append(stringResource(id = R.string.beyond_our_control_disclaimer))
                    append(stringResource(id = R.string.statutory_rights_notice))
                    append(stringResource(id = R.string.unlawful_exclusion_notice))
                    append(stringResource(id = R.string.eea_consumer_guarantee))
                    append(stringResource(id = R.string.platform_experience_summary))


                    withStyle(style = boldStyle) {
                        append(stringResource(id = R.string.suspending_ending_relationship_bold))
                    }
                    withStyle(style = boldStyle) {
                        append(stringResource(id = R.string.your_rights_description))
                    }
                    append(stringResource(id = R.string.close_account_instructions))
                    append(stringResource(id = R.string.content_availability))
                    append(stringResource(id = R.string.eea_consumer_close_account))
                    append(stringResource(id = R.string.closing_account_summary))

                    withStyle(style = boldStyle) {
                        append(stringResource(id = R.string.foodclubs_rights_description))
                    }
                    append(stringResource(id = R.string.investigate_and_take_down))
                    append(stringResource(id = R.string.suspend_or_terminate_account))
                    append(stringResource(id = R.string.material_or_repeated_breach))
                    append(stringResource(id = R.string.about_to_seriously_breach))
                    append(stringResource(id = R.string.legally_required))
                    append(stringResource(id = R.string.dealing_with_technical_issue))
                    append(stringResource(id = R.string.previously_terminated_account))
                    append(stringResource(id = R.string.notify_in_advance))
                    append(stringResource(id = R.string.appeal_mistake))
                    append(stringResource(id = R.string.suspension_termination_loss))


                    withStyle(style = boldStyle) {
                        append(stringResource(id = R.string.changes_to_terms_bold))
                        append(stringResource(id = R.string.what_happens_when_changes_description))
                    }
                    append(stringResource(id = R.string.make_changes_to_terms_platform))
                    append(stringResource(id = R.string.advance_notice_of_changes))
                    append(stringResource(id = R.string.urgent_changes_security))
                    append(stringResource(id = R.string.disagree_stop_using_platform))
                    append(stringResource(id = R.string.short_summary_changes))

                    withStyle(style = boldStyle) {
                        append(stringResource(id = R.string.reasons_for_changes_bold))
                    }

                    append(stringResource(id = R.string.reasons_for_changes_description))
                    append(stringResource(id = R.string.beyond_our_reasonable_control))
                    append(stringResource(id = R.string.changes_in_law))
                    append(stringResource(id = R.string.platform_development_changes))
                    append(stringResource(id = R.string.adapt_to_new_technologies))
                    append(stringResource(id = R.string.reflect_changes_in_users))
                    append(stringResource(id = R.string.address_security_issue))
                    append(stringResource(id = R.string.short_summary_changes_2))


                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(stringResource(id = R.string.resolving_disputes_bold))
                    }
                    append(stringResource(id = R.string.governed_by_law))
                    append(stringResource(id = R.string.try_resolve_amicably))
                    append(stringResource(id = R.string.cannot_resolve_dispute))
                    append(stringResource(id = R.string.courts_in_england_wales))
                    append(stringResource(id = R.string.resident_in_eea))
                    append(stringResource(id = R.string.short_summary_disputes))

                    withStyle(style = boldStyle) {
                        append(stringResource(id = R.string.other_bold))
                    }
                    append(stringResource(id = R.string.terms_permissions_not_transferred))
                    append(stringResource(id = R.string.other_in_short_text))
                    append(stringResource(id = R.string.we_may_reclaim_account))
                    append(stringResource(id = R.string.reclaim_account_short))
                    append(stringResource(id = R.string.delay_enforcing_provision))
                    append(stringResource(id = R.string.short_terms_agreement))

                    withStyle(style = boldStyle) {
                        append(stringResource(id = R.string.contact_foodclub_bold))
                    }
                    append(stringResource(id = R.string.contact_information))
                    append(stringResource(id = R.string.additional_regulatory_info))

                }

                Text(
                    text = annotatedString,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight(300),
                        color = Color(0xFF000000),
                    )
                )

            }


        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = dimensionResource(id = R.dimen.dim_765),
                    start = dimensionResource(id = R.dimen.dim_46),
                    end = dimensionResource(id = R.dimen.dim_46)
                )
        ) {
            Button(
                onClick = {
                    navController.navigate(AuthScreen.MainLogInAndSignUp.route)
                },
                modifier = Modifier
                    .weight(1f)
                    .width(dimensionResource(id = R.dimen.dim_164))
                    .height(dimensionResource(id = R.dimen.dim_56))
                    .border(
                        width = 1.5.dp,
                        color = foodClubGreen,
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.dim_8)),
                    )
                    .background(Color.White),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = foodClubGreen,
                    disabledContainerColor = disabledContainerColor,
                    disabledContentColor = Color.White,
                )
            ) {
                Text(text = stringResource(id = R.string.decline),
                    fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,)
            }


            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_25)))

            Button(
                onClick = {
                          navController.navigate(AuthScreen.SignUp.route)
                }, modifier = Modifier
                    .weight(1f)
                    .width(dimensionResource(id = R.dimen.dim_164))
                    .height(dimensionResource(id = R.dimen.dim_56)),
                shape = RoundedCornerShape( dimensionResource(id = R.dimen.dim_8)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = foodClubGreen,
                    disabledContainerColor = disabledContainerColor,
                    disabledContentColor = Color.White,
                    contentColor = Color.White
                )

            ) {
                Text(text = stringResource(id = R.string.accept),
                    fontSize = dimensionResource(id = R.dimen.fon_16).value.sp,)
            }
        }

    }
}

@Composable
fun TermsAndConditionsSimplified() {
    Box(
        modifier = Modifier
            .width(dimensionResource(id = R.dimen.dim_428))
            .height(dimensionResource(id = R.dimen.dim_926))
            .background(Color.White)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding
                    (
                    top = dimensionResource(id = R.dimen.dim_38),
                    start = dimensionResource(id = R.dimen.dim_40),
                    end = dimensionResource(id = R.dimen.dim_30),
                )

        ) {
            Text(
                text = stringResource(id = R.string.terms_condition_title),
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        start = dimensionResource(id = R.dimen.dim_75),
                        top = dimensionResource(id = R.dimen.dim_7)
                    )
                    .width(dimensionResource(id = R.dimen.dim_168))
                    .height(dimensionResource(id = R.dimen.dim_22)),

                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = Montserrat,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF000000),

                    )
            )


        }

        Column(
            Modifier
                .fillMaxSize()
                .padding(
                    dimensionResource(id = R.dimen.dim_50),
                    top = dimensionResource(id = R.dimen.dim_95),
                    end = dimensionResource(id = R.dimen.dim_50)
                )

        ) {
            Column(
                modifier = Modifier
                    .height(dimensionResource(id = R.dimen.dim_677))
                    .padding(
                        bottom = dimensionResource(id = R.dimen.dim_20)
                    )
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                val annotatedString = buildAnnotatedString {

                    val boldStyle = SpanStyle(fontWeight = FontWeight.Bold)

                    withStyle(style = boldStyle) {
                        append(stringResource(id = R.string.contracting_with_bold))
                    }
                    append(stringResource(id = R.string.contracting_with_description))

                    withStyle(style = boldStyle) {
                        append(stringResource(id = R.string.services_covered_bold))
                    }
                    append(stringResource(id = R.string.services_covered_description))

                    withStyle(style = boldStyle) {
                        append(stringResource(id = R.string.additional_terms_bold))
                    }
                    append(stringResource(id = R.string.additional_terms_description))
                    append(stringResource(id = R.string.intellectual_property_policy))
                    append(stringResource(id = R.string.buyer_terms))

                    withStyle(style = boldStyle) {
                        append(stringResource(id = R.string.using_platform_bold))
                    }

                    withStyle(style = boldStyle) {
                        append(stringResource(id = R.string.platform_and_business_model_bold))
                    }
                    append(stringResource(id = R.string.platform_description_1))
                    append(stringResource(id = R.string.platform_description_2))
                    append(stringResource(id = R.string.platform_description_3))
                    append(stringResource(id = R.string.platform_description_4))
                    append(stringResource(id = R.string.platform_description_5))
                    append(stringResource(id = R.string.short_summary_using_platform))

                    withStyle(style = boldStyle) {
                        append(stringResource(id = R.string.minimum_age))
                    }
                    append(stringResource(id = R.string.minimum_age_description))
                    append(stringResource(id = R.string.minimum_age_summary))

                    withStyle(style = boldStyle) {
                        append(stringResource(id = R.string.what_you_can_do_bold))
                    }
                    append(stringResource(id = R.string.what_you_can_do_description))

                    append(stringResource(id = R.string.permission_we_give))
                    append(stringResource(id = R.string.permission_we_give_description))
                    append(stringResource(id = R.string.access_dependent_on_age))
                    append(stringResource(id = R.string.short_summary_what_you_can_do))

                    withStyle(style = boldStyle) {
                        append(stringResource(id = R.string.what_you_cant_do_bold))
                    }
                    append(stringResource(id = R.string.community_guidelines_description))
                    append(stringResource(id = R.string.prohibited_actions))
                    append(stringResource(id = R.string.prohibited_actions_description))
                    append(stringResource(id = R.string.content_restrictions))
                    append(stringResource(id = R.string.content_restrictions_description))
                    append(stringResource(id = R.string.branded_content_policy))
                    append(stringResource(id = R.string.additional_guidelines))
                    append(stringResource(id = R.string.additional_guidelines_description))
                    append(stringResource(id = R.string.respect_copyright))
                    append(stringResource(id = R.string.report_copyright))
                    append(stringResource(id = R.string.report_trademark))
                    append(stringResource(id = R.string.report_illegal_content))
                    append(stringResource(id = R.string.short_summary_cant_do))

                    withStyle(style = boldStyle) {
                        append(stringResource(id = R.string.your_content_bold))
                    }
                    append(stringResource(id = R.string.your_content_description))
                    append(stringResource(id = R.string.content_responsibility))
                    append(stringResource(id = R.string.automatic_posting))
                    append(stringResource(id = R.string.remove_or_restrict))
                    append(stringResource(id = R.string.notification_remove))
                    append(stringResource(id = R.string.content_usage))
                    append(stringResource(id = R.string.restrict_interaction))

                    withStyle(style = boldStyle) {
                        append(stringResource(id = R.string.ownership_and_licences_bold))
                    }
                    append(stringResource(id = R.string.ownership_and_licences_description))
                    append(stringResource(id = R.string.non_exclusive))
                    append(stringResource(id = R.string.royalty_free))
                    append(stringResource(id = R.string.transferable))
                    append(stringResource(id = R.string.sub_licensable))
                    append(stringResource(id = R.string.worldwide_licence))
                    append(stringResource(id = R.string.licence_to_affiliates))
                    append(stringResource(id = R.string.user_licence))
                    append(stringResource(id = R.string.licence_end))
                    append(stringResource(id = R.string.licence_continuation))
                    append(stringResource(id = R.string.legal_obligations))
                    append(stringResource(id = R.string.comments_licence))
                    append(stringResource(id = R.string.short_summary_licences))


                    withStyle(style = boldStyle) {
                        append(stringResource(id = R.string.promise_to_you_bold))
                    }
                    append(stringResource(id = R.string.platform_promise_description))
                    append(stringResource(id = R.string.user_generated_content_disclaimer))
                    append(stringResource(id = R.string.user_content_disclaimer_1))
                    append(stringResource(id = R.string.user_content_disclaimer_2))
                    append(stringResource(id = R.string.user_content_disclaimer_3))
                    append(stringResource(id = R.string.user_content_disclaimer_4))
                    append(stringResource(id = R.string.user_content_disclaimer_summary))
                    append(stringResource(id = R.string.third_party_links_disclaimer))
                    append(stringResource(id = R.string.limitation_of_responsibility))
                    append(stringResource(id = R.string.limitation_of_responsibility_1))
                    append(stringResource(id = R.string.limitation_of_responsibility_2))
                    append(stringResource(id = R.string.beyond_our_control_disclaimer))
                    append(stringResource(id = R.string.statutory_rights_notice))
                    append(stringResource(id = R.string.unlawful_exclusion_notice))
                    append(stringResource(id = R.string.eea_consumer_guarantee))
                    append(stringResource(id = R.string.platform_experience_summary))


                    withStyle(style = boldStyle) {
                        append(stringResource(id = R.string.suspending_ending_relationship_bold))
                    }
                    withStyle(style = boldStyle) {
                        append(stringResource(id = R.string.your_rights_description))
                    }
                    append(stringResource(id = R.string.close_account_instructions))
                    append(stringResource(id = R.string.content_availability))
                    append(stringResource(id = R.string.eea_consumer_close_account))
                    append(stringResource(id = R.string.closing_account_summary))

                    withStyle(style = boldStyle) {
                        append(stringResource(id = R.string.foodclubs_rights_description))
                    }
                    append(stringResource(id = R.string.investigate_and_take_down))
                    append(stringResource(id = R.string.suspend_or_terminate_account))
                    append(stringResource(id = R.string.material_or_repeated_breach))
                    append(stringResource(id = R.string.about_to_seriously_breach))
                    append(stringResource(id = R.string.legally_required))
                    append(stringResource(id = R.string.dealing_with_technical_issue))
                    append(stringResource(id = R.string.previously_terminated_account))
                    append(stringResource(id = R.string.notify_in_advance))
                    append(stringResource(id = R.string.appeal_mistake))
                    append(stringResource(id = R.string.suspension_termination_loss))


                    withStyle(style = boldStyle) {
                        append(stringResource(id = R.string.changes_to_terms_bold))
                        append(stringResource(id = R.string.what_happens_when_changes_description))
                    }
                    append(stringResource(id = R.string.make_changes_to_terms_platform))
                    append(stringResource(id = R.string.advance_notice_of_changes))
                    append(stringResource(id = R.string.urgent_changes_security))
                    append(stringResource(id = R.string.disagree_stop_using_platform))
                    append(stringResource(id = R.string.short_summary_changes))

                    withStyle(style = boldStyle) {
                        append(stringResource(id = R.string.reasons_for_changes_bold))
                    }

                    append(stringResource(id = R.string.reasons_for_changes_description))
                    append(stringResource(id = R.string.beyond_our_reasonable_control))
                    append(stringResource(id = R.string.changes_in_law))
                    append(stringResource(id = R.string.platform_development_changes))
                    append(stringResource(id = R.string.adapt_to_new_technologies))
                    append(stringResource(id = R.string.reflect_changes_in_users))
                    append(stringResource(id = R.string.address_security_issue))
                    append(stringResource(id = R.string.short_summary_changes_2))


                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(stringResource(id = R.string.resolving_disputes_bold))
                    }
                    append(stringResource(id = R.string.governed_by_law))
                    append(stringResource(id = R.string.try_resolve_amicably))
                    append(stringResource(id = R.string.cannot_resolve_dispute))
                    append(stringResource(id = R.string.courts_in_england_wales))
                    append(stringResource(id = R.string.resident_in_eea))
                    append(stringResource(id = R.string.short_summary_disputes))

                    withStyle(style = boldStyle) {
                        append(stringResource(id = R.string.other_bold))
                    }
                    append(stringResource(id = R.string.terms_permissions_not_transferred))
                    append(stringResource(id = R.string.other_in_short_text))
                    append(stringResource(id = R.string.we_may_reclaim_account))
                    append(stringResource(id = R.string.reclaim_account_short))
                    append(stringResource(id = R.string.delay_enforcing_provision))
                    append(stringResource(id = R.string.short_terms_agreement))

                    withStyle(style = boldStyle) {
                        append(stringResource(id = R.string.contact_foodclub_bold))
                    }
                    append(stringResource(id = R.string.contact_information))
                    append(stringResource(id = R.string.additional_regulatory_info))

                }

                Text(
                    text = annotatedString,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontFamily = Montserrat,
                        fontWeight = FontWeight(300),
                        color = Color(0xFF000000),
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TermsAndConditionsPreview() {
    val navController = rememberNavController()
    TermsAndConditions(navController)
}


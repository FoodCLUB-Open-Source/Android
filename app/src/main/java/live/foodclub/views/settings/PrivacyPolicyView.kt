package live.foodclub.views.settings

import android.graphics.Typeface
import live.foodclub.R
import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController


@Composable
fun PrivacyPolicyView(navController: NavController) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.dim_16))
                .padding(top = dimensionResource(id = R.dimen.dim_80))
                .background(Color.White),
        ) {
            SettingsTopBar(
                label = stringResource(id = R.string.privacy_policy),
                navController = navController
            )

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_40)))
            DrawScrollableView(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                content = {
                    Column {
                        TextField(
                            value = TextFieldValue(
                                annotatedString = Html.fromHtml(stringResource(id = R.string.privacy_policy_text), FROM_HTML_MODE_LEGACY).toAnnotatedString()
                            ),
                            readOnly = true,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                disabledContainerColor = Color.White,
                            ),
                            onValueChange = {},
                        )
                    }
                }
            )
        }
    }
}

fun Spanned.toAnnotatedString(): AnnotatedString = buildAnnotatedString {
    val spanned = this@toAnnotatedString
    append(spanned.toString())
    getSpans(0, spanned.length, Any::class.java).forEach { span ->
        val start = getSpanStart(span)
        val end = getSpanEnd(span)
        when (span) {
            is StyleSpan -> when (span.style) {
                Typeface.BOLD -> addStyle(SpanStyle(fontWeight = FontWeight.Bold), start, end)
                Typeface.ITALIC -> addStyle(SpanStyle(fontStyle = FontStyle.Italic), start, end)
                Typeface.BOLD_ITALIC -> addStyle(SpanStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic), start, end)
            }
            is UnderlineSpan -> addStyle(SpanStyle(textDecoration = TextDecoration.Underline), start, end)
            is ForegroundColorSpan -> addStyle(SpanStyle(color = Color(span.foregroundColor)), start, end)
        }
    }
}

@Composable
fun DrawScrollableView(content: @Composable () -> Unit, modifier: Modifier) {
    AndroidView(
        modifier = modifier,
        factory = {
            val scrollView = ScrollView(it)
            val layout = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            scrollView.layoutParams = layout
            scrollView.isVerticalFadingEdgeEnabled = true
            scrollView.isScrollbarFadingEnabled = false
            scrollView.addView(ComposeView(it).apply {
                setContent {
                    content()
                }
            })
            val linearLayout = LinearLayout(it)
            linearLayout.orientation = LinearLayout.VERTICAL
            linearLayout.layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            linearLayout.addView(scrollView)
            linearLayout
        }
    )
}
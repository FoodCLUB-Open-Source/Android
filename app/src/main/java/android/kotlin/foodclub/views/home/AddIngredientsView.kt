package android.kotlin.foodclub.views.home

//import androidx.compose.foundation.layout.BoxScopeInstance.align
//import androidx.compose.*
import android.kotlin.foodclub.R
import android.kotlin.foodclub.viewmodels.home.AddIngredientsViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

var fontFamily = FontFamily(Font(R.font.montserratregular, FontWeight.Normal))
@Preview(showBackground = true)
@Composable
fun AddIngredientsView() {
    val viewModel: AddIngredientsViewModel = viewModel()
    val title = viewModel.title.value ?: "Loading..."

    Box {
        Column(modifier = Modifier.padding(15.dp))//, horizontalAlignment =Alignment.CenterHorizontally)
        {

            Column(modifier = Modifier.weight(1f))
            {
                //CaptionText()
                /*
                SearchBarIngredient()
                IngredientsList()
                 */
                Header("Add Ingredients")
                Ingredients()

            }
            Button(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp)
                    .padding(top = 40.dp)
                    .padding(horizontal = 10.dp),
                onClick = {

                }, colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF7EC60B),
                    disabledContainerColor = Color(0xFF7EC60B),
                    disabledContentColor = Color(0xFF7EC60B),
                    contentColor = Color(0xFF7EC60B)
                ),
                contentPadding = PaddingValues(0.dp)
            )
            {
                Text(text = "Next", fontSize = 15.sp, color = Color.White, fontFamily = fontFamily)
            }
        }


    }
}



@Composable
fun Header(title: String) {

    Box(modifier = Modifier.padding(bottom = 120.dp))
    {
        Row(
            modifier = Modifier.padding(vertical = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Button(
                shape = RectangleShape,
                modifier = Modifier
                    .border(1.dp, Color(0xFFB8B8B8), shape = RoundedCornerShape(15.dp))
                    .clip(RoundedCornerShape(10.dp))
                    //.align(Alignment.BottomCenter)
                    .width(35.dp)
                    .height(35.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFB8B8B8),
                    contentColor = Color.White
                ), contentPadding = PaddingValues(5.dp),
                onClick = {}
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                    contentDescription = "Back",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp)
                )
            }

            Text(
                text = title,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 10.dp),
                fontFamily = fontFamily
            )
        }
    }

}

/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaptionText()
{
    var text by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("", TextRange(0, 7)))
    }

    OutlinedTextField(
        modifier= Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .height(90.dp)
            .clip(RoundedCornerShape(30.dp))
            .border(1.dp, Color.DarkGray, shape = RoundedCornerShape(30.dp))
        ,
        value = text,
        onValueChange = { text = it },
        //label = { Text("Ingredient") },
        shape= RoundedCornerShape(10.dp),
        leadingIcon = {Image(
            painter = painterResource(id = R.drawable.addingredientsframework),
            contentDescription = "Back",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(50.dp)
                .height(70.dp)
                .padding(start = 10.dp)
                .clip(RoundedCornerShape(10.dp))
        )},
        placeholder = { Text("Add a Caption") },
        colors = TextFieldDefaults.textFieldColors(placeholderColor = Color.Gray).also {
            TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = Color.Gray, focusedBorderColor = Color.Gray, containerColor = Color.White) }
        //colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = Color.Gray, focusedBorderColor = Color.Gray)
    )

    Divider(modifier=Modifier.padding(vertical = 10.dp))
}

 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarIngredient(searchText: TextFieldValue, onTextChange: (TextFieldValue) -> Unit) {
    /*
    var (searchText, onTextChange) = rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("", TextRange(0, 10)))
    }
    */


    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth())
    {
        TextField(
            modifier = Modifier
                .padding(0.dp)
                .height(50.dp)
                .fillMaxWidth(0.8f),
            shape = RoundedCornerShape(15.dp),
            value = searchText,
            onValueChange = { onTextChange(it) },
            //label = { Text("Ingredient") },
            textStyle= TextStyle(fontFamily = montserratFamily)
            ,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null, // decorative element
                    modifier = Modifier
                        .size(25.dp)
                        .clip(RoundedCornerShape(25.dp))
                )
            },
            placeholder = { Text("Search Query", fontFamily = montserratFamily) },
            colors = TextFieldDefaults.textFieldColors(
                //placeholderColor = Color.DarkGray,
                containerColor = Color.LightGray,
                //textColor = Color.Gray,
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )

        Button(
            onClick = { onTextChange(TextFieldValue(text = "")) }, modifier = Modifier
                .padding(0.dp)
                .fillMaxWidth()
                //.defaultMinSize(minWidth = 10.dp)
                .background(
                    Color.Transparent
                ), colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = Color.Transparent,
                contentColor = Color.Transparent
            ), shape = RoundedCornerShape(10.dp), contentPadding = PaddingValues(0.dp)
        ) {
            Text(
                text = "Clear", color = Color.Gray, modifier = Modifier
                    .background(Color.Transparent)
                    .padding(0.dp)
                , fontFamily = fontFamily
            )
        }
    }

}

@Composable
fun IngredientItem(
    name: String,
    modifier: Modifier = Modifier,
    selectedOptions: SnapshotStateList<String>
) {


    Row(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .then(modifier)
            .selectable(
                selected = (name in selectedOptions)//(name == selectedOption)
                , onClick = {
                    // onOptionSelected(name)

                    if (name in selectedOptions) {
                        selectedOptions.remove(name)
                    } else {
                        selectedOptions.add(name)
                    }
                }
            ),
        verticalAlignment = Alignment.CenterVertically,

        )
    {
        Row(
            modifier = Modifier
                .weight(1F)
                .then(Modifier.padding(2.4.dp))
                .then(modifier), verticalAlignment = Alignment.CenterVertically
        )
        {
            /*
            Icon(
                painter = painterResource(id = androidx.core.R.drawable.notification_icon_background),
                contentDescription = null // decorative element
            )
             */
            Image(
                painter = painterResource(id = R.drawable.imagecard),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Text(
                text = name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 15.dp),
                fontFamily = fontFamily
            )
        }

        //Radio Button
        /*
        RadioButton(selected = (name == selectedOption), onClick = {
            onOptionSelected(name)
        })
        */

        Button(
            shape = CircleShape,
            modifier = Modifier
                .size(20.dp)
                .clip(CircleShape)
                //.padding(horizontal = 20.dp)
                .then(
                    if (name in selectedOptions)//(name == selectedOption)
                        Modifier
                            .border(1.dp, Color.Green, shape = CircleShape)
                            .background(Color(150, 255, 200))
                            .padding(0.dp)
                    else Modifier
                        .border(1.dp, Color.Gray, shape = CircleShape)
                        .background(Color(200, 200, 200))
                        .padding(0.dp)
                )
            //.padding(end = 50.dp)
            ,
            onClick = {
                //onOptionSelected(name)
                if (name in selectedOptions) {
                    selectedOptions.remove(name)
                } else {
                    selectedOptions.add(name)
                }
            }, colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = Color.Transparent,
                contentColor = Color.Transparent
            )
        )
        {

        }

        //Image(painter= painterResource(id = androidx.core.R.drawable.notification_icon_background), contentDescription = null)
    }
}

@Composable
private fun IngredientsList(
    modifier: Modifier = Modifier,
    names: List<String> = List(10) { "Ingredient $it" },
    search_names: SnapshotStateList<String> = SnapshotStateList<String>()
) {
    val selectedOptions = remember { mutableStateListOf<String>() }
    LazyColumn(
        modifier = Modifier
            .padding(5.dp)
            .padding(end = 21.dp)
            .padding(top = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        items(items = if (search_names.isEmpty()) names else search_names) { name ->
            IngredientItem(name = name, selectedOptions = selectedOptions)
        }
    }
}


@Composable
fun Ingredients() {
    var all_names: List<String> = List(10) { "Ingredient $it" }
    var (searchText, onTextChange) = rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue("", TextRange(0, 10)))
    }

    var search_names = remember { mutableStateListOf<String>() }

    for (name in all_names) {
        if (searchText.text in name) {
            search_names.add(name)
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally)
    {
        //Text(fontSize = 30.sp, text = searchText.text)
        SearchBarIngredient(searchText, onTextChange)
        IngredientsList()
    }

}
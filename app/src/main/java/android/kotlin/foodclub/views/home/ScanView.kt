package android.kotlin.foodclub.views.home

import android.Manifest
import android.annotation.SuppressLint
import android.kotlin.foodclub.config.ui.BottomBarScreenObject
import android.kotlin.foodclub.config.ui.Montserrat
import android.kotlin.foodclub.config.ui.foodClubGreen
import android.kotlin.foodclub.utils.composables.FabButtonItem
import android.kotlin.foodclub.utils.composables.FabButtonMain
import android.kotlin.foodclub.utils.composables.FabButtonSub
import android.kotlin.foodclub.utils.composables.MultiFloatingActionButton
import android.kotlin.foodclub.utils.composables.engine.createImageCaptureUseCase
import android.kotlin.foodclub.viewModels.home.DiscoverViewModel
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.delay


@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun ScanView(navController: NavController,viewModel: DiscoverViewModel) {
    var scanstate:String  by rememberSaveable { mutableStateOf("off") }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.CAMERA,
        )
    )
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp + 10.dp

    val previewView: PreviewView = remember { PreviewView(context) }
    val imageCapture: MutableState<ImageCapture?> = remember { mutableStateOf(null) }
    val cameraSelector: MutableState<CameraSelector> = remember {
        mutableStateOf(CameraSelector.DEFAULT_BACK_CAMERA)
    }
    var showCamPreview by rememberSaveable { mutableStateOf(true) }
    var showImage by rememberSaveable { mutableStateOf(false) }

    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    var BottomSheetNextButton by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(showBottomSheet)

    var Button1text = ""
    var Button2text = ""
    var onclick1:() -> Unit={}
    var onclick2:() -> Unit={}
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp) // Add overall padding to the Column
    ) {
        when (scanstate) {

            "off" -> {Text(
                text = "Once you've finished scanning, you can use Search to find ingredients you need",
                modifier = Modifier.padding(10.dp),
                color = Color.Gray,
                fontFamily = Montserrat,
                textAlign = TextAlign.Center
            )
                Button1text="Maybe Later"
                Button2text="Scan"
                onclick1={navController.navigate(BottomBarScreenObject.Play.route)}
                onclick2= {
                    scanstate = "Scanning"
                    viewModel.Scan(imageCapture.value!!, context)
                    showCamPreview=!showCamPreview
                    showImage = !showImage
                    showBottomSheet = !showBottomSheet

                }
            }

            "Scanning" -> Text(
                text = "Scan in process... ",
                modifier = Modifier.padding(10.dp),
                color = Color.Gray,
                fontFamily = Montserrat,
                textAlign = TextAlign.Center
            )

            "Completed" ->{ Text(
                text = "Scan completed! \nCheckout the scan results below",
                modifier = Modifier.padding(10.dp),
                color = Color.Gray,
                fontFamily = Montserrat,
                textAlign = TextAlign.Center
            )
                Button1text = "Scan Again"
                Button2text = "Next"
                onclick1 = {

                    navController.navigate("ScanView_route")
                }

                onclick2 = {navController.navigate("ScanResultView_route")}
            }

        }

        LaunchedEffect(Unit) {
            permissionState.launchMultiplePermissionRequest()
        }

        LaunchedEffect(previewView) {
            imageCapture.value = context.createImageCaptureUseCase(
                lifecycleOwner = lifecycleOwner,
                cameraSelector = cameraSelector.value,
                previewView = previewView
            )
        }

         //Capture and display the image
        PermissionsRequired(
            multiplePermissionsState = permissionState,
            permissionsNotGrantedContent = { /* ... */ },
            permissionsNotAvailableContent = { /* ... */ }
        ) {
            Box(
                modifier = Modifier
                    .size(580.dp)
                    .background(Color.Transparent)
            )
            {
                // Scaffold with a Multi-Floating Action Button (Multi-FAB)
                Scaffold(
                    floatingActionButton = {
                        if (scanstate=="Scanning"||scanstate=="Completed")
                        {
                            MultiFloatingActionButton(
                                items = listOf(

                                     FabButtonItem(
                                    iconRes = Icons.Filled.Refresh,
                                    label = "Scan again"
                                     ),
                                    FabButtonItem(
                                    iconRes = Icons.Filled.AddCircle,
                                     label = "Scan more"
                                    ),

                                ),

                            onFabItemClicked = {
                            navController.navigate("ScanView_route")

//                                when (it.label) {
//                                    "Scan again" -> {
//                                        navController.navigate("ScanView_route")
//                                    }
//                                    " Scan more" -> {
//                                    }
//                                }
                            },
                                 fabIcon = FabButtonMain(),
                                 fabOption = FabButtonSub()
                            )
                        }
                    }
                )
                {
                    if (showCamPreview) {
                        AndroidView(
                            factory = { previewView },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(screenHeight)
                        )
                    }

                        if(scanstate=="Scanning"||scanstate=="Completed") {
                            viewModel.capturedImage.value?.let { imageBitmap ->
                                Image(
                                    bitmap = imageBitmap,
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }
                }
            }
        if (scanstate=="off"||scanstate=="Completed")
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp) // Adjust padding for the buttons
            ) {
                Buttons( text =Button1text, onClick =onclick1 , containerColor =Color.Transparent,textcolor= foodClubGreen)
                Buttons(text =Button2text ,  onClick =onclick2, containerColor = foodClubGreen,textcolor=Color.White )
            }
        }
        if(scanstate=="Scanning")
        {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Bottom) {

                Button(
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .size(50.dp)
                        .align(Alignment.End),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(126, 198, 11, 255),
                        contentColor = Color.White
                    ),
                    contentPadding = PaddingValues(15.dp),
                    onClick = {
                        showBottomSheet = !showBottomSheet
                    }
                )
                {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.clickable {
                            // Handle click on the additional icon
                            showBottomSheet = !showBottomSheet
                        }
                    )
                }
            }
        }

            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    sheetState = bottomSheetState,
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${viewModel.ScanResultItemList.size} Items Found",
                            fontFamily = Montserrat,
                            fontWeight = FontWeight.Bold,
                        )
                        Divider(
                            color = Color.Gray,
                            thickness = 0.8.dp,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                        val visibleItems = viewModel.ScanResultItemList.take(3)
                        Row {
                            visibleItems.forEach {
                                horizontalBottomSheetItem(
                                    icon = it.imageUrl,
                                    text = it.type,

                                )
                            }

                            if(viewModel.ScanResultItemList.size>visibleItems.size)
                            {
                                Column(modifier = Modifier.padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                                )
                                {
                                    Icon(
                                        imageVector = Icons.Default.AddCircle,
                                        contentDescription = null,
                                        tint = Color.Black,
                                        modifier = Modifier
                                            .clickable(onClick = {})
                                            .size(35.dp)

                                        // Handle click on the additional icon
                                    )
                                    Text(
                                        text = " ${viewModel.ScanResultItemList.size-visibleItems.size} " +
                                                "More",
                                        fontFamily = Montserrat,
                                    )
                                }
                            }
                            Column(modifier = Modifier.padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally) {

                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowRight,
                                    contentDescription = null,
                                    tint = Color.Black,
                                    modifier = Modifier
                                        .clickable(onClick = {
                                            BottomSheetNextButton = !BottomSheetNextButton
                                            scanstate = "Completed"
                                        })
                                        .clip(CircleShape)
                                        .background(foodClubGreen)
                                        .size(35.dp)
                                )
                                Text(
                                    text = "Next",
                                    fontFamily = Montserrat,
                                    )
                            }

                        }

                        Spacer(modifier = Modifier.height(25.dp))

                    }
                }

            }

            if(BottomSheetNextButton) {
                AddIngredientDialog(
                    "Scanning completed!",
                    "${viewModel.ScanResultItemList.size} items were detected in your fridge \n and the results will be imported now..."
                )
                LaunchedEffect(key1 = true) {
                    delay(3000)
                    BottomSheetNextButton=!BottomSheetNextButton
                    showBottomSheet = !showBottomSheet

                }
            }
        }
    }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topbackbar(navController: NavController, screenContent: @Composable () -> Unit)
{
    Scaffold(
        topBar = {
            TopAppBar(
                title = {  Text(
                    text = "Scan My Fridge",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp), // Adjust padding as needed
                    textAlign = TextAlign.Center
                )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.clickable(onClick = { navController.popBackStack() })
                            .size(35.dp)
                    )
                },
                actions = {
                    // Additional navigation icon on the right
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.clickable (onClick = {navController.navigate(BottomBarScreenObject.Play.route)})
                            .size(35.dp)
                    )
                },

                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onSecondary,

                    )
            )
        },
   )
    { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {

            screenContent()
        }

    }
}


@Composable
fun horizontalBottomSheetItem(icon: Any, text: String) {
    Column(
        modifier = Modifier
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (icon) {
            is Int -> {
                // If icon is of type Int (resource ID), use painterResource
                Image(
                    painter = painterResource(icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(35.dp)
                        .clip(CircleShape),
                )
            }

            is Painter -> {
                // If icon is of type Painter, directly use it
                Image(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier
                        .size(35.dp)
                        .clip(CircleShape),
                )
            }
        }

            Text(
                text = text,
                fontFamily = Montserrat,

                )
    }
}

@Composable
fun Buttons(text:String,onClick: () -> Unit,containerColor:Color,textcolor:Color)
{
    Button(
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .padding(8.dp)
            .border(1.dp, foodClubGreen, shape = RoundedCornerShape(15.dp))
            .clip(RoundedCornerShape(15.dp))
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor =containerColor,
            contentColor = foodClubGreen
        ),
        contentPadding = PaddingValues(15.dp),
        onClick = onClick
    ) {
        Text(
            text = text,
            color = textcolor,
            fontFamily = Montserrat,
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraBold
        )
    }
}
package live.foodclub.presentation.ui.home.home.foodSNAPS

//@Composable
//fun MemoriesView(
//    modifier: Modifier,
//    storyListEmpty: Boolean,
//    state: HomeState,
//    onShowMemoriesChanged: (Boolean) -> Unit,
//    updateCurrentMemoriesModel: (MemoriesModel) -> Unit
//) {
//
//    Column(
//        modifier = modifier,
//        verticalArrangement = Arrangement.Center,
//    ) {
//        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_90)))
//
//        Text(
//            text = stringResource(id = R.string.memories),
//            style = TextStyle(
//                fontWeight = FontWeight.Bold,
//                color = Color.Black,
//                fontSize = dimensionResource(id = R.dimen.fon_20).value.sp,
//                fontFamily = Montserrat
//            ),
//            modifier = Modifier
//                .padding(dimensionResource(id = R.dimen.dim_15))
//        )
//
//        if (state.memories.isEmpty()) {
//            MemoriesItemView(
//                modifier = Modifier
//                    .clickable { onShowMemoriesChanged(true) },
//                painter = painterResource(id = R.drawable.nosnapsfortheday),
//                date = ""
//            )
//        } else {
//            LazyRow(
//                modifier = Modifier
//            ) {
//                items(state.memories) {
//                    val painter: Painter =
//                        rememberAsyncImagePainter(model = it.stories[0].imageUrl)
//                    MemoriesItemView(
//                        modifier = Modifier.clickable {
//                            onShowMemoriesChanged(true)
//                            updateCurrentMemoriesModel(it)
//                        },
//                        painter = painter,
//                        date = it.dateTime
//                    )
//                    Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.dim_12)))
//                }
//            }
//        }
//
//        if (storyListEmpty) {
//            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.dim_15)))
//        } else {
//            Divider(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = dimensionResource(id = R.dimen.dim_15)),
//                color = snapsTopbar
//            )
//        }
//    }
//}
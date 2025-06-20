package com.luacheia.kmptravelapp.backoffice.sections.categoriesplaces

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.luacheia.kmptravelapp.backoffice.sections.categoriesplaces.addcategory.AddCategoryScreen
import com.luacheia.kmptravelapp.backoffice.ui.theme.backgroundColor
import com.luacheia.kmptravelapp.data.model.Category
import com.luacheia.kmptravelapp.data.model.Place
import com.luacheia.kmptravelapp.presentation.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.compose.viewmodel.koinViewModel
import java.io.IOException
import java.net.URL

@Composable
fun CategoriesAndPlacesRoot(viewModel: CategoriesAndPlacesViewModel = koinViewModel()) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "categories") {
        composable("categories") {
            CategoriesAndPlacesScreen(
                viewModel = viewModel,
                onCategoryClicked = { categoryId ->
                    navController.navigate("categoryDetail/$categoryId")
                },
                onAddCategoryClicked = {
                    navController.navigate("addCategory")
                },
            )
        }
        // TODO usar navigation
        composable("categoryDetail/{categoryId}") { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getString("categoryId") ?: return@composable
            CategoryDetailScreen(
                categoryId = categoryId,
                viewModel = viewModel,
                onClose = { navController.popBackStack() }
            )
        }
        composable("addCategory") {
            AddCategoryScreen(
                onDismiss = { navController.popBackStack() }
            )

        }
    }
}


@Composable
fun CategoriesAndPlacesScreen(
    viewModel: CategoriesAndPlacesViewModel = koinViewModel(),
    onCategoryClicked: (String) -> Unit = { _ -> },
    onAddCategoryClicked: () -> Unit = {},
    onAddCategorySuccessCallback: () -> Unit = {},
    onPlaceClicked: (String) -> Unit = { _ -> }
) {
    var selectedCategoryId by remember { mutableStateOf<String?>(null) }
    val categoriesAndPlacesUiState by viewModel.categoriesAndPlacesUiState.collectAsState()
    val addCategoryState by viewModel.addCategoryState.collectAsState()

    // Show detail screen if a category is selected
    selectedCategoryId?.let { categoryId ->
        CategoryDetailScreen(
            categoryId = categoryId,
            viewModel = viewModel,
            onClose = { selectedCategoryId = null }
        )
        return
    }

    when (val uiState = categoriesAndPlacesUiState) {
        is UiState.Success -> {
            CategoriesAndPlacesSuccessLayout(
                categories = uiState.data.categories,
                places = uiState.data.places,
                onCategoryClicked = { id -> selectedCategoryId = id },
                onPlaceClicked = onPlaceClicked,
                onAddCategoryClick = onAddCategoryClicked
            )
        }

        is UiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Loading...", fontSize = 20.sp, color = Color.Black)
            }
        }

        is UiState.Failure -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Error loading data", fontSize = 20.sp, color = Color.Red)
            }
        }

        else -> {}
    }
}

@Composable
fun CategoryDetailScreen(
    categoryId: String,
    viewModel: CategoriesAndPlacesViewModel,
    onClose: () -> Unit
) {
    val categoryState by viewModel.categoryDetailState.collectAsState()
    val editState by viewModel.editCategoryState.collectAsState()
    val deleteState by viewModel.deleteCategoryState.collectAsState()
    var isEditing by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var iconUrl by remember { mutableStateOf("") }

    LaunchedEffect(categoryId) {
        viewModel.loadCategoryById(categoryId)
    }

    when (categoryState) {
        is UiState.Loading -> Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) { Text("Carregando...") }

        is UiState.Failure -> Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) { Text("Erro ao carregar categoria") }

        is UiState.Success -> {
            val category = (categoryState as UiState.Success<Category>).data
            if (!isEditing) {
                name = category.name
                iconUrl = category.iconUrl
            }
            Column(
                modifier = Modifier.fillMaxSize().padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Categoria", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                Spacer(Modifier.height(16.dp))
                if (isEditing) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Nome") })
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = iconUrl,
                        onValueChange = { iconUrl = it },
                        label = { Text("URL do Ícone") })
                } else {
                    Text("Nome: ${category.name}")
                    Spacer(Modifier.height(8.dp))
                    Text("Ícone: ${category.iconUrl}")
                }
                Spacer(Modifier.height(24.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (isEditing) {
                        Button(onClick = {
                            viewModel.updateCategory(category.copy(name = name, iconUrl = iconUrl))
                            isEditing = false
                        }, enabled = editState !is UiState.Loading) { Text("Salvar") }
                        Button(onClick = { isEditing = false }) { Text("Cancelar") }
                    } else {
                        Button(onClick = { isEditing = true }) { Text("Editar") }
                        Button(
                            onClick = {
                                viewModel.deleteCategory(category.id)
                                onClose()
                            },
                            enabled = deleteState !is UiState.Loading,
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) { Text("Excluir") }
                        Button(onClick = onClose) { Text("Fechar") }
                    }
                }
                if (editState is UiState.Failure) {
                    Text("Erro ao editar categoria", color = Color.Red)
                }
                if (deleteState is UiState.Failure) {
                    Text("Erro ao excluir categoria", color = Color.Red)
                }
            }
        }

        else -> {}
    }
}

@Composable
fun CategoriesAndPlacesSuccessLayout(
    categories: List<Category>,
    onCategoryClicked: (String) -> Unit,
    places: List<Place>,
    onPlaceClicked: (String) -> Unit,
    onAddCategoryClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Topic("Categorias", onAddCategoryClick)
        CategoriesList(categories, onCategoryClicked)
        Spacer(modifier = Modifier.height(20.dp))
        Topic("Lugares")
        PlacesGrid(places, onPlaceClicked)
    }
}

@Composable
fun Topic(
    text: String,
    onAddClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier.wrapContentHeight().padding(bottom = 25.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Title(text = text)
        Spacer(modifier = Modifier.width(30.dp))
        if (onAddClick != null) {
            AddButton(onClick = onAddClick)
        }
    }
}

@Composable
fun Title(
    text: String,
) {
    Text(
        text = text,
        color = Color.Black,
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold,
    )
}


@Composable
fun CategoriesList(
    categories: List<Category>,
    onCategoryClicked: (String) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        itemsIndexed(categories) { index, item ->
            CategoryItem(
                id = item.id,
                name = item.name,
                iconUrl = item.iconUrl,
                onCategoryClicked = onCategoryClicked
            )
        }
    }
}

@Composable
fun CategoryItem(
    id: String,
    name: String,
    iconUrl: String,
    onCategoryClicked: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(backgroundColor)
                .clickable { onCategoryClicked.invoke(id) }, // FIX: pass real id
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                load = { loadImageBitmap(iconUrl) },
                painterFor = { remember { BitmapPainter(it) } },
                contentDescription = "",
                modifier = Modifier.padding(20.dp)
                    .size(40.dp),
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )

    }
}

@Composable
fun PlacesGrid(
    places: List<Place>,
    onPlaceClicked: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        modifier = Modifier.fillMaxWidth().height(1000.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(places) { place ->
            PlaceItem(
                id = place.id,
                name = place.name,
                iconUrl = place.imageUrls.first(),
                onPlaceClicked = onPlaceClicked
            )
        }
    }
}

@Composable
fun PlaceItem(
    id: String,
    name: String,
    iconUrl: String,
    onPlaceClicked: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .size(180.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(backgroundColor)
                .clickable { onPlaceClicked.invoke("id") }
        ) {
            AsyncImage(
                load = { loadImageBitmap(iconUrl) },
                painterFor = { remember { BitmapPainter(it) } },
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )

    }
}

@Composable
fun AddButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(20.dp)
            .clip(shape = CircleShape)
            .background(backgroundColor)
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            tint = Color.White,
            contentDescription = "Add"
        )
    }
}


@Composable
fun <T> AsyncImage(
    load: suspend () -> T,
    painterFor: @Composable (T) -> Painter,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
) {
    val image: T? by produceState<T?>(null) {
        value = withContext(Dispatchers.IO) {
            try {
                load()
            } catch (e: IOException) {
                // instead of printing to console, you can also write this to log,
                // or show some error placeholder
                e.printStackTrace()
                null
            }
        }
    }

    if (image != null) {
        Image(
            painter = painterFor(image!!),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier
        )
    }
}
//fun loadImageBitmap(file: File): ImageBitmap =
//    file.inputStream().buffered().use(::loadImageBitmap)

//fun loadSvgPainter(file: File, density: Density): Painter =
//    file.inputStream().buffered().use { loadSvgPainter(it, density) }
//
//fun loadXmlImageVector(file: File, density: Density): ImageVector =
//    file.inputStream().buffered().use { loadXmlImageVector(InputSource(it), density) }

/* Loading from network with java.net API */

fun loadImageBitmap(url: String): ImageBitmap =
    URL(url).openStream().buffered().use(::loadImageBitmap)
//
//fun loadSvgPainter(url: String, density: Density): Painter =
//    URL(url).openStream().buffered().use { loadSvgPainter(it, density) }
//
//fun loadXmlImageVector(url: String, density: Density): ImageVector =
//    URL(url).openStream().buffered().use { loadXmlImageVector(InputSource(it), density) }

@Preview
@Composable
fun AddButtonPreview() {
    AddButton(onClick = { }, modifier = Modifier.padding(16.dp))
}
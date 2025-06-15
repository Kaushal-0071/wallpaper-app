package com.example.wallpaper.presentation.search_screen

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.forEach
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wallpaper.data.model.BackendImageDto
import com.example.wallpaper.presentation.components.ImagesVerticalGrid
import com.example.wallpaper.presentation.util.colurs
import com.example.wallpaper.presentation.util.creators
import com.example.wallpaper.presentation.wallpaperScreen.WallPaperViewModel


@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SearchScreen(
    onImageClick: (String) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        // State for the search query
        var query by remember { mutableStateOf("") }
        // State for managing the active state of the search bar
        var active by remember { mutableStateOf(false) }
        // State to hold search results (replace with your actual data fetching)
        val viewModel: SearchViewModel = hiltViewModel()

        val focusManager = LocalFocusManager.current
//        BackHandler(enabled = true) {
//            active = false                // collapse the search bar
//                // clear any input focus
//        }


        // Launch an effect once to install a LifecycleObserver

        val keyboardController = LocalSoftwareKeyboardController.current
        Column(modifier = Modifier.fillMaxSize()) {
            MySearchBar(
                query = query,
                onQueryChange = { newQuery ->
                    query = newQuery
                    // You can perform live search updates here if needed
                    // For example, by filtering a list based on newQuery
                },
                onSearch = { searchQuery ->
                    // This is typically called when the user presses the search icon on the keyboard
                    active = false // Usually, you want to close the search bar after a search
                    viewModel.searchImages(searchQuery)


                },
                active = active,
                onActiveChange = { isActive ->

                    active = isActive
                    if (!isActive) {focusManager.clearFocus()

                        viewModel.images=emptyList()
                    }
                },
                placeholderText = "Search for wallpapers...",
                modifier = Modifier.padding(16.dp) ,
                trailingIcon ={   if (query.isNotEmpty()) {
                    IconButton(onClick = { query=""
                        viewModel.images=emptyList()

                    }) {
                        Icon(Icons.Default.Close, contentDescription = "Clear Search")
                    }
                }}

            // Example modifier
            )

                if(viewModel.images.isEmpty()  && query.isEmpty()){
                    SearchPreview(onClick = {color->
                        query=color
                        active=true
                        viewModel.searchImages(query)
                    }, onChipClick = {creator->
                        query=creator
                        active=true
                        viewModel.searchImages(query)
                    })
                }

            if(viewModel.images.isEmpty() && query.isNotEmpty() && viewModel.success==false){
             Text("fail")
            }
            if(viewModel.images.isNotEmpty() && viewModel.success){
                ImagesVerticalGrid(
                    modifier = Modifier,
                    images = viewModel.images,
                    onImageClick =onImageClick,
                    sharedTransitionScope =sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope
                )
            }





        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    placeholderText: String = "Search...",
    leadingIcon: @Composable (() -> Unit)? = {
        Icon(Icons.Default.Search, contentDescription = "Search Icon")
    },
    trailingIcon: @Composable (() -> Unit)? ,

) {
    Box(modifier = modifier.fillMaxWidth()) {
        val colors1 = SearchBarDefaults.colors()

        SearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                    query = query,
                    onQueryChange = onQueryChange,
                    onSearch = onSearch,
                    expanded = active,
                    onExpandedChange = onActiveChange,
                    placeholder = { Text(placeholderText) },
                    leadingIcon = leadingIcon,
                    trailingIcon = trailingIcon,
                    colors = colors1.inputFieldColors,
                )
            },
            expanded = false,
            onExpandedChange = onActiveChange,
            modifier = Modifier.fillMaxWidth(),
            colors = colors1,
            content ={

            }
        )
    }
}



@Composable
fun SearchPreview(
    onClick: (String) -> Unit,
    onChipClick: (String) -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
Column (modifier = Modifier.fillMaxSize()){

    Icon(Icons.Default.Search, contentDescription = "", modifier = Modifier.size(100.dp).align(Alignment.CenterHorizontally))
    Text("Search by Creators")
    LazyRow {
        items(creators){ word->
            SuggestionChip(
                onClick = {
onChipClick(word)
                },
                label = {Text(word)},
                modifier = Modifier.padding(2.dp)
            )
        }
    }
    Text("Search by Colors")
    ColorGrid(hexColors = colurs, onClick = onClick)

}

    }
}
@Composable
fun ColorGrid(
    hexColors: List<String>,
    columns: Int = 4,
    modifier: Modifier = Modifier,
  onClick: (String) -> Unit
) {
    // Convert your hex strings into Compose Color objects

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(hexColors.size) { color ->
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))  // optional: rounded corners
                    .aspectRatio(1f)      // keep each cell square
                    .background(Color(hexColors[color].toColorInt())).clickable {
                       onClick(hexColors[color])
                    }   // fill with the color
            )
        }
    }
}

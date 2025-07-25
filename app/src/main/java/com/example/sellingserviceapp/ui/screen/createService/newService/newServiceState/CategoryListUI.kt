package com.example.sellingserviceapp.ui.screen.createService.newService.newServiceState

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sellingserviceapp.ui.screen.createService.component.CategoryButton
import com.example.sellingserviceapp.ui.screen.createService.newService.NewServiceUIState
import com.example.sellingserviceapp.ui.screen.createService.newService.NewServiceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryListUI(
    viewModel: NewServiceViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
    ) {
        LazyColumn (
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    BottomSheetDefaults.DragHandle(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                    )
                }
            }
            item {
                Text("Категория", fontSize = 32.sp, color = MaterialTheme.colorScheme.onBackground)
            }
            items(viewModel.categories) { category ->
                CategoryButton(
                    category = category.name,
                    onClick = {
                        viewModel.newServiceUIState = NewServiceUIState.Subcategories
                        viewModel.getSubcategories(categoryId = category.id)
                    }
                )
            }
        }
    }
}
package com.example.sellingserviceapp.ui.screen.createService.newService

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sellingserviceapp.ui.screen.createService.newService.newServiceState.CategoryListUI
import com.example.sellingserviceapp.ui.screen.createService.newService.newServiceState.DescriptionUI
import com.example.sellingserviceapp.ui.screen.createService.newService.newServiceState.ParametersUI
import com.example.sellingserviceapp.ui.screen.createService.newService.newServiceState.SubcategoryListUI

sealed class NewServiceUIState {
    data object Categories: NewServiceUIState()
    data object Subcategories: NewServiceUIState()
    data object Description: NewServiceUIState()
    data object Parameters: NewServiceUIState()
}

@Composable
fun NewServiceUI(
    viewModel: NewServiceViewModel = hiltViewModel()
) {
    AnimatedContent(
        targetState = viewModel.newServiceUIState,
        transitionSpec = {
            fadeIn(tween(500)) togetherWith fadeOut(tween(500))
        }
    ) {
        when(it) {
            is NewServiceUIState.Categories -> {
                CategoryListUI()
            }
            is NewServiceUIState.Subcategories -> {
                SubcategoryListUI()
            }
            is NewServiceUIState.Description -> {
                DescriptionUI()
            }
            is NewServiceUIState.Parameters -> {
                ParametersUI()
            }
        }
    }
}
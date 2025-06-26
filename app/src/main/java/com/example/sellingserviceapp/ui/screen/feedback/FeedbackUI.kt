package com.example.sellingserviceapp.ui.screen.feedback

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sellingserviceapp.data.network.feedback.model.AvailableFeedbacks
import com.example.sellingserviceapp.model.FeedbackWithData
import com.example.sellingserviceapp.ui.screen.createService.component.ServiceTextField
import com.example.sellingserviceapp.ui.screen.createService.newService.NewServiceUIState
import com.example.sellingserviceapp.ui.screen.createService.newService.NewServiceViewModel
import com.example.sellingserviceapp.ui.screen.offer.BookingListItem
import com.example.sellingserviceapp.ui.screen.offer.BookingSkeleton

sealed class FeedbackUIState {
    data object Init: FeedbackUIState()
    data object Loaded: FeedbackUIState()
}

sealed class FeedbackSheetState {
    data object New: FeedbackSheetState()
    data object Edit: FeedbackSheetState()
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FeedbackUI(
    viewModel: FeedbackViewModel = hiltViewModel(),
    onBackButtonClick: () -> Unit
) {

    val userFeedbacks by viewModel.feedbackFlow.collectAsState()
    val availableFeedbacks by viewModel.availableFeedbackFlow.collectAsState()

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )


    if (viewModel.isSheetOpen) {
        ModalBottomSheet(
            modifier = Modifier
                .displayCutoutPadding(),
            containerColor = MaterialTheme.colorScheme.background,
            sheetState = sheetState,
            onDismissRequest = {viewModel.isSheetOpen = false},
            scrimColor = Color.Black.copy(0.6f),
            dragHandle = null
        ) {
            when(viewModel.feedbackSheetState) {
                is FeedbackSheetState.New -> {
                    EditFeedback(
                        feedbackWithData = viewModel.pickedFeedback,
                        onSaveButtonClick = {comment, rating ->
                            viewModel.isSheetOpen = false
                            viewModel.createFeedback(comment, rating)
                        }
                    )
                }
                is FeedbackSheetState.Edit -> {
                    EditFeedback(
                        feedbackWithData = viewModel.pickedFeedback,
                        onSaveButtonClick = {comment, rating ->
                            viewModel.isSheetOpen = false
                            viewModel.editFeedback(comment, rating)
                        }
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.updateList()
    }

    AnimatedContent(
        targetState = viewModel.feedbackUIState,
        transitionSpec = {
            fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(300))
        }
    ) {
        when(it) {
            is FeedbackUIState.Init -> {
                BookingSkeleton()
            }
            is FeedbackUIState.Loaded -> {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                        .fillMaxSize()
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .displayCutoutPadding()
                            .padding(horizontal = 15.dp),
                        verticalArrangement = Arrangement.spacedBy(15.dp),
                    ) {
                        stickyHeader {
                            Row(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.background)
                                    .padding(bottom = 15.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    IconButton(
                                        onClick = onBackButtonClick,
                                        modifier = Modifier
                                    ) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                            contentDescription = "Back",
                                            modifier = Modifier
                                                .size(28.dp),
                                            tint = MaterialTheme.colorScheme.onBackground
                                        )
                                    }
                                    Text(
                                        "Отзывы",
                                        fontSize = 32.sp,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                        if (availableFeedbacks != emptyList<AvailableFeedbacks>()) {
                            items(availableFeedbacks) { feedback ->
                                UserFeedbackListItem(
                                    offer = feedback,
                                    isLoading = viewModel.isLoading,
                                    onClick = {
                                        viewModel.feedbackSheetState = FeedbackSheetState.New
                                        viewModel.pickedFeedback = feedback
                                        viewModel.isSheetOpen = true
                                    }
                                )
                            }
                        }
                        items(userFeedbacks) { feedback ->
                            UserFeedbackListItem(
                                offer = feedback,
                                isLoading = viewModel.isLoading,
                                onClick = {
                                    viewModel.feedbackSheetState = FeedbackSheetState.Edit
                                    viewModel.pickedFeedback = feedback
                                    viewModel.isSheetOpen = true
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
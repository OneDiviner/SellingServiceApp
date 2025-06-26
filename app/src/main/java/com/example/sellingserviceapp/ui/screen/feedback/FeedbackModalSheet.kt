package com.example.sellingserviceapp.ui.screen.feedback

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sellingserviceapp.model.FeedbackWithData
import com.example.sellingserviceapp.ui.screen.createService.component.ServiceTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditFeedback(
    feedbackWithData: FeedbackWithData? = null,
    onSaveButtonClick: (String, Int) -> Unit
) {

    var comment by remember { mutableStateOf<String?>(feedbackWithData?.feedback?.comment) }
    var isPicked by remember { mutableIntStateOf(feedbackWithData?.feedback?.rating as Int) }

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
    ) {
        LazyColumn (
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            item {
                Box(modifier = Modifier.fillMaxSize()) {
                    BottomSheetDefaults.DragHandle(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                    )
                }
            }
            item {
                Text(
                    text = feedbackWithData?.service?.tittle ?: "Отзыв",
                    fontSize = 28.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            item {
                ServiceTextField(
                    modifier = Modifier.wrapContentHeight(),
                    value = comment?: "",
                    onValueChange = {
                        comment = it
                    },
                    label = "Комментарий",
                    isSingleLine = false,
                    textStyle = TextStyle.Default.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
            item {
                var marks by remember { mutableStateOf(listOf(1,2,3,4,5)) }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    marks.forEach { mark ->
                        val isSelected = mark == isPicked
                        FilterChip(
                            modifier = Modifier.height(35.dp).weight(1f),
                            label = {Text(mark.toString(), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, fontSize = 24.sp)},
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary
                            ),
                            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onBackground.copy(0.2f)),
                            selected = isSelected,
                            onClick = {
                                isPicked = if (isSelected) 0 else mark
                            }
                        )
                    }
                }
            }
            item {
                Button(
                    onClick = { onSaveButtonClick(comment ?: "", isPicked) },
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    enabled = comment?.isNotBlank() == true && isPicked != 0,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        disabledContainerColor = MaterialTheme.colorScheme.primary.copy(0.5f)
                    )
                ) {
                    Text("Сохранить", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}
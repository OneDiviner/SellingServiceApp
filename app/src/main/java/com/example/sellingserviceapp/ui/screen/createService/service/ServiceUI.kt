package com.example.sellingserviceapp.ui.screen.createService.service

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sellingserviceapp.R
import com.example.sellingserviceapp.model.domain.ServiceDomain
import com.example.sellingserviceapp.ui.screen.createService.CreateServiceViewModel
import com.example.sellingserviceapp.ui.screen.createService.SheetContentState
import com.example.sellingserviceapp.ui.screen.createService.component.ServiceInfoRow
import com.example.sellingserviceapp.util.extension.imagePicker.ImageContent
import com.example.sellingserviceapp.util.extension.imagePicker.pickImageLauncher

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceUI(
    onEditButtonClick: () -> Unit,
    viewModel: ServiceViewModel = hiltViewModel()
) {

    val service by viewModel.serviceFlow.collectAsState()

    val pickImageLauncher = pickImageLauncher {
        viewModel.onPhotoSelected(service?.id?: 0, it)
    }

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(bottom = 15.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .displayCutoutPadding(),
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.5f)
                        .background(MaterialTheme.colorScheme.surfaceContainer),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    BottomSheetDefaults.DragHandle(
                        modifier = Modifier
                            .displayCutoutPadding()
                            .zIndex(3f)
                            .align(Alignment.TopCenter)
                    )
                    Card(
                        modifier = Modifier
                            .padding(start = 15.dp)
                            .offset(y = 18.dp)
                            .zIndex(3f)
                            .align(Alignment.BottomStart),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            text = service?.statusName?: "",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)
                        )
                    }
                    ImageContent(
                        photoBase64 = service?.photo ?: "",
                        onEditButtonClick = onEditButtonClick,
                        onMoreButtonClick = {},
                        onPickImageButtonClick = pickImageLauncher
                    )
                }
            }
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier.padding(horizontal = 15.dp)
                ) {
                    Text(
                        service?.tittle?: "",
                        fontSize = 26.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    ServiceInfoRow(
                        title = "Категория",
                        value = service?.categoryName?: ""
                    )
                    ServiceInfoRow(
                        title = "Подкатегория",
                        value = service?.subcategoryName?: ""
                    )
                    ServiceInfoRow(
                        title = "Цена",
                        value = "${service?.price} ${service?.priceTypeName}"
                    )
                    ServiceInfoRow(
                        title = "Длительность",
                        value = service?.duration.toString()
                    )
                    ServiceInfoRow(
                        title = "Формат оказания услуги",
                        values = service?.formats?: emptyList()
                    )
                }
            }
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier.padding(horizontal = 15.dp)
                ) {
                    Text("Описание")
                    Text(
                        text = service?.description?: "Error",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.ExtraLight,
                        color = MaterialTheme.colorScheme.onBackground.copy(0.7f),
                        // modifier = Modifier.padding(15.dp)
                    )

                }
            }
        }
    }
}
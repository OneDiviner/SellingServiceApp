package com.example.sellingserviceapp.util.extension.imagePicker

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.sellingserviceapp.R

@Composable
fun pickImageLauncher(
    onImageSelected: (base64: String?) -> Unit
):() -> Unit {
    val context = LocalContext.current

    val imagePicker = remember {
        ImagePicker(context, onImageSelected)
    }

    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imagePicker.handleUri(uri)
    }

    return { imageLauncher.launch("image/*") }
}

@Composable
fun ImageContent(
    modifier: Modifier = Modifier,
    photoBase64: String,
    onEditButtonClick: () -> Unit,
    onMoreButtonClick: () -> Unit,
    onPickImageButtonClick: () -> Unit,
    isDropdownExpanded: Boolean = false,
    onDismissRequest: () -> Unit = {},
    onDeleteButtonClick: () -> Unit = {},
    isEditable: Boolean = true,
    isPickImage: Boolean = true,
    isGenerateImage: Boolean = false,
    isGenerating: Boolean = false,
    onGenerateImageButtonClick: () -> Unit = {}
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (photoBase64.isNotBlank()) {
            val bitmap = remember(photoBase64) {
                val bytes = Base64.decode(photoBase64, Base64.DEFAULT)
                BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            }
            bitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "User photo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        } else {
            Icon(
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.Center),
                painter = painterResource(R.drawable.photo),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        if (isEditable) {
            IconButton(
                onClick = onEditButtonClick,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .systemBarsPadding()
                    .padding(start = 15.dp)
                    .zIndex(3f)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    modifier = Modifier
                        .size(28.dp),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
        IconButton(
            onClick = onMoreButtonClick,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .systemBarsPadding()
                .padding(end = 15.dp)
                .zIndex(3f)
        ) {
            Box() {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More",
                    modifier = Modifier
                        .size(28.dp),
                    tint = MaterialTheme.colorScheme.onBackground
                )
                DropdownMenu(
                    modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainer),
                    expanded = isDropdownExpanded,
                    onDismissRequest = onDismissRequest
                ) {
                    DropdownMenuItem(
                        text = { Text("Удалить услугу") },
                        onClick = onDeleteButtonClick,
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.background.copy(alpha = 0.7f),
                            Color.Transparent
                        ),
                        startY = 0f,
                        endY = 300f
                    )
                )
        )
        Box(
        modifier = Modifier
            .matchParentSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        MaterialTheme.colorScheme.background.copy(alpha = 0.7f)
                    ),
                    startY = 300f,
                    endY = Float.POSITIVE_INFINITY
                )
            )
        )
        Column(
            modifier = Modifier.align(Alignment.BottomEnd).padding(end = 15.dp).offset(y = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            if (isGenerateImage) {
                OutlinedButton(
                    onClick = onGenerateImageButtonClick,
                    modifier = Modifier
                        .size(40.dp)
                        .zIndex(5f),
                    contentPadding = PaddingValues(0.dp),
                    border = _root_ide_package_.androidx.compose.foundation.BorderStroke(1.dp,
                        MaterialTheme.colorScheme.onBackground.copy(0.5f)),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.background.copy(0.3f)
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isGenerating) {
                            CircularProgressIndicator(
                                modifier = Modifier.fillMaxSize(0.7f),
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        } else {
                            Icon(
                                modifier = Modifier.fillMaxSize(0.7f),
                                painter = painterResource(R.drawable.aiimage),
                                contentDescription = "Изменить фото",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            }
            if (isPickImage) {
                Button(
                    onClick = onPickImageButtonClick,
                    modifier = Modifier
                        .size(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        modifier = Modifier.fillMaxSize(0.7f),
                        painter = painterResource(R.drawable.add_photo_alternate),
                        contentDescription = "Изменить фото",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}
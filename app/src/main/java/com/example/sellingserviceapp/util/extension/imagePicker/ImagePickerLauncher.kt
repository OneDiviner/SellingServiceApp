package com.example.sellingserviceapp.util.extension.imagePicker

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
    photoBase64: String,
    onEditButtonClick: () -> Unit,
    onMoreButtonClick: () -> Unit,
    onPickImageButtonClick: () -> Unit,
    modifier: Modifier = Modifier
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
        IconButton(
            onClick = onEditButtonClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(15.dp)
                .zIndex(3f)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit",
                modifier = Modifier
                    .size(28.dp)
            )
        }
        IconButton(
            onClick = onMoreButtonClick,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(15.dp)
                .zIndex(3f)
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More",
                modifier = Modifier
                    .size(28.dp)
            )
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
        Button(
            onClick = onPickImageButtonClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 15.dp)
                .offset(y = 18.dp)
                .size(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                modifier = Modifier.size(22.dp),
                painter = painterResource(R.drawable.add_photo_alternate),
                contentDescription = "Изменить фото",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}
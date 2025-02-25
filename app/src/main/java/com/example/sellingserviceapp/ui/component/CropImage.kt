package com.example.sellingserviceapp.ui.component

/*
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.example.sellingserviceapp.ui.screen.authentication.userinfo.UserInfoViewModel

@Composable
fun CropImage(
    viewModel: UserInfoViewModel
) {
    val context = LocalContext.current

    val borderColor = if (isSystemInDarkTheme()) {
        Color.White.copy(alpha = 0.3f)
    } else {
        Color.Black.copy(alpha = 0.3f)
    }

    val cropImageLauncher = rememberLauncherForActivityResult(
        contract = CropImageContract()
    ) { result ->
        if (result.isSuccessful) {
            val croppedUri = result.uriContent
            Log.d("UserInfoScreen", "Image cropped successfully: $croppedUri")
            croppedUri?.let { viewModel.setProfileImage(it) }
        } else {
            // Обработка ошибки
            val exception = result.error
            Log.e("UserInfoScreen", "Crop image failed: ${exception?.message}")
            Toast.makeText(context, "Ошибка обрезки изображения: ${exception?.message}", Toast.LENGTH_SHORT).show()
        }
    }

// Лаунчер для выбора изображения
    val selectImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            Log.d("UserInfoScreen", "Image selected: $uri")
            viewModel.setProfileImage(it)
            // После выбора изображения запускаем обрезку
            cropImageLauncher.launch(
                CropImageContractOptions(
                    uri = it,
                    cropImageOptions = CropImageOptions(
                        aspectRatioX = 1,
                        aspectRatioY = 1,
                        fixAspectRatio = true
                    )
                )
            )
        } ?: run {
            Log.d("UserInfoScreen", "No image selected")
            Toast.makeText(context, "Не удалось выбрать изображение", Toast.LENGTH_SHORT).show()
        }
    }

    val profileImageUri by viewModel.profileImageUri.collectAsState()

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .size(150.dp)
            .border(5.dp, borderColor, CircleShape)
            .clickable {
                // Запуск выбора изображения
                selectImageLauncher.launch("image/*")
            },
        contentAlignment = Alignment.Center
    ) {

        profileImageUri?.let { uri ->
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = "Profile Image",
                modifier = Modifier.fillMaxSize()
            )
        } ?: Icon(
            Icons.Default.Person,
            contentDescription = "Default Profile Image",
            modifier = Modifier
                .fillMaxSize(),
            tint = borderColor
        )
    }
}
*/
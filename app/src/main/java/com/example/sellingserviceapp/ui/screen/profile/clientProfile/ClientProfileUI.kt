package com.example.sellingserviceapp.ui.screen.profile.clientProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sellingserviceapp.model.domain.UserDomain
import com.example.sellingserviceapp.ui.screen.profile.ProfileSheetState
import com.example.sellingserviceapp.ui.screen.profile.ProfileViewModel
import com.example.sellingserviceapp.util.extension.imagePicker.ImageContent
import com.example.sellingserviceapp.util.extension.imagePicker.pickImageLauncher

data class Contacts(
    val value: String,
    val icon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientProfileUI(
    paddingValues: PaddingValues,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val user by viewModel.userFLow.collectAsState(initial = UserDomain.EMPTY)

    val pickImageLauncher = pickImageLauncher {
        viewModel.onPhotoSelected(it)
    }

    val contacts by remember {
        mutableStateOf(
            listOf(
                Contacts(
                    value = user.email,
                    icon = Icons.Default.Email
                ),
                Contacts(
                    value = "https://vk.com/nomatd",
                    icon = Icons.AutoMirrored.Filled.Send
                )
            )
        )
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
                    Column(
                        modifier = Modifier
                            .zIndex(3f)
                            .align(Alignment.BottomStart)
                            .padding(15.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text("${user.firstName} ${user.secondName}", fontSize = 32.sp)
                        Text(user.email, fontSize = 14.sp)
                    }

                    ImageContent(
                        photoBase64 = user.avatar?: "",
                        onEditButtonClick = { viewModel.profileSheetState = ProfileSheetState.EditProfile},
                        onMoreButtonClick = {},
                        onPickImageButtonClick = pickImageLauncher
                    )
                }
            }
            item {
                Text("Контакты")
            }
            item {
                Column {
                    contacts.forEach { contact ->
                        Row {
                            Icon(
                                imageVector = contact.icon,
                                contentDescription = "",
                                modifier = Modifier.size(26.dp)
                            )
                            Text(contact.value, fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}
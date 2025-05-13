package com.example.sellingserviceapp.ui.screen.createService.component

import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.sellingserviceapp.ui.screen.createService.model.Address

@Composable
fun ServiceInfoRow(
    title: String,
    value: String = "",
    values: List<Address> = emptyList()
) {
    if (values.isEmpty()) {
        FlowRow() {
            Text(
                text = "$title: ",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onBackground.copy(0.7f)
            )
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    } else {
        FlowRow() {
            Text(
                text = "$title: ",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onBackground.copy(0.7f)
            )
            var address = ""
            values.forEach { value ->
                Text(
                    text = value.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                if (!value.location.isNullOrBlank()) {
                    address = value.location
                }
            }
            if (address.isNotBlank()) {
                FlowRow() {
                    Text(
                        text = "Адресс: ",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onBackground.copy(0.7f)
                    )
                    Text(
                        text = address,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}
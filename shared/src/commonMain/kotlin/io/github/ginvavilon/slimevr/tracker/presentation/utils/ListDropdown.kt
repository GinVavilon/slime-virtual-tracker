package io.github.ginvavilon.slimevr.tracker.presentation.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun <T> ListDropdown(
    list: List<T>,
    value: T,
    label: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    onValueChanged: (value: T) -> Unit
) {
    var isListExpanded by remember { mutableStateOf(false) }
    var size by remember { mutableStateOf(IntSize(100, 100)) }

    val icon = if (isListExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column {
        OutlinedTextField(
            modifier = modifier
                .onSizeChanged { size = it },
            readOnly = true,
            value = value.toString(),
            onValueChange = {},
            label = label,
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { isListExpanded = !isListExpanded })
            }
        )
        DropdownMenu(
            modifier = Modifier.width(size.width.dp).height(size.height.dp * 3),
            expanded = isListExpanded,
            onDismissRequest = { isListExpanded = false },
        ) {
            list.forEach { label ->
                DropdownMenuItem(
                    text = { Text(text = label.toString(), fontSize = 14.sp) },
                    onClick = {
                        onValueChanged(label)
                        isListExpanded = false
                    },
                )
            }
        }
    }
}
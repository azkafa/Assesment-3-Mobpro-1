package org.d3if0020.assesment2mobpro.commonui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SpacerHeight(
    height:Dp = 10.dp
) {
    Spacer(modifier = Modifier.height(height))
}

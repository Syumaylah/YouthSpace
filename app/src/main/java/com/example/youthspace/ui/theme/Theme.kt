package com.example.youthspace.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val YouthSpaceColors = lightColorScheme(
    primary          = YSBlueHeader,
    onPrimary        = YSWhite,
    primaryContainer = YSNavyDark,
    secondary        = YSBlueMed,
    background       = YSOffWhite,
    surface          = YSWhite,
    error            = YSErrorRed,
    onBackground     = YSTextDark,
    onSurface        = YSTextDark,
    outline          = YSBorderGray,
)

private val YouthSpaceTypography = Typography(
    headlineLarge = TextStyle(fontWeight = FontWeight.Bold,    fontSize = 24.sp, lineHeight = 32.sp),
    headlineMedium= TextStyle(fontWeight = FontWeight.Bold,    fontSize = 20.sp, lineHeight = 28.sp),
    titleLarge    = TextStyle(fontWeight = FontWeight.SemiBold,fontSize = 18.sp, lineHeight = 24.sp),
    titleMedium   = TextStyle(fontWeight = FontWeight.SemiBold,fontSize = 16.sp, lineHeight = 24.sp),
    bodyLarge     = TextStyle(fontWeight = FontWeight.Normal,  fontSize = 16.sp, lineHeight = 24.sp),
    bodyMedium    = TextStyle(fontWeight = FontWeight.Normal,  fontSize = 14.sp, lineHeight = 20.sp),
    bodySmall     = TextStyle(fontWeight = FontWeight.Normal,  fontSize = 12.sp, lineHeight = 16.sp),
    labelSmall    = TextStyle(fontWeight = FontWeight.Medium,  fontSize = 11.sp, lineHeight = 16.sp),
)

@Composable
fun YouthSpaceTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = YouthSpaceColors,
        typography  = YouthSpaceTypography,
        content     = content
    )
}

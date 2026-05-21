package com.example.youthspace.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.youthspace.viewmodel.AuthUiState
import com.example.youthspace.R

private val BgHeader   = Color(0xFF1A5296)
private val BtnColor   = Color(0xFF1A5296)
private val SpaceGold  = Color(0xFFFFB86F)
private val HeadingCol = Color(0xFF003B75)
private val LabelGray  = Color(0xFF737782)
private val BorderGray = Color(0xFFC2C7D1)
private val BlueMed    = Color(0xFF1A5296)
private val ErrRed     = Color(0xFFEF4444)
private val TextMain   = Color(0xFF111C2C)

@Composable
fun LoginScreen(
    email: String,
    password: String,
    uiState: AuthUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgHeader)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(BgHeader)
                .padding(top = 80.dp, bottom = 48.dp, start = 24.dp, end = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White.copy(alpha = 0.1f))
                        .border(1.dp, Color.White.copy(alpha = 0.25f), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_youthspace_logo),
                        contentDescription = "Logo",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    buildAnnotatedString {
                        withStyle(SpanStyle(color = Color.White, fontWeight = FontWeight.Normal)) { append("Youth") }
                        withStyle(SpanStyle(color = SpaceGold,  fontWeight = FontWeight.Normal)) { append("Space") }
                    },
                    fontSize = 16.sp,
                    letterSpacing = (-0.4).sp
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    "Bergabung dan mulai perjalananmu",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(Color.White)
                .padding(start = 24.dp, end = 24.dp, top = 32.dp, bottom = 32.dp)
        ) {
            Text(
                "Selamat datang!",
                color = HeadingCol,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "Masuk untuk melanjutkan perjalanan belajarmu",
                color = LabelGray,
                fontSize = 14.sp
            )

            Spacer(Modifier.height(24.dp))

            LabelText("EMAIL / USERNAME")
            Spacer(Modifier.height(6.dp))
            OutlinedTextField(
                value = email,
                onValueChange = onEmailChange,
                placeholder = { Text("email@kamu.com", color = LabelGray, fontSize = 14.sp) },
                leadingIcon = {
                    Icon(Icons.Outlined.Email, null, tint = LabelGray, modifier = Modifier.size(20.dp))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                colors = ysFieldColors(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            LabelText("PASSWORD")
            Spacer(Modifier.height(6.dp))
            OutlinedTextField(
                value = password,
                onValueChange = onPasswordChange,
                placeholder = { Text("Min. 8 karakter", color = LabelGray, fontSize = 14.sp) },
                leadingIcon = {
                    Icon(Icons.Outlined.Lock, null, tint = LabelGray, modifier = Modifier.size(20.dp))
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            if (passwordVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility,
                            null, tint = LabelGray, modifier = Modifier.size(20.dp)
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                colors = ysFieldColors(),
                modifier = Modifier.fillMaxWidth()
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Text(
                    "Lupa password?",
                    color = BlueMed,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .clickable { /* TODO: forgot password */ }
                )
            }

            Spacer(Modifier.height(20.dp))

            Button(
                onClick  = onLoginClick,
                enabled  = uiState !is AuthUiState.Loading,
                shape    = RoundedCornerShape(8.dp),
                colors   = ButtonDefaults.buttonColors(
                    containerColor         = BtnColor,
                    disabledContainerColor = BtnColor.copy(alpha = 0.5f)
                ),
                elevation       = ButtonDefaults.buttonElevation(4.dp),
                contentPadding  = PaddingValues(vertical = 16.dp),
                modifier        = Modifier.fillMaxWidth()
            ) {
                if (uiState is AuthUiState.Loading) {
                    CircularProgressIndicator(Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
                } else {
                    Text("Masuk", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(Modifier.height(16.dp))

            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Divider(Modifier.weight(1f), color = BorderGray)
                Text("  atau masuk dengan  ", color = LabelGray, fontSize = 12.sp)
                Divider(Modifier.weight(1f), color = BorderGray)
            }

            Spacer(Modifier.height(16.dp))

            OutlinedButton(
                onClick  = { /* TODO: Google Sign-In */ },
                shape    = RoundedCornerShape(8.dp),
                border   = ButtonDefaults.outlinedButtonBorder,
                colors   = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.White,
                    contentColor   = TextMain
                ),
                contentPadding = PaddingValues(vertical = 14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = "Google",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text("Lanjutkan dengan Google", fontSize = 14.sp, fontWeight = FontWeight.Medium)
            }

            Spacer(Modifier.height(20.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("Belum punya akun? ", color = LabelGray, fontSize = 14.sp)
                Text(
                    "Daftar Sekarang",
                    color = BlueMed,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable { onNavigateToRegister() }
                )
            }

            if (uiState is AuthUiState.Error) {
                Spacer(Modifier.height(16.dp))
                Box(
                    Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(ErrRed.copy(alpha = 0.08f))
                        .padding(12.dp, 10.dp)
                ) { Text(uiState.message, color = ErrRed, fontSize = 13.sp) }
            }
        }
    }
}

@Composable
private fun LabelText(text: String) {
    Text(text, color = LabelGray, fontSize = 12.sp, fontWeight = FontWeight.Medium, letterSpacing = 0.5.sp)
}

@Composable
private fun ysFieldColors() = OutlinedTextFieldDefaults.colors(
    unfocusedContainerColor = Color.White,
    focusedContainerColor   = Color.White,
    unfocusedBorderColor    = BorderGray,
    focusedBorderColor      = BlueMed,
    cursorColor             = BlueMed,
)
package com.youthspace.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.youthspace.viewmodel.AuthUiState

private val BgHeader   = Color(0xFF1A5296)
private val BtnColor   = Color(0xFF1A5296)
private val SpaceGold  = Color(0xFFFFB86F)
private val HeadingBlue= Color(0xFF1A5296)
private val LabelGray  = Color(0xFF737782)
private val BorderGray = Color(0xFFC2C7D1)
private val ErrRed     = Color(0xFFEF4444)
private val GreenCheck = Color(0xFF10B981)

@Composable
fun RegisterScreen(
    firstName: String,
    lastName: String,
    username: String,
    email: String,
    password: String,
    confirmPassword: String,
    uiState: AuthUiState,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmVisible  by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(BgHeader)
                .padding(top = 80.dp, bottom = 48.dp),
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
                    Text("✦", fontSize = 24.sp, color = Color.White)
                }
                Spacer(Modifier.height(16.dp))
                Text(
                    buildAnnotatedString {
                        withStyle(SpanStyle(color = Color.White)) { append("Youth") }
                        withStyle(SpanStyle(color = SpaceGold))   { append("Space") }
                    },
                    fontSize = 16.sp
                )
                Spacer(Modifier.height(4.dp))
                Text("Bergabung dan mulai perjalananmu", color = Color.White, fontSize = 16.sp)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(Color.White)
                .padding(start = 24.dp, end = 24.dp, top = 32.dp, bottom = 40.dp)
        ) {
            Text("Buat Akun Baru", color = HeadingBlue, fontSize = 16.sp, fontWeight = FontWeight.Normal)
            Spacer(Modifier.height(4.dp))
            Text("Isi data diri kamu untuk memulai.", color = LabelGray, fontSize = 14.sp)

            Spacer(Modifier.height(24.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Column(Modifier.weight(1f)) {
                    RegLabel("NAMA DEPAN")
                    Spacer(Modifier.height(6.dp))
                    OutlinedTextField(
                        value = firstName,
                        onValueChange = onFirstNameChange,
                        placeholder = { Text("Nama", color = LabelGray, fontSize = 13.sp) },
                        leadingIcon = {
                            Icon(Icons.Default.Person, null, tint = LabelGray, modifier = Modifier.size(16.dp))
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = regFieldColors(),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Column(Modifier.weight(1f)) {
                    RegLabel("NAMA BELAKANG")
                    Spacer(Modifier.height(6.dp))
                    OutlinedTextField(
                        value = lastName,
                        onValueChange = onLastNameChange,
                        placeholder = { Text("Belakang", color = LabelGray, fontSize = 13.sp) },
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        colors = regFieldColors(),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            RegLabel("USERNAME")
            Spacer(Modifier.height(6.dp))
            OutlinedTextField(
                value = username,
                onValueChange = onUsernameChange,
                placeholder = { Text("@username_kamu", color = LabelGray, fontSize = 14.sp) },
                leadingIcon = {
                    Icon(Icons.Default.AlternateEmail, null, tint = LabelGray, modifier = Modifier.size(16.dp))
                },
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                colors = regFieldColors(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            RegLabel("EMAIL")
            Spacer(Modifier.height(6.dp))
            OutlinedTextField(
                value = email,
                onValueChange = onEmailChange,
                placeholder = { Text("email@kamu.com", color = LabelGray, fontSize = 14.sp) },
                leadingIcon = {
                    Icon(Icons.Default.Email, null, tint = LabelGray, modifier = Modifier.size(16.dp))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                colors = regFieldColors(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            RegLabel("PASSWORD")
            Spacer(Modifier.height(6.dp))
            OutlinedTextField(
                value = password,
                onValueChange = onPasswordChange,
                placeholder = { Text("Min. 8 karakter", color = LabelGray, fontSize = 14.sp) },
                leadingIcon = {
                    Icon(Icons.Default.Lock, null, tint = LabelGray, modifier = Modifier.size(16.dp))
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            null, tint = LabelGray, modifier = Modifier.size(16.dp)
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                colors = regFieldColors(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            RegLabel("KONFIRMASI PASSWORD")
            Spacer(Modifier.height(6.dp))
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = onConfirmPasswordChange,
                placeholder = { Text("Ulangi password", color = LabelGray, fontSize = 14.sp) },
                leadingIcon = {
                    val match = confirmPassword.isNotEmpty() && confirmPassword == password
                    Icon(
                        if (match) Icons.Default.CheckCircle else Icons.Default.Lock,
                        null,
                        tint = if (match) GreenCheck else LabelGray,
                        modifier = Modifier.size(16.dp)
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { confirmVisible = !confirmVisible }) {
                        Icon(
                            if (confirmVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            null, tint = LabelGray, modifier = Modifier.size(16.dp)
                        )
                    }
                },
                visualTransformation = if (confirmVisible) VisualTransformation.None
                else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                colors = regFieldColors(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = onRegisterClick,
                enabled = uiState !is AuthUiState.Loading,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor         = BtnColor,
                    disabledContainerColor = BtnColor.copy(alpha = 0.5f)
                ),
                elevation = ButtonDefaults.buttonElevation(4.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (uiState is AuthUiState.Loading) {
                    CircularProgressIndicator(Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
                } else {
                    Text("BUAT AKUN", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 1.sp)
                }
            }

            Spacer(Modifier.height(16.dp))

            Text(
                "Dengan mendaftar, kamu menyetujui Syarat & Ketentuan dan Kebijakan YouthSpace",
                color = LabelGray,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("Sudah punya akun? ", color = LabelGray, fontSize = 14.sp)
                Text(
                    "Masuk",
                    color = BtnColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable { onNavigateToLogin() }
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
private fun RegLabel(text: String) {
    Text(text, color = LabelGray, fontSize = 12.sp, fontWeight = FontWeight.Medium, letterSpacing = 0.5.sp)
}

@Composable
private fun regFieldColors() = OutlinedTextFieldDefaults.colors(
    unfocusedContainerColor = Color.White,
    focusedContainerColor   = Color.White,
    unfocusedBorderColor    = BorderGray,
    focusedBorderColor      = Color(0xFF1A5296),
    cursorColor             = Color(0xFF1A5296),
)
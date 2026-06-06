package com.example.youthspace.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.youthspace.navigation.Screen
import com.example.youthspace.ui.components.BottomBar
import com.example.youthspace.viewmodel.AuthViewModel
import com.example.youthspace.viewmodel.ProfileViewModel

private val ProfileBlue  = Color(0xFF1A3A63)
private val SlateText    = Color(0xFF64748B)
private val DividerColor = Color(0xFFE2E8F0)
private val BgPage       = Color(0xFFF7FAFF)
private val ErrorRed     = Color(0xFFEF4444)

@Composable
fun ProfileScreen(
    navController: NavController,
    onLogoutClick: () -> Unit,
    profileViewModel: ProfileViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel()
) {
    val currentUser by profileViewModel.currentUser.collectAsStateWithLifecycle()

    val userName  = currentUser?.name?.takeIf { it.isNotBlank() } ?: "Pengguna"
    val userEmail = currentUser?.email
        ?: authViewModel.currentUserEmail()
        ?: ""
    val photoUrl  = currentUser?.photoUrl

    LaunchedEffect(Unit) {
        profileViewModel.loadUser()
    }

    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BgPage)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(32.dp))

                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .background(ProfileBlue)
                        .border(3.dp, Color.White, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    if (!photoUrl.isNullOrBlank()) {
                        AsyncImage(
                            model = photoUrl,
                            contentDescription = "Foto Profil",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                        )
                    } else {
                        Text(
                            text = userName.first().uppercase(),
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(Modifier.height(14.dp))

                Text(
                    text = userName,
                    color = ProfileBlue,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = userEmail,
                    color = SlateText,
                    fontSize = 14.sp
                )

                Spacer(Modifier.height(32.dp))
            }

            Spacer(Modifier.height(16.dp))

            ProfileSection(title = "AKUN") {

                ProfileMenuItem(
                    icon      = Icons.Default.Person,
                    iconBg    = Color(0xFFEFF6FF),
                    iconColor = ProfileBlue,
                    title     = "Edit Profil",
                    subtitle  = "Ubah nama dan foto profil",
                    onClick   = {
                        navController.navigate(Screen.EditProfile.route)
                    }
                )

                HorizontalDivider(
                    color = Color(0xFFF1F5F9),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                ProfileMenuItem(
                    icon      = Icons.Default.Lock,
                    iconBg    = Color(0xFFFAF5FF),
                    iconColor = Color(0xFF7C3AED),
                    title     = "Ubah Password",
                    subtitle  = "Keamanan akun",
                    onClick   = { /* TODO: navigasi ke UbahPassword screen */ }
                )
            }

            Spacer(Modifier.height(12.dp))

            ProfileSection(title = "PREFERENSI") {

                ProfileMenuItem(
                    icon      = Icons.Default.Notifications,
                    iconBg    = Color(0xFFFFF7ED),
                    iconColor = Color(0xFFEA580C),
                    title     = "Notifikasi",
                    subtitle  = "Artikel baru & rekomendasi",
                    onClick   = { /* TODO */ }
                )

                HorizontalDivider(
                    color = Color(0xFFF1F5F9),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                ProfileMenuItem(
                    icon      = Icons.Default.Info,
                    iconBg    = Color(0xFFF0FDF4),
                    iconColor = Color(0xFF16A34A),
                    title     = "Tentang YouthSpace",
                    subtitle  = "Versi 1.0.0",
                    onClick   = { /* TODO */ }
                )
            }

            Spacer(Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
            ) {
                OutlinedButton(
                    onClick = onLogoutClick,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = ErrorRed.copy(alpha = 0.05f),
                        contentColor   = ErrorRed
                    ),
                    contentPadding = PaddingValues(vertical = 14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = null,
                        tint = ErrorRed,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "Keluar",
                        color = ErrorRed,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ProfileSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        Text(
            text = title,
            color = SlateText,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 1.sp
        )
        Spacer(Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .border(1.dp, DividerColor, RoundedCornerShape(12.dp)),
            content = content
        )
    }
}

@Composable
private fun ProfileMenuItem(
    icon: ImageVector,
    iconBg: Color,
    iconColor: Color,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null, tint = iconColor, modifier = Modifier.size(20.dp))
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    text = title,
                    color = ProfileBlue,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = subtitle,
                    color = SlateText,
                    fontSize = 12.sp
                )
            }
            Icon(
                Icons.Default.ChevronRight,
                null,
                tint = SlateText,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
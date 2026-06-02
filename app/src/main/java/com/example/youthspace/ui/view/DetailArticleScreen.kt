package com.example.youthspace.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun DetailArtikelScreen(
    navController: NavController,
    artikelId: String
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .background(Color(0xFF0E4C92))
        ) {

            IconButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {

                Icon(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
            }

            Text(
                text = "Gambar Artikel",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = "PSIKOLOGI",
                color = Color(0xFFC98A21),
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Mengelola Kecemasan Saat Menghadapi Ujian Akhir",
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                lineHeight = 34.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row {

                Icon(
                    imageVector = Icons.Outlined.BookmarkBorder,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text("8 menit baca")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = """
                Kecemasan menjelang ujian merupakan hal yang sering dialami oleh pelajar maupun mahasiswa.

                Namun apabila tidak dikelola dengan baik, kecemasan dapat mengganggu konsentrasi belajar serta menurunkan performa saat ujian.

                Beberapa cara yang dapat dilakukan adalah:

                • Membuat jadwal belajar yang teratur

                • Menghindari sistem kebut semalam

                • Menjaga pola tidur yang baik

                • Melakukan relaksasi atau olahraga ringan

                Dengan pengelolaan yang tepat, kecemasan dapat berubah menjadi motivasi untuk belajar lebih efektif.
                """.trimIndent(),
                lineHeight = 28.sp
            )
        }
    }
}
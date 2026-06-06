package com.example.youthspace.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.youthspace.viewmodel.CategoryViewModel
import com.example.youthspace.viewmodel.CreateArticleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateArticleScreen(
    navController: NavController,
    viewModel: CreateArticleViewModel = viewModel(),
    categoryViewModel: CategoryViewModel = viewModel()
) {

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    var selectedCategoryName by remember {
        mutableStateOf("")
    }

    var selectedCategoryId by remember {
        mutableStateOf("")
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    val categories = viewModel.categories.value
    val isLoading = viewModel.isLoading.value

    // State untuk dialog tambah kategori baru
    val showAddCategoryDialog = categoryViewModel.showDialog.value
    val categoryInput = categoryViewModel.inputName.value
    val categoryLoading = categoryViewModel.isLoading.value

    // Refresh daftar kategori di CreateArticleViewModel setelah tambah kategori baru
    LaunchedEffect(categoryViewModel.message.value) {
        if (categoryViewModel.message.value?.contains("berhasil") == true) {
            viewModel.refreshCategories()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Create Article")
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {

            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                },
                label = {
                    Text("Judul Artikel")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Row: dropdown kategori + tombol + kategori baru
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        value = selectedCategoryName,
                        onValueChange = {},
                        readOnly = true,
                        label = {
                            Text("Kategori")
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        }
                    ) {
                        categories.forEach { category ->
                            DropdownMenuItem(
                                text = {
                                    Text(category.name)
                                },
                                onClick = {
                                    selectedCategoryName = category.name
                                    selectedCategoryId = category.id
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Tombol + untuk tambah kategori baru
                IconButton(
                    onClick = { categoryViewModel.openAddDialog() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Tambah Kategori Baru"
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = content,
                onValueChange = {
                    content = it
                },
                label = {
                    Text("Isi Artikel")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading,
                onClick = {
                    if (
                        title.isBlank() ||
                        content.isBlank() ||
                        selectedCategoryId.isBlank()
                    ) return@Button

                    viewModel.createArticle(
                        judul = title,
                        isi = content,
                        kategoriId = selectedCategoryId,
                        imageUrl = null
                    ) {
                        navController.popBackStack()
                    }
                }
            ) {
                Text(
                    if (isLoading)
                        "Publishing..."
                    else
                        "Publish Article"
                )
            }
        }
    }

    if (showAddCategoryDialog) {
        AlertDialog(
            onDismissRequest = { categoryViewModel.closeDialog() },
            title = { Text("Tambah Kategori Baru") },
            text = {
                OutlinedTextField(
                    value = categoryInput,
                    onValueChange = { categoryViewModel.inputName.value = it },
                    label = { Text("Nama Kategori") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(
                    onClick = { categoryViewModel.saveCategory() },
                    enabled = !categoryLoading
                ) {
                    Text("Simpan")
                }
            },
            dismissButton = {
                TextButton(onClick = { categoryViewModel.closeDialog() }) {
                    Text("Batal")
                }
            }
        )
    }
}
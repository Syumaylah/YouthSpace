package com.example.youthspace.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.youthspace.viewmodel.CreateArticleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateArticleScreen(
    navController: NavController,
    viewModel: CreateArticleViewModel = viewModel()
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

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
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

                                selectedCategoryName =
                                    category.name

                                selectedCategoryId =
                                    category.id

                                expanded = false
                            }
                        )
                    }
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

}

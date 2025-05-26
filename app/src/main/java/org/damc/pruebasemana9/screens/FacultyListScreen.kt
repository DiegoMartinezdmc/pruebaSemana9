package org.damc.pruebasemana9.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.damc.pruebasemana9.services.Faculty
import org.damc.pruebasemana9.viewmodels.FacultyViewModel

// Composable principal que muestra la lista de facultades
@Composable
fun FacultyListScreen(viewModel: FacultyViewModel = viewModel()) {
    // Observa el mensaje de toast
    val toastMessage by viewModel.toastMessage.collectAsState()
    val context = LocalContext.current

    // Observa la lista de facultades desde el ViewModel
    val faculties by viewModel.facultyList.collectAsState()

    // Observa el estado de login
    val loginState = viewModel.loginState.collectAsState()

    // Muestra un Toast si hay un mensaje
    LaunchedEffect(toastMessage) {
        if (toastMessage.isNotEmpty()) {
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
            viewModel.clearToastMessage()
        }
    }

    // Carga las facultades cuando el login es exitoso
    LaunchedEffect(loginState.value) {
        if (loginState.value == true) {
            viewModel.loadFaculties()
        }
    }

    // Diseño general de la pantalla
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
            .wrapContentWidth(Alignment.CenterHorizontally),
    ) {
        // Título de la pantalla
        Text(
            text = "Lista de facultades",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de facultades usando LazyColumn
        LazyColumn {
            items(faculties) { faculty ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    FacultyItem(faculty = faculty)  // Muestra cada ítem
                    HorizontalDivider()            // Línea divisora
                }
            }
        }
    }
}

// Composable que muestra una fila con el nombre y la abreviatura de una facultad
@Composable
fun FacultyItem(faculty: Faculty) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Nombre de la facultad con espacio flexible
        Text(
            text = faculty.facultyName,
            modifier = Modifier.weight(1f)
        )
        // Abreviatura al lado derecho
        Text(text = faculty.facultyAbbreviation)
    }
}

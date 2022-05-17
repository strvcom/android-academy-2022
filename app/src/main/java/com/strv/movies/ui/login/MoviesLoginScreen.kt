package com.strv.movies.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.strv.movies.R
import com.strv.movies.ui.error.ErrorScreen
import com.strv.movies.ui.loading.LoadingScreen

@Composable
fun MoviesLoginScreen(
    navigateToMovieList: () -> Unit,
    viewModel: MoviesLoginViewModel = viewModel()
) {
    val viewState by viewModel.viewState.collectAsState()

    if (viewState.loading) {
        LoadingScreen()
    } else if (viewState.error != null) {
        ErrorScreen(errorMessage = viewState.error!!)
    } else {
        LoginElements(navigateToMovieList)

    }
}

@Composable
private fun LoginElements(navigateToMovieList: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(start = 40.dp, bottom = 20.dp, top = 20.dp, end = 40.dp)
            .fillMaxWidth()
    ) {
        TextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.padding(bottom = 10.dp),
            isError = false,
            label = {
                Text(text = stringResource(id = R.string.login_username_text_input_field))
            }
        )
        TextField(
            value = " ",
            onValueChange = {},
            modifier = Modifier.padding(bottom = 10.dp),
            isError = false,
            label = {
                Text(stringResource(id = R.string.login_password_text_input_field))
            }
        )
        Button(
            onClick = { navigateToMovieList() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            Text(stringResource(id = R.string.login_btn))
        }
        ContinueWithFacebookButton(navigateToMovieList)
    }
}

@Composable
private fun ContinueWithFacebookButton(navigateToMovieList: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
//            .padding(start = 40.dp, bottom = 10.dp, top = 18.dp, end = 40.dp)
            .fillMaxWidth()
    ) {
        Button(
            onClick = { navigateToMovieList() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_facebook),
                contentDescription = stringResource(id = R.string.continue_with_fb),
                modifier = Modifier.size(24.dp)
            )
            Text(
                modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                text = stringResource(id = R.string.continue_with_fb)
            )
        }
    }
    SignInGmailButton(navigateToMovieList)
}

@Composable
private fun SignInGmailButton(navigateToMovieList: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
//            .padding(start = 40.dp, bottom = 10.dp, top = 18.dp, end = 40.dp)
            .fillMaxWidth()
    ) {
        Button(
            onClick = { navigateToMovieList() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_gmail),
                modifier = Modifier.size(24.dp),
                contentDescription = stringResource(id = R.string.continue_with_gmail)
            )
            Text(
                modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                text = stringResource(id = R.string.continue_with_gmail)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 70.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_movie_clapper),
                contentDescription = "Movie Clapper",
            )
            Icon(
                painter = painterResource(R.drawable.ic_popcorn),
                contentDescription = "Popcorn",
            )
        }
    }
}





package com.strv.movies.ui.auth

import android.util.Log
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import com.strv.movies.R
import com.strv.movies.network.auth.LoginEvent
import com.strv.movies.ui.components.CustomTopAppBar
import com.strv.movies.ui.theme.login_facebookLogo
import kotlinx.coroutines.launch


@Composable
fun LogInScreen(
    viewModel: LoginViewModel = viewModel(),
    onSuccessfulLogin: () -> Unit,
    isDarkTheme: Boolean,
    onChangeThemeClicked: () -> Unit
) {
    val viewState by viewModel.viewState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val userName = viewState.user
    val password = viewState.password
    val uriHandler = LocalUriHandler.current
    val uriSignIn = stringResource(id = R.string.login_signup_link)
    val uriPassword = stringResource(R.string.login_password_reset)
    var passwordVisibility by rememberSaveable {
        mutableStateOf(false)
    }
    val icon = if (passwordVisibility) {
        painterResource(id = R.drawable.ic_invisible)
    } else {
        painterResource(id = R.drawable.ic_visible)
    }
    LaunchedEffect(key1 = viewModel.errorFlow) {
        Log.d("TAG", "launch effect launched")
        coroutineScope.launch {
            viewModel.errorFlow.collect {
                snackBarHostState.showSnackbar(message = it.message)
            }
        }
    }
    Scaffold(
        scaffoldState = rememberScaffoldState(snackbarHostState = snackBarHostState),
        topBar = {
            CustomTopAppBar(
                isDarkTheme = isDarkTheme,
                onChangeThemeClick = onChangeThemeClicked
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_login_logo),
                contentDescription = "App logo",
                modifier = Modifier.padding(top = 20.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(R.string.login_login),
                style = MaterialTheme.typography.h2
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(0.8f),
                value = userName,
                onValueChange = { viewModel.loginEvent(LoginEvent.UpdateUsername(it)) },
                maxLines = 1,
                label = {
                    Text(
                        text = stringResource(
                            R.string.login_username
                        )
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = stringResource
                            (R.string.login_contentDesc_emailIcon)
                    )
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(0.8f),
                value = password,
                onValueChange = { viewModel.loginEvent(LoginEvent.UpdatePassword(it)) },
                label = {
                    Text(
                        text = stringResource(
                            R.string.login_password
                        )
                    )
                },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordVisibility) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = stringResource(
                            R.string.login_contentDesc_passwordIcon
                        )
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        Icon(
                            painter = icon,
                            contentDescription = stringResource(
                                R.string.login_contentDesc_visibilityIcon
                            )
                        )

                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    viewModel.loginEvent(LoginEvent.Login(onSuccessfulLogin))
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Text(
                    text = stringResource(id = R.string.login_login),
                    modifier = Modifier.align(CenterVertically)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            ClickableText(
                text = AnnotatedString(stringResource(R.string.login_forgotPassword)),
                onClick = {
                    uriHandler.openUri(uriPassword)
                },
                style = TextStyle(
                    color = MaterialTheme.colors.onBackground,
                    textDecoration = TextDecoration.Underline
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(0.6f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(
                    onClick = {
                        viewModel.loginEvent(LoginEvent.OnClickNotImplemented)
                    },
                    modifier = Modifier
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_login_google),
                        contentDescription = stringResource(
                            R.string.login_contentDesc_googleIcon
                        ),
                    )
                }

                IconButton(
                    onClick = {
                        viewModel.loginEvent(LoginEvent.OnClickNotImplemented)
                    },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(color = login_facebookLogo)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_login_facebook),
                        contentDescription = stringResource(
                            R.string.login_contentDesc_facebookIcon
                        )
                    )
                }
            }
            ClickableText(
                text = AnnotatedString(stringResource(R.string.login_signup)),
                onClick = {
                    uriHandler.openUri(uriSignIn)
                },
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(top = 20.dp),
                style = TextStyle(
                    color = MaterialTheme.colors.onBackground,
                    textDecoration = TextDecoration.Underline
                )
            )

        }

    }
}
